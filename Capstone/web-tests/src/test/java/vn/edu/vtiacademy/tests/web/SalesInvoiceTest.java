package vn.edu.vtiacademy.tests.web;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import java.math.BigDecimal;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import vn.edu.vtiacademy.common.data.TestDataPrefix;
import vn.edu.vtiacademy.pages.CreateSalesInvoicePage;
import vn.edu.vtiacademy.pages.SalesInvoiceListPage;

/**
 * Kiem thu phan he Sales Invoice cua WorkDo Dash SaaS.
 *
 * <p><b>Boi canh:</b> he thong khong co tai lieu dac ta (brownfield). Ky vong cua tung test vi
 * vay duoc rut ra tu hai nguon: hanh vi quan sat duoc trong phien kham pha
 * ({@code docs/exploratory-test-notes.md}) va cac quy tac ke toan pho thong. Cho nao hai nguon
 * lech nhau thi ghi lai thanh defect chu khong sua ky vong cho khop voi ung dung.
 *
 * <p><b>Cach ly du lieu:</b> {@code dash-demo.workdo.io} la ban demo dung chung voi ca internet,
 * du lieu bi nguoi khac sua bat cu luc nao. Khong test nao duoc gia dinh co san mot ban ghi
 * nao do. Ban ghi do test tao ra deu mang marker {@code AT_<timestamp>_<random>} nhet trong o
 * Notes (so hoa don la auto-generated, khong co o de nhet marker vao).
 *
 * <p>Chay: {@code mvn -pl web-tests test}
 */
@Epic("Capstone - Web Layer")
@Feature("WorkDo Dash SaaS - Sales Invoice")
public class SalesInvoiceTest extends BaseWebTest {

  /**
   * Chi so option duoc chon cho Customer va Warehouse.
   *
   * <p>The {@code <select>} an cua Radix KHONG chua option placeholder "Select Customer" - chu
   * "Select Customer" chi nam tren the {@code <div role=combobox>} nhin thay duoc. Nen
   * option[0] da la mot khach hang that.
   */
  private static final int FIRST_REAL_OPTION = 0;

  private static final int FIRST_PRODUCT = 0;

  /**
   * Sai so cho phep khi doi chieu bon con so trong khoi Summary.
   *
   * <p>Ung dung hien tien da lam tron ve 2 chu so, nen phep {@code Total = Subtotal - Discount
   * + Tax} tinh tren cac gia tri DA LAM TRON co the lech toi mot xu. Vi du WEB_INV_04: chiet
   * khau that la 49.995 nhung hien ra 50.00. Sai so mot xu la do hien thi, khong phai loi tinh
   * toan - neu de dung sai so 0 thi test se do vi mot ly do khong dang.
   */
  private static final BigDecimal ROUNDING_TOLERANCE = new BigDecimal("0.01");

  // -----------------------------------------------------------------------------------
  // Smoke
  // -----------------------------------------------------------------------------------

  @Test(description = "WEB_INV_01 - Trang danh sach Sales Invoice tai duoc va co du lieu")
  @Story("Xem danh sach hoa don")
  @Severity(SeverityLevel.BLOCKER)
  @Description("Test chan cua ca bo web: neu khong vao duoc danh sach thi moi test phia sau "
      + "deu do va bao loi vo nghia. De rieng de nhin mot cai la biet loi o dau.")
  public void salesInvoiceList_loadsWithData() {
    SalesInvoiceListPage listPage = salesInvoiceListPage.open();

    SoftAssert softly = new SoftAssert();
    softly.assertTrue(listPage.isLoaded(), "Bang danh sach hoa don phai hien thi");
    softly.assertTrue(listPage.getRowCount() > 0,
        "Danh sach demo phai co it nhat mot hoa don de test");
    softly.assertAll();
  }

  // -----------------------------------------------------------------------------------
  // Tinh toan tien - nhom INV_CALC
  // -----------------------------------------------------------------------------------

