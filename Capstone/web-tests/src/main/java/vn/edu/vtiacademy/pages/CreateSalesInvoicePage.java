package vn.edu.vtiacademy.pages;

import io.qameta.allure.Step;
import java.math.BigDecimal;
import java.util.List;
import org.openqa.selenium.WebElement;
import vn.edu.vtiacademy.common.keywords.WebUI;

/**
 * Form tao Sales Invoice ({@code /sales-invoices/create}).
 *
 * <p><b>Ba dac diem cua form nay quyet dinh cach viet page object - doc truoc khi sua:</b>
 *
 * <ol>
 *   <li><b>React render lai form sau moi lan chon dropdown.</b> Moi {@code WebElement} lay truoc
 *       do deu tro thanh {@code StaleElementReferenceException}. Vi vay o day KHONG cache
 *       {@code WebElement} nao - moi thao tac deu goi lai {@code webUI.<action>(locator)} de
 *       element duoc tim lai ngay truoc khi dung.
 *   <li><b>Dropdown la Radix Select, va PHAI dieu khien qua giao dien that.</b> Trong DOM co
 *       the {@code <select>} native an, va {@link org.openqa.selenium.support.ui.Select} dat
 *       duoc gia tri cho no. Nhung lam vay <b>khong dat gia tri cho form</b>: React van coi
 *       truong do la rong, va bam Create se khong gui gi len server. Cai bay o cho viec dat
 *       the an VAN kich hoat cac danh sach phu thuoc tai lai (chon Warehouse xong thi danh sach
 *       San pham co du lieu), nen moi thu trong nhu da chay dung. Bat buoc phai bam
 *       {@code <button role=combobox>} roi chon {@code <div role=option>}.
 *   <li><b>Mot lop phu de len nut Create.</b> Widget feedback cua ben thu ba
 *       ({@code #rt-feedback-widget-root}) nam dung tren nut. Bam bang toa do se im lang roi
 *       vao lop phu ma khong bao loi gi. Phai go lop phu roi bam that, xem
 *       {@link #clickCreate()}. Rieng lich chon ngay lai can pointer event that vi Radix
 *       Popover khong phan ung voi {@code click()} cua JavaScript.
 * </ol>
 */
public class CreateSalesInvoicePage extends BasePage {

  public static final String CREATE_URL = "https://dash-demo.workdo.io/sales-invoices/create";

  private static final int PAGE_LOAD_TIMEOUT = 60;

  /** Thoi gian cho React ve lai form sau moi lan chon dropdown. */
  private static final int REACT_RERENDER_DELAY_SECONDS = 3;

  /**
   * Ngay trong thang duoc chon lam Due Date.
   *
   * <p>Luon bam "thang sau" roi chon ngay 15: cach nay cho ra mot ngay den han o TUONG LAI du
   * chay vao bat cu ngay nao trong thang. Chon "ngay hom nay + n" se do vao cuoi thang, con
   * chon ngay co dinh cua thang hien tai se thanh ngay qua khu neu chay vao cuoi thang.
   */
  private static final String DUE_DATE_DAY_OF_MONTH = "15";

  /**
   * Thoi gian toi da cho server tra ve danh sach san pham cua MOT kho.
   *
   * <p>De ngan (15 giay) vi co the phai thu vai kho lien tiep - xem
   * {@link #selectWarehouseWithAvailableProducts()}.
   */
  private static final int PRODUCT_LIST_TIMEOUT = 15;

  /** So kho toi da se thu truoc khi bo cuoc. */
  private static final int MAX_WAREHOUSE_ATTEMPTS = 5;

  /** Timeout ngan khi kiem tra mot thu co the khong ton tai (lop phu cua widget). */
  private static final int OVERLAY_CHECK_TIMEOUT = 3;

  public CreateSalesInvoicePage(WebUI webUI) {
    super(webUI);
    setRepoName(CreateSalesInvoicePage.class.getSimpleName());
  }

  @Step("Mo form tao Sales Invoice")
  public CreateSalesInvoicePage open() {
    webUI.navigateToUrl(CREATE_URL);
    webUI.waitForElementVisible(findTestObject("BTN_CREATE"), PAGE_LOAD_TIMEOUT);
    return this;
  }

  // ---------------------------------------------------------------------------------
  // Nhap lieu
  // ---------------------------------------------------------------------------------

