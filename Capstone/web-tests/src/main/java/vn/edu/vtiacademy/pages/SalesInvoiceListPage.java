package vn.edu.vtiacademy.pages;

import io.qameta.allure.Step;
import java.util.List;
import org.openqa.selenium.WebElement;
import vn.edu.vtiacademy.common.keywords.WebUI;

/**
 * Trang danh sach Sales Invoice ({@code /sales-invoices}).
 *
 * <p><b>Vi sao dieu huong bang URL truc tiep thay vi bam menu:</b> menu ben trai cua WorkDo co
 * hon 200 muc add-on, cuon rat dai va vi tri thay doi theo cau hinh workspace. Bam qua menu la
 * ba thao tac de vo, khong kiem tra them duoc gi ve nghiep vu Sales Invoice. Deep-link tro
 * thang toi trang can test - ngan hon va on dinh hon. Danh doi: bo test nay KHONG bao ve duong
 * dan menu; da ghi vao muc gioi han cua bao cao.
 */
public class SalesInvoiceListPage extends BasePage {

  public static final String LIST_URL = "https://dash-demo.workdo.io/sales-invoices";

  /** Trang la React SPA va he thong demo cham - cho rong tay. */
  private static final int PAGE_LOAD_TIMEOUT = 60;

  public SalesInvoiceListPage(WebUI webUI) {
    super(webUI);
    setRepoName(SalesInvoiceListPage.class.getSimpleName());
  }

  @Step("Mo trang danh sach Sales Invoice")
  public SalesInvoiceListPage open() {
    webUI.navigateToUrl(LIST_URL);
    waitUntilLoaded();
    return this;
  }

  /**
   * Mo trang danh sach voi tham so loc tren URL.
   *
   * <p>WorkDo ho tro {@code ?search=<so hoa don>} - dung de tim lai dung ban ghi cua test giua
   * hang tram ban ghi cua nguoi khac tren he thong demo dung chung.
   */
  @Step("Mo danh sach va tim theo so hoa don '{0}'")
  public SalesInvoiceListPage openWithSearch(String invoiceNumber) {
    webUI.navigateToUrl(LIST_URL + "?search=" + invoiceNumber);
    waitUntilLoaded();
    return this;
  }

  @Step("Cho trang danh sach tai xong")
  public SalesInvoiceListPage waitUntilLoaded() {
    webUI.waitForElementVisible(findTestObject("LBL_TABLE_HEADER_INVOICE_NUMBER"),
        PAGE_LOAD_TIMEOUT);
    return this;
  }

  @Step("Kiem tra bang danh sach da hien thi")
  public boolean isLoaded() {
    return webUI.verifyElementVisible(findTestObject("LBL_TABLE_HEADER_INVOICE_NUMBER"));
  }

  @Step("Dem so dong trong bang")
  public int getRowCount() {
    List<WebElement> rows = webUI.findWebElements(findTestObject("TBL_ROWS"), PAGE_LOAD_TIMEOUT);
    return rows == null ? 0 : rows.size();
  }

  @Step("Lay so hoa don cua dong dau tien")
  public String getFirstRowInvoiceNumber() {
    return webUI.getText(findTestObject("TBL_FIRST_ROW_INVOICE_NUMBER"), PAGE_LOAD_TIMEOUT);
  }

}