  /**
   * Bo du lieu kiem tra cong thuc tinh tien.
   *
   * <p>Moi dong: {@code {ma case, so luong, don gia, chiet khau %, subtotal ky vong,
   * chiet khau ky vong}}.
   *
   * <p><b>Ky vong nay duoc rut ra tu chinh ung dung, khong phai doan.</b> He thong khong co tai
   * lieu dac ta, nen cong thuc duoc xac dinh bang cach nhap nhieu bo so va doc lai ket qua
   * (xem muc 2.3 trong {@code docs/exploratory-test-notes.md}):
   * <pre>
   *   Subtotal = Qty x Unit Price
   *   Discount = Subtotal x (chiet khau % / 100)      (tinh tren TUNG DONG)
   *   Tax      = (Subtotal - Discount) x thue suat    (tinh SAU khi tru chiet khau)
   *   Total    = Subtotal - Discount + Tax
   * </pre>
   *
   * <p>Lan chay dau tien cua bo test nay ky vong {@code Tax = 0} vi khi mo form ra o Tax hien
   * "No tax". Do la quan sat khi CHUA chon san pham - chon san pham xong thi thue suat cua
   * chinh san pham do duoc ap vao. Ky vong da duoc sua theo hanh vi that.
   *
   * <p><b>Ve WEB_INV_04 va cach lam tron:</b> chiet khau 50% cua 99.99 la <b>49.995</b>, va ung
   * dung hien ra <b>49.99</b> - tuc lam tron XUONG chu khong phai lam tron nua len (half-up)
   * nhu thong le ke toan (se ra 50.00). Chenh lech mot xu. Bo test ghi lai hanh vi THUC TE
   * (49.99) va ghi cau hoi ve huong lam tron vao bao cao de mentor xac nhan, thay vi tu quyet
   * dinh day la loi hay khong.
   */
  @DataProvider(name = "invoiceCalculationData")
  public Object[][] invoiceCalculationData() {
    return new Object[][] {
        {"WEB_INV_02", "2", "100", "0", "200.00", "0.00"},
        {"WEB_INV_03", "3", "100", "10", "300.00", "30.00"},
        {"WEB_INV_04", "1", "99.99", "50", "99.99", "49.99"},
    };
  }

  @Test(dataProvider = "invoiceCalculationData",
      description = "Khoi Invoice Summary tinh dung so tien")
  @Story("Tinh tien tren hoa don")
  @Severity(SeverityLevel.CRITICAL)
  @Description("Kiem tra hai thu: (1) Subtotal va Discount dung bang tay tinh ra; (2) bon con so "
      + "trong khoi Summary nhat quan voi nhau theo Total = Subtotal - Discount + Tax. "
      + "Phep (2) khong phu thuoc thue suat cua san pham nao, nen bo test khong vo khi du lieu "
      + "demo doi san pham. Case WEB_INV_04 dung 99.99 voi chiet khau 50% de lo ra cach lam tron "
      + "(49.995 - len hay xuong?).")
  public void invoiceSummary_calculatesAmountsCorrectly(
      String caseId, String quantity, String unitPrice, String discountPercent,
      String expectedSubtotal, String expectedDiscount) {

    CreateSalesInvoicePage createPage = new CreateSalesInvoicePage(webUI).open();
    fillMandatoryFields(createPage);
    createPage.selectProductByIndex(FIRST_PRODUCT)
        .inputQuantity(quantity)
        .inputUnitPrice(unitPrice)
        .inputDiscountPercent(discountPercent);

    BigDecimal subtotal = createPage.getSubtotal();
    BigDecimal discount = createPage.getDiscount().abs();   // ung dung hien dang "-30.00$"
    BigDecimal tax = createPage.getTax();
    BigDecimal total = createPage.getTotal();

    SoftAssert softly = new SoftAssert();
    softly.assertEquals(subtotal, new BigDecimal(expectedSubtotal),
        caseId + " - Subtotal voi qty=" + quantity + ", don gia=" + unitPrice);
    softly.assertEquals(discount, new BigDecimal(expectedDiscount),
        caseId + " - Discount voi chiet khau " + discountPercent + "%");

    BigDecimal expectedTotal = subtotal.subtract(discount).add(tax);
    softly.assertTrue(total.subtract(expectedTotal).abs().compareTo(ROUNDING_TOLERANCE) <= 0,
        caseId + " - Total phai bang Subtotal - Discount + Tax. "
            + "Subtotal=" + subtotal + ", Discount=" + discount + ", Tax=" + tax
            + " => mong doi " + expectedTotal + ", thuc te " + total);
    softly.assertAll();
  }

  // -----------------------------------------------------------------------------------
  // Tao hoa don - luong chinh
  // -----------------------------------------------------------------------------------