  /**
   * Chon Due Date = ngay 15 cua thang ke tiep.
   *
   * <p>Phai mo lich va bam that: hai o ngay la {@code <input type=hidden>}, React giu ngay trong
   * state rieng va khong doc lai tu the hidden. Set gia tri bang JavaScript se lam o hidden
   * "trong nhu da dien" - doc lai bang {@code getAttribute("value")} thay dung ngay mong muon -
   * nhung React van coi Due Date la rong, va bam Create khong gui gi len server. Da mat mot lan
   * chay de phat hien, va rat kho lan vi bang chung truc tiep (gia tri trong the hidden) lai
   * chi ra dieu nguoc lai.
   */
  @Step("Chon Due Date (ngay " + DUE_DATE_DAY_OF_MONTH + " thang sau)")
  public CreateSalesInvoicePage selectDueDateNextMonth() {
    webUI.scrollToElementAtCenterOfPage(findTestObject("BTN_DUE_DATE_PICKER"));
    // click (Actions) chu KHONG phai enhancedClick: Radix Popover doi pointer event that.
    webUI.click(findTestObject("BTN_DUE_DATE_PICKER"));
    webUI.waitForElementVisible(findTestObject("BTN_CALENDAR_NEXT_MONTH"), PAGE_LOAD_TIMEOUT);
    webUI.click(findTestObject("BTN_CALENDAR_NEXT_MONTH"));
    webUI.delayInSeconds(1);
    // Locator o ngay co ${param} -> phai qua findWebElement(locator, param) de thay tham so.
    webUI.clickOn(webUI.findWebElement(
        findTestObject("LBL_CALENDAR_DAY"), DUE_DATE_DAY_OF_MONTH));
    webUI.delayInSeconds(REACT_RERENDER_DELAY_SECONDS);
    return this;
  }

  @Step("Chon khach hang thu {0} trong danh sach")
  public CreateSalesInvoicePage selectCustomerByIndex(int index) {
    selectFromRadixDropdown(findTestObject("CBB_CUSTOMER_TRIGGER"), index);
    return this;
  }

  /**
   * Chon mot muc trong dropdown Radix dung nhu nguoi dung: bam the combobox, cho danh sach
   * hien ra, roi bam vao muc can chon.
   *
   * <p>Xem ghi chu o dau lop ve ly do KHONG duoc dung {@code Select} tren the an.
   *
   * @param triggerLocator locator cua {@code <button role=combobox>}
   * @param index vi tri muc can chon trong danh sach hien ra
   */
  private void selectFromRadixDropdown(String triggerLocator, int index) {
    webUI.scrollToElementAtCenterOfPage(triggerLocator);
    // Radix Popover chi mo khi nhan pointer event that - JavaScript click khong an gi.
    webUI.click(triggerLocator);
    if (!webUI.waitForElementVisible(findTestObject("LST_DROPDOWN_OPTIONS"), PAGE_LOAD_TIMEOUT)) {
      LOGGER.error("Danh sach cua dropdown '{}' khong mo ra", triggerLocator);
      return;
    }
    List<WebElement> options =
        webUI.findWebElements(findTestObject("LST_DROPDOWN_OPTIONS"), PAGE_LOAD_TIMEOUT);
    if (options == null || options.isEmpty()) {
      LOGGER.error("Dropdown '{}' mo ra nhung khong co muc nao", triggerLocator);
      return;
    }
    int safeIndex = Math.min(index, options.size() - 1);
    WebElement chosen = options.get(safeIndex);
    LOGGER.info("Chon muc thu {} trong {} muc: '{}'", safeIndex, options.size(), chosen.getText());
    webUI.clickOn(chosen);
    webUI.delayInSeconds(REACT_RERENDER_DELAY_SECONDS);
  }

  /**
   * Chon kho dau tien co san pham trong kho.
   *
   * <p><b>Vi sao khong chon dai mot kho:</b> danh sach san pham duoc LOC THEO TON KHO cua kho
   * da chon. Tren du lieu demo hien tai, kho dau tien ("Central Distribution Center") co dung
   * 0 san pham, cac kho sau co 25 / 19 / 13. Neu test chon cung kho[0] thi khong bao gio chon
   * duoc san pham, form thieu du lieu, va bam Create khong co gi xay ra - ung dung cung khong
   * bao loi gi (xem DEF-04 trong bao cao). Trieu chung rat kho lan vi khong co thong bao nao.
   *
   * <p>Do dai la du lieu cua he thong demo dung chung, hom nay kho nao co hang thi mai co the
   * khac. Vi vay test DO tim kho co hang thay vi ghi cung mot chi so - dung cach nay bo test
   * khong vo khi du lieu demo thay doi.
   */
  @Step("Chon kho dau tien con san pham")
  public CreateSalesInvoicePage selectWarehouseWithAvailableProducts() {
    for (int index = 0; index < MAX_WAREHOUSE_ATTEMPTS; index++) {
      selectFromRadixDropdown(findTestObject("CBB_WAREHOUSE_TRIGGER"), index);
      if (webUI.waitForElementPresent(findTestObject("CBB_PRODUCT_ROW1_OPTIONS"),
          PRODUCT_LIST_TIMEOUT)) {
        LOGGER.info("Kho thu {} co san pham - dung kho nay", index);
        return this;
      }
      LOGGER.info("Kho thu {} khong co san pham nao, thu kho tiep theo", index);
    }
    LOGGER.error("Da thu {} kho ma khong kho nao co san pham - du lieu demo co the da doi",
        MAX_WAREHOUSE_ATTEMPTS);
    return this;
  }



