package vn.edu.vtiacademy.tests.mobile;

import io.qameta.allure.Step;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import vn.edu.vtiacademy.common.helper.Device;
import vn.edu.vtiacademy.common.keywords.MobileUI;
import vn.edu.vtiacademy.common.listeners.ScreenshotCapable;
import vn.edu.vtiacademy.screens.HomeScreen;

/**
 * Nen chung cho moi test mobile: khoi tao Appium + mo app, va don dep khi xong.
 *
 * <p><b>Vong doi:</b> app duoc mo MOT lan cho ca {@code <test>} trong suite XML
 * ({@code @BeforeTest}) chu khong phai moi test method mot lan. Ly do: khoi dong lai
 * Appium session tren emulator x86_64 mat 20-40 giay - nhan voi 6 test la mat them
 * ~4 phut moi lan chay ma khong doi lai duoc gia tri kiem thu nao. Doi lai, tung test
 * phai tu dua man hinh ve trang thai sach; xem {@code resetLoginForm()} o lop con.
 *
 * <p><b>{@code deviceName}</b> lay tu {@code <parameter>} trong suite XML, tro toi file
 * {@code src/main/resources/devices/<deviceName>.properties}. Muon chay tren may khac chi
 * can them mot file properties va mot the {@code <test>}, khong sua code.
 *
 * <p>Lop nay implement {@link ScreenshotCapable} de {@code TestListener} o {@code core}
 * tu dinh anh man hinh vao Allure khi test fail - listener khong can biet day la mobile
 * hay web.
 */
public class BaseMobileTest implements ScreenshotCapable {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseMobileTest.class);

  protected MobileUI mobileUI;
  protected HomeScreen homeScreen;

  @BeforeTest(alwaysRun = true)
  @Parameters(value = {"deviceName"})
  public void startApp(String deviceName) {
    LOGGER.info("Khoi dong app tren thiet bi '{}'", deviceName);
    // Phai tao Device TRUOC MobileUI: MobileUI doc cau hinh thiet bi ngay khi start app.
    new Device(deviceName);
    mobileUI = new MobileUI();
    mobileUI.startApplication();
    verifyAppStarted(deviceName);
    homeScreen = new HomeScreen(mobileUI);
  }

  /**
   * Dung ngay voi thong bao ro rang neu khong tao duoc phien Appium.
   *
   * <p><b>Vi sao can:</b> {@code MobileUI.startApplication()} boc toan bo qua trinh trong mot
   * {@code catch (Exception)} va chi ghi log roi di tiep. Neu phien khong tao duoc thi
   * {@code appiumDriver} van la {@code null}, va MOI test sau do do voi thong bao kieu
   * "phai hien thong bao loi email ... expected [true] but found [false]" - sau test do, sau
   * thong bao khong lien quan gi den nguyen nhan that. Da mat mot lan chay de nhan ra dieu do.
   *
   * <p>Nguyen nhan hay gap nhat: emulator vua bao {@code sys.boot_completed=1} nhung dich vu he
   * thong chua on dinh, Appium bao <i>"Appium Settings app is not running after 30000ms"</i>.
   * Cach xu ly la doi them roi chay lai, khong phai sua test.
   */
  private void verifyAppStarted(String deviceName) {
    byte[] screenshot = captureScreenshot();
    if (screenshot == null || screenshot.length == 0) {
      throw new IllegalStateException(
          "Khong tao duoc phien Appium tren thiet bi '" + deviceName + "'. "
              + "Moi test phia sau se do voi thong bao khong lien quan den nguyen nhan that, "
              + "nen dung ngay tai day. Cach kiem tra: (1) `adb devices` phai thay thiet bi o "
              + "trang thai `device`; (2) doi them ~30 giay sau khi emulator bao boot xong roi "
              + "chay lai; (3) doc log chi tiet trong mobile-tests/testlog/appium-*.log.");
    }
    LOGGER.info("Phien Appium da san sang tren '{}'", deviceName);
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
  public void stopApp() {
    if (mobileUI != null) {
      mobileUI.stopApplication();
    }
  }

  /**
   * {@inheritDoc}
   *
   * <p>Tra ve {@code null} khi chua co driver (test fail truoc khi app kip mo) - {@code
   * TestListener} hieu {@code null} la "chua chup duoc" va bo qua, thay vi lam hong ca
   * bao cao.
   */
  @Override
  public byte[] captureScreenshot() {
    if (mobileUI == null) {
      return null;
    }
    try {
      return mobileUI.takeScreenshot();
    } catch (Exception e) {
      LOGGER.warn("Khong chup duoc man hinh: {}", e.getMessage());
      return null;
    }
  }

  @Step("Mo man hinh Home")
  protected HomeScreen onHomeScreen() {
    return homeScreen;
  }
}
