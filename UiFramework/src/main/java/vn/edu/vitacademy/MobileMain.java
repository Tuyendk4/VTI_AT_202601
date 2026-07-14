package vn.edu.vitacademy;

import io.appium.java_client.AppiumDriver;
import java.net.MalformedURLException;
import vn.edu.vitacademy.common.keywords.MobileUI;

public class MobileMain {

  private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
  static AppiumDriver driver;

  private static final String TAB_LOGIN = "//android.widget.Button[@content-desc=\"Logins\"]";
  private static final String TXT_EMAIL = "//android.widget.EditText[@content-desc=\"input-email\"]";

  static void main() throws InterruptedException, MalformedURLException {
//    DesiredCapabilities dc = new DesiredCapabilities();
//    dc.setPlatform(Platform.IOS);
//    dc.setCapability("appium:automationName", "XCUITest");
//    dc.setCapability("appium:deviceName", "iPhone X");
//    dc.setCapability("appium:bundleId", "com.apple.weather");
//    dc.setCapability("appium:platformVersion", "16.7.16");
//    dc.setCapability("appium:udid", "d7ad0223e16d29a3587a96cf0cc45e4cbd9b9994");
//    driver = new IOSDriver(new URL(APPIUM_SERVER_URL), dc);

//    DesiredCapabilities dc = new DesiredCapabilities();
//    dc.setPlatform(Platform.ANDROID);
//    dc.setCapability("appium:automationName", "UiAutomator2");
//    dc.setCapability("appium:deviceName", "Redmi Note 9 Pro");
//    dc.setCapability("appium:app", "/Users/tuyenluu/training-workspace/VTI_AT_202601/UiFramework/apps/android.wdio.native.app.v2.2.0.apk");
//    dc.setCapability("appium:platformVersion", "12");
//    dc.setCapability("appium:udid", "80eabbae");
//    driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), dc);
//    Thread.sleep(60);
//    driver.quit();
    MobileUI mobileUI = new MobileUI();
//    mobileUI.startApplication("Android", "12",
//        "/Users/tuyenluu/training-workspace/VTI_AT_202601/UiFramework/apps/android.wdio.native.app.v2.2.0.apk",
//        "80eabbae", "Redmi Note 9 Pro");
//    mobileUI.startApplication("ios", "16.7.16", "d7ad0223e16d29a3587a96cf0cc45e4cbd9b9994",
//        "iPhone X", "com.apple.weather", "");
//    mobileUI.startApplication("Android", "12", "80eabbae",
//        "Redmi Note 9 Pro", "com.shopee.vn", "com.shopee.app.ui.home.HomeActivity_");
    mobileUI.startApplication();
    mobileUI.delayInSeconds(5);
    mobileUI.clickOn(TAB_LOGIN);
    mobileUI.inputText(TXT_EMAIL, "tuyenluu@vti.com.vn");
    mobileUI.delayInSeconds(10);
    mobileUI.clearText(TXT_EMAIL);
    mobileUI.delayInSeconds(10);
    mobileUI.stopApplication();
  }

}