  /**
   * Chon san pham cho dong item dau tien.
   *
   * <p>Danh sach san pham duoc nap DONG sau khi chon Warehouse - goi ham nay truoc do se thay
   * mot {@code <select>} rong va khong chon duoc gi.
   */
  @Step("Chon san pham thu {0} cho dong item dau tien")
  public CreateSalesInvoicePage selectProductByIndex(int index) {
    // Cho tung <option> xuat hien thay vi delay co dinh: danh sach duoc goi ve tu server sau
    // khi chon Warehouse va thoi gian tra loi cua he thong demo dao dong tu 2 den hon 10 giay.
    if (!webUI.waitForElementPresent(findTestObject("CBB_PRODUCT_ROW1_OPTIONS"),
        PRODUCT_LIST_TIMEOUT)) {
      LOGGER.error("Danh sach san pham rong - hay dung selectWarehouseWithAvailableProducts()"
          + " thay vi chon kho theo chi so co dinh");
    }
    selectFromRadixDropdown(findTestObject("CBB_PRODUCT_ROW1_TRIGGER"), index);
    return this;
  }

  @Step("Nhap so luong = {0}")
  public CreateSalesInvoicePage inputQuantity(String quantity) {
    setReactNumberInput(findTestObject("TXT_QTY_ROW1"), quantity);
    return this;
  }

  @Step("Nhap don gia = {0}")
  public CreateSalesInvoicePage inputUnitPrice(String unitPrice) {
    setReactNumberInput(findTestObject("TXT_UNIT_PRICE_ROW1"), unitPrice);
    return this;
  }

  @Step("Nhap chiet khau = {0}%")
  public CreateSalesInvoicePage inputDiscountPercent(String discountPercent) {
    setReactNumberInput(findTestObject("TXT_DISCOUNT_ROW1"), discountPercent);
    return this;
  }

  /**
   * Dat gia tri cho mot o {@code <input type=number>} do React quan ly.
   *
   * <p><b>Vi sao khong dung {@code clearText} + {@code inputText} (tuc sendKeys):</b> o so lieu
   * la input CO KIEM SOAT cua React - gia tri hien thi do React quyet dinh, khong phai do the
   * input tu giu. {@code clear()} khong dong bo lai state cua React, va go tung phim vao mot
   * {@code type=number} lam React ghi de lai sau moi ky tu.
   *
   * <p>Hau qua rat de bi hieu nham la loi cua ung dung: go {@code "99.99"} thi o nhap ket thuc
   * voi gia tri <b>{@code "099"}</b> va khoi Invoice Summary hien {@code 99.00$}. Nhin vao ket
   * qua test thi giong het "ung dung cat mat phan thap phan cua don gia" - mot bao cao loi sai
   * suyt duoc gui di. Kiem chung bang cach dat gia tri theo cach duoi day thi ung dung tinh
   * dung {@code 99.99$}: <b>ung dung khong he co loi nay.</b>
   *
   * <p>Cach dung dung: goi ham {@code set} nguyen ban cua {@code HTMLInputElement.prototype}
   * (React da thay the {@code set} tren tung the) roi ban {@code input} va {@code change} de
   * React nhan duoc thay doi - dung cach React tu nhan su kien tu ban phim that.
   */
  private void setReactNumberInput(String locator, String value) {
    WebElement input = webUI.findWebElement(locator, PAGE_LOAD_TIMEOUT);
    if (input == null) {
      LOGGER.error("Khong tim thay o nhap so voi locator '{}'", locator);
      return;
    }
    ((org.openqa.selenium.JavascriptExecutor) webUI.getWebDriver()).executeScript(
        "const el = arguments[0];"
            + "const setter = Object.getOwnPropertyDescriptor("
            + "    window.HTMLInputElement.prototype, 'value').set;"
            + "setter.call(el, arguments[1]);"
            + "el.dispatchEvent(new Event('input', {bubbles: true}));"
            + "el.dispatchEvent(new Event('change', {bubbles: true}));",
        input, value);
    webUI.delayInSeconds(2);
  }

  @Step("Nhap ghi chu: {0}")
  public CreateSalesInvoicePage inputNotes(String notes) {
    webUI.clearText(findTestObject("TXA_NOTES"));
    webUI.inputText(findTestObject("TXA_NOTES"), notes);
    return this;
  }

  // ---------------------------------------------------------------------------------
  // Doc khoi Invoice Summary
  // ---------------------------------------------------------------------------------

