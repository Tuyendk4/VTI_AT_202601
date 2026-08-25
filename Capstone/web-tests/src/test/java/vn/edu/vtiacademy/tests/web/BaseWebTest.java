package vn.edu.vtiacademy.tests.web;

import java.lang.reflect.Method;
import org.openqa.selenium.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import vn.edu.vtiacademy.common.keywords.WebUI;
import vn.edu.vtiacademy.common.listeners.ScreenshotCapable;
import vn.edu.vtiacademy.pages.LoginPage;
import vn.edu.vtiacademy.pages.SalesInvoiceListPage;

/**
 * Nen chung cho moi test web: mo trinh duyet, vao workspace demo mot lan, don dep khi xong.
 *
 * <p><b>Vi sao dang nhap MOT lan cho ca {@code <test>} thay vi moi test method:</b> vao demo
 * WorkDo mat 15-20 giay (trang cham + overlay chuan bi webapp). Lam lai cho tung test la doi
 * them vai phut moi lan chay ma khong kiem tra them duoc gi. Doi lai, moi test phai tu mo
 * trang no can tu dau chu khong duoc dua vao trang thai test truoc de lai.
 *
 * <p><b>{@code browser} lay tu suite XML</b> nen chay headless (dong goi/CI) hay headed (nhin
 * tan mat luc go loi) chi khac mot tham so, khong sua code:
 * {@code mvn -pl web-tests test -Dbrowser=CHROME}.
 *
 * <p>Implement {@link ScreenshotCapable} de {@code TestListener} o {@code core} tu dinh anh
 * man hinh vao Allure khi test fail.
 */
public class BaseWebTest implements ScreenshotCapable {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseWebTest.class);

  protected WebUI webUI;
  protected SalesInvoiceListPage salesInvoiceListPage;

  @BeforeTest(alwaysRun = true)
  @Parameters({"browser"})
  public void openBrowserAndEnterDemo(@Optional("CHROME_HEADLESS") String browser) {
    LOGGER.info("Mo trinh duyet: {}", browser);
    webUI = new WebUI();
    webUI.openBrowser(browser, LoginPage.LOGIN_URL);
    setViewportSize();

    LoginPage loginPage = new LoginPage(webUI);
    salesInvoiceListPage = loginPage.enterDemoWorkspace();
    if (!loginPage.isInsideWorkspace()) {
      LOGGER.warn("Chua chac da vao duoc workspace. URL hien tai: {}", webUI.getUrl());
    }
  }

  /**
   * Ep cua so ve kich thuoc desktop co dinh.
   *
   * <p>Chrome headless mac dinh mo o 800x600 va {@code maximizeWindow()} khong an gi khi chay
   * headless. O 800x600 thi WorkDo doi sang bo cuc thu hep: menu thanh dang thu gon, cac nut
   * bi day xuong duoi hoac bi che - nut "Explore All Add-ons" van "bam duoc" theo bao cao cua
   * Selenium nhung khong dan di dau ca, va test do o buoc dau tien voi thong bao vo nghia.
   *
   * <p>Dat kich thuoc tuong minh cung lam bo test on dinh hon giua may nay va may khac:
   * ket qua khong con phu thuoc do phan giai man hinh cua nguoi chay.
   */
  private void setViewportSize() {
    try {
      webUI.getWebDriver().manage().window().setSize(new Dimension(1920, 1200));
    } catch (Exception e) {
      LOGGER.warn("Khong dat duoc kich thuoc cua so: {}", e.getMessage());
    }
  }

  @BeforeMethod(alwaysRun = true)
  public void logTestStart(Method method) {
    LOGGER.info("=== BAT DAU {} ===", method.getName());
  }

  @AfterMethod(alwaysRun = true)
  public void logTestEnd(Method method) {
    LOGGER.info("=== KET THUC {} ===", method.getName());
  }

  @AfterTest(alwaysRun = true)
  public void closeBrowser() {
    if (webUI != null) {
      webUI.closeBrowser();
    }
  }

  @Override
  public byte[] captureScreenshot() {
    if (webUI == null) {
      return null;
    }
    try {
      return webUI.attachmentScreenshot();
    } catch (Exception e) {
      LOGGER.warn("Khong chup duoc man hinh: {}", e.getMessage());
      return null;
    }
  }
}