  @Test(description = "WEB_INV_05 - Tao hoa don voi du lieu hop le thi hoa don xuat hien "
      + "trong danh sach")
  @Story("Tao hoa don")
  @Severity(SeverityLevel.BLOCKER)
  @Description("Duong di hanh phuc day du. Ban ghi mang marker AT_ trong Notes de tim lai duoc "
      + "giua du lieu cua nguoi khac tren he thong demo dung chung.")
  public void createInvoice_withValidData_appearsInList() {
    String marker = TestDataPrefix.newMarker("WEB_INV_05");

    // Chup so hoa don o dong dau danh sach TRUOC khi tao: so hoa don do he thong tu sinh, khong
    // doan truoc duoc, nen cach duy nhat de chung minh "da tao them mot ban ghi" la so sanh
    // dong dau truoc va sau.
    String newestBefore = salesInvoiceListPage.open().getFirstRowInvoiceNumber();

    CreateSalesInvoicePage createPage = new CreateSalesInvoicePage(webUI).open();
    fillMandatoryFields(createPage);
    createPage.selectProductByIndex(FIRST_PRODUCT)
        .inputQuantity("2")
        .inputUnitPrice("150")
        .inputNotes(marker);
    SalesInvoiceListPage listAfter = createPage.clickCreateAndWaitForList();

    String newestAfter = listAfter.open().getFirstRowInvoiceNumber();

    assertTrue(newestAfter != null && !newestAfter.equals(newestBefore),
        "WEB_INV_05 - phai co hoa don MOI o dau danh sach sau khi tao. "
            + "Truoc: '" + newestBefore + "', sau: '" + newestAfter + "'");
  }

  @Test(description = "WEB_INV_06 - Tim theo so hoa don tra ve dung mot dong")
  @Story("Tim kiem hoa don")
  @Severity(SeverityLevel.NORMAL)
  @Description("Deep-link ?search=<so hoa don> la co che de test co lap dung ban ghi cua minh "
      + "giua hang tram ban ghi dung chung - test nay bao ve chinh co che do.")
  public void searchByInvoiceNumber_returnsExactlyOneRow() {
    String existingInvoiceNumber = salesInvoiceListPage.open().getFirstRowInvoiceNumber();

    SalesInvoiceListPage result = salesInvoiceListPage.openWithSearch(existingInvoiceNumber);

    SoftAssert softly = new SoftAssert();
    softly.assertEquals(result.getRowCount(), 1,
        "WEB_INV_06 - tim theo so hoa don '" + existingInvoiceNumber + "' phai ra dung 1 dong");
    softly.assertEquals(result.getFirstRowInvoiceNumber(), existingInvoiceNumber,
        "WEB_INV_06 - dong tra ve phai dung hoa don da tim");
    softly.assertAll();
  }

  // -----------------------------------------------------------------------------------
  // Negative
  // -----------------------------------------------------------------------------------

  @Test(description = "WEB_INV_07 - Bam Create khi form con trong thi KHONG tao ra hoa don nao")
  @Story("Tao hoa don")
  @Severity(SeverityLevel.CRITICAL)
  @Description("Case negative cua lop web. Kiem tra dieu QUAN TRONG NHAT khi validate that bai: "
      + "he thong khong duoc am tham tao ra mot ban ghi thieu du lieu. "
      + "Test KHONG assert noi dung thong bao loi, va day la co y - trong qua trinh khao sat, "
      + "form khi thieu du lieu co luc hien thong bao co luc khong (xem DEF-03, DEF-04). "
      + "Gan test vao mot hanh vi chua on dinh se tao ra test chap chon; gan vao hau qua "
      + "nghiep vu (co ban ghi rac hay khong) thi vua on dinh vua dung cai can bao ve.")
  public void createInvoice_withEmptyForm_doesNotCreateAnyInvoice() {
    String newestBefore = salesInvoiceListPage.open().getFirstRowInvoiceNumber();

    new CreateSalesInvoicePage(webUI).open().clickCreate();

    String newestAfter = salesInvoiceListPage.open().getFirstRowInvoiceNumber();

    assertEquals(newestAfter, newestBefore,
        "WEB_INV_07 - form trong ma van tao duoc hoa don thi day la loi nghiem trong: "
            + "he thong sinh ra ban ghi thieu Due Date / Customer / Warehouse / San pham");
  }

  // -----------------------------------------------------------------------------------
  // Helper
  // -----------------------------------------------------------------------------------

  /**
   * Dien ba truong bat buoc: Due Date, Customer, Warehouse.
   *
   * <p>Thu tu QUAN TRONG: danh sach san pham chi duoc nap sau khi da chon Warehouse, va chi
   * chua san pham CON TON trong dung kho do. Doi thu tu, hoac chon phai kho khong con hang,
   * deu dan den dropdown san pham rong.
   */
  private void fillMandatoryFields(CreateSalesInvoicePage createPage) {
    createPage.selectDueDateNextMonth()
        .selectCustomerByIndex(FIRST_REAL_OPTION)
        .selectWarehouseWithAvailableProducts();
  }
}