  @Step("Doc Subtotal")
  public BigDecimal getSubtotal() {
    return parseMoney(webUI.getText(findTestObject("LBL_SUBTOTAL")));
  }

  @Step("Doc Discount")
  public BigDecimal getDiscount() {
    return parseMoney(webUI.getText(findTestObject("LBL_DISCOUNT")));
  }

  @Step("Doc Tax")
  public BigDecimal getTax() {
    return parseMoney(webUI.getText(findTestObject("LBL_TAX")));
  }

  @Step("Doc Total")
  public BigDecimal getTotal() {
    return parseMoney(webUI.getText(findTestObject("LBL_TOTAL")));
  }

  /**
   * Doi chuoi tien te cua WorkDo thanh so.
   *
   * <p>Ung dung hien tien dang {@code "1,234.50$"}, rieng Discount co dau tru dang
   * {@code "-0.00$"}. So sanh bang {@link BigDecimal} chu khong phai {@code double}: tien te
   * can so sanh chinh xac tung xu, va {@code 0.1 + 0.2 != 0.3} voi so thuc dau phay dong.
   *
   * @return so tien, hoac {@link BigDecimal#ZERO} neu khong doc duoc (loi da ghi log)
   */
  private BigDecimal parseMoney(String rawText) {
    if (rawText == null || rawText.isBlank()) {
      LOGGER.warn("Khong doc duoc gia tri tien te - tra ve 0");
      return BigDecimal.ZERO;
    }
    String digitsOnly = rawText.replace("$", "").replace(",", "").trim();
    try {
      return new BigDecimal(digitsOnly);
    } catch (NumberFormatException e) {
      LOGGER.warn("Chuoi '{}' khong phai so tien hop le - tra ve 0", rawText);
      return BigDecimal.ZERO;
    }
  }

  // ---------------------------------------------------------------------------------
  // Submit
  // ---------------------------------------------------------------------------------

  /**
   * Bam nut Create.
   *
   * <p><b>Phai go lop phu truoc.</b> Widget feedback cua ben thu ba
   * ({@code #rt-feedback-widget-root}) trai mot lop div len goc phai duoi, phu dung len nut
   * Create. Hau qua tuy cach bam ma khac nhau, va deu de danh lac huong:
   * <ul>
   *   <li>{@code element.click()} nem {@code ElementClickInterceptedException} - cach bao loi
   *       tu te nhat, nhung {@code WebUI} nuot exception nen chi thay test do khong ro ly do;
   *   <li>bam bang toa do (Actions) KHONG bao gi ca, cu the roi vao lop phu - form dung im,
   *       khong request, khong thong bao. Day la trieu chung ton nhieu thoi gian nhat de lan ra.
   * </ul>
   *
   * <p>Go lop phu la hop ly ve mat kiem thu: no la widget cua ben thu ba, khong thuoc nghiep vu
   * dang test, va tren he thong demo no dang loi (cac request cua no tra ve 403). Viec no che
   * mat nut chinh cua form da duoc ghi lai thanh mot defect rieng trong bao cao.
   */
  @Step("Bam nut Create")
  public CreateSalesInvoicePage clickCreate() {
    removeFeedbackWidgetOverlay();
    webUI.scrollToElementAtCenterOfPage(findTestObject("BTN_CREATE"));
    webUI.clickOn(webUI.findWebElement(findTestObject("BTN_CREATE"), PAGE_LOAD_TIMEOUT));
    return this;
  }

  /**
   * Go lop phu cua widget feedback ben thu ba de no khong an mat cu bam.
   *
   * <p>Tim bang locator trong object repository (khong ghi cung selector o day) va dung timeout
   * ngan: neu mot ngay nao do widget bien mat thi khong co ly do gi de doi no 30 giay.
   */
  private void removeFeedbackWidgetOverlay() {
    List<WebElement> overlays = webUI.findWebElements(
        findTestObject("PNL_FEEDBACK_WIDGET_OVERLAY"), OVERLAY_CHECK_TIMEOUT);
    if (overlays == null || overlays.isEmpty()) {
      LOGGER.debug("Khong thay lop phu cua widget feedback - khong can go");
      return;
    }
    ((org.openqa.selenium.JavascriptExecutor) webUI.getWebDriver())
        .executeScript("arguments[0].remove();", overlays.get(0));
    LOGGER.info("Da go lop phu cua widget feedback de bam duoc nut Create");
  }

  /** Bam Create va cho he thong chuyen ve trang danh sach. */
  @Step("Bam Create va cho luu xong")
  public SalesInvoiceListPage clickCreateAndWaitForList() {
    clickCreate();
    webUI.delayInSeconds(10);
    return new SalesInvoiceListPage(webUI);
  }

}
