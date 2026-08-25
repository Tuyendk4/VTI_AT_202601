package vn.edu.vtiacademy.pages;

import io.qameta.allure.Step;
import vn.edu.vtiacademy.common.keywords.WebUI;

/**
 * Trang dang nhap cua WorkDo Dash SaaS.
 *
 * <p><b>Khong co o email/password.</b> {@code dash-demo.workdo.io} la ban demo cong khai:
 * nguoi dung vao thang workspace bang nut "Explore All Add-ons". Day khong phai thieu sot cua
 * bo test ma la dac diem cua he thong duoc test - va cung la ly do bo test nay khong co case
 * "dang nhap sai mat khau": khong co man hinh nhap mat khau de ma sai.
 *
 * <p>Trang co overlay "Please wait while we prepare your webapp..." moi lan tai, nen phai cho
 * nut hien ra chu khong duoc bam ngay.
 */
public class LoginPage extends BasePage {

  public static final String LOGIN_URL = "https://dash-demo.workdo.io/login";

  /** URL sau khi vao demo thanh cong - dung de xac nhan da vao duoc he thong. */
  private static final String DASHBOARD_URL_FRAGMENT = "/account";

  /** Thoi gian toi da cho moi lan thu vao workspace. */
  private static final int ENTER_TIMEOUT_SECONDS = 60;

  /**
   * So lan bam thu nut vao demo.
   *
   * <p>Trang login co overlay "Please wait while we prepare your webapp...". Doi khi nut da
   * hien va Selenium bao bam thanh cong, nhung ung dung chua gan xong su kien nen cu bam khong
   * dan di dau. Bam lai mot lan re hon nhieu so voi de ca bo test do.
   */
  private static final int MAX_ENTER_ATTEMPTS = 3;

  public LoginPage(WebUI webUI) {
    super(webUI);
    setRepoName(LoginPage.class.getSimpleName());
  }

  @Step("Mo trang dang nhap WorkDo")
  public LoginPage open() {
    webUI.navigateToUrl(LOGIN_URL);
    return this;
  }

  /**
   * Vao workspace demo bang nut "Explore All Add-ons".
   *
   * @return trang danh sach Sales Invoice de test viet duoc theo chuoi lien tiep
   */
  @Step("Vao workspace demo")
  public SalesInvoiceListPage enterDemoWorkspace() {
    for (int attempt = 1; attempt <= MAX_ENTER_ATTEMPTS; attempt++) {
      webUI.waitForElementClickable(findTestObject("BTN_EXPLORE_DEMO"), ENTER_TIMEOUT_SECONDS);
      webUI.click(findTestObject("BTN_EXPLORE_DEMO"));
      if (waitUntilLeftLoginPage()) {
        LOGGER.info("Da vao workspace o lan bam thu {}", attempt);
        return new SalesInvoiceListPage(webUI);
      }
      LOGGER.warn("Lan bam thu {} chua vao duoc workspace, van o {}", attempt, webUI.getUrl());
    }
    LOGGER.error("Khong vao duoc workspace sau {} lan bam", MAX_ENTER_ATTEMPTS);
    return new SalesInvoiceListPage(webUI);
  }

  /**
   * Cho den khi trinh duyet roi khoi trang login.
   *
   * <p>Cho theo TRANG THAI (URL da doi chua) thay vi cho mot khoang thoi gian co dinh:
   * {@code dash-demo.workdo.io} la he thong demo cong cong, thoi gian phan hoi dao dong tu
   * 2 den hon 10 giay. Mot lenh cho 8 giay co dinh vua qua cham khi he thong nhanh, vua
   * khong du khi he thong cham - va khi khong du thi moi test phia sau deu do voi thong bao
   * khong lien quan gi den nguyen nhan that.
   *
   * @return {@code true} neu da roi khoi trang login trong thoi gian cho phep
   */
  private boolean waitUntilLeftLoginPage() {
    long deadline = System.currentTimeMillis() + ENTER_TIMEOUT_SECONDS * 1000L;
    while (System.currentTimeMillis() < deadline) {
      String currentUrl = webUI.getUrl();
      if (currentUrl != null && !currentUrl.contains("/login")) {
        // Da chuyen trang, cho them mot nhip de React ve xong noi dung.
        webUI.delayInSeconds(3);
        return true;
      }
      webUI.delayInSeconds(2);
    }
    return false;
  }

  @Step("Kiem tra da vao duoc workspace")
  public boolean isInsideWorkspace() {
    return webUI.getUrl() != null && webUI.getUrl().contains(DASHBOARD_URL_FRAGMENT);
  }
}
