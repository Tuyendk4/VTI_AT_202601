package vn.edu.vitacademy.common.keywords;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vitacademy.common.helper.PropertyHelper;

public class MobileUI {

  private AppiumDriver appiumDriver;
  private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";

  private static final String NODE_EXECUTE_PATH = "/usr/local/bin/node";
  private static final String APPIUM_JS_PATH = "/usr/local/lib/node_modules/appium/build/lib/main.js";

  private static final String DEFAULT_IP_ADDRESS = "127.0.0.1";

  private static final String APPIUM_LOG_FILE_PATH = System.getProperty("user.dir") + File.separator + "testlog" + File.separator + "appium.log";

  private static final Logger LOGGER = LoggerFactory.getLogger(MobileUI.class);

  private static final int DEFAULT_TIMEOUT = PropertyHelper.getIntegerProperty("timeout", 30);

  private AppiumDriverLocalService appiumDriverLocalService;

  public void startApplication() {
    startAppiumServer();
    try {
      LOGGER.info("Starting application with path: {}", PropertyHelper.getProperty("appName"));
      DesiredCapabilities dc = new DesiredCapabilities();
      if (PropertyHelper.getProperty("platform").equalsIgnoreCase("ios")) {
        dc.setPlatform(Platform.IOS);
        dc.setCapability("appium:automationName", "XCUITest");
      } else {
        dc.setPlatform(Platform.ANDROID);
        dc.setCapability("appium:automationName", "UiAutomator2");
      }
      dc.setCapability("appium:deviceName", PropertyHelper.getProperty("deviceName"));
      dc.setCapability("appium:app", PropertyHelper.getProperty("appName"));
      dc.setCapability("appium:platformVersion", PropertyHelper.getProperty("platformVersion"));
      dc.setCapability("appium:udid", PropertyHelper.getProperty("udid"));
      if (PropertyHelper.getProperty("platform").equalsIgnoreCase("ios")) {
        appiumDriver = new IOSDriver(appiumDriverLocalService.getUrl(), dc);
      } else {
        appiumDriver = new AndroidDriver(appiumDriverLocalService.getUrl(), dc);
      }
      LOGGER.info("Application started successfully");
    } catch (Exception e) {
      LOGGER.error("Failed to start application {}. Root cause: {}", PropertyHelper.getProperty("appName"), e.getMessage());
    }
  }
  public void startApplication(String platformName, String platformVersion, String appPath,  String udid, String deviceName) {
    startAppiumServer();
    try {
      LOGGER.info("Starting application with path: {}", appPath);
      DesiredCapabilities dc = new DesiredCapabilities();
      if (platformName.equalsIgnoreCase("ios")) {
        dc.setPlatform(Platform.IOS);
        dc.setCapability("appium:automationName", "XCUITest");
      } else {
        dc.setPlatform(Platform.ANDROID);
        dc.setCapability("appium:automationName", "UiAutomator2");
      }
      dc.setCapability("appium:deviceName", deviceName);
      dc.setCapability("appium:app", appPath);
      dc.setCapability("appium:platformVersion", platformVersion);
      dc.setCapability("appium:udid", udid);
      if (platformName.equalsIgnoreCase("ios")) {
        appiumDriver = new IOSDriver(appiumDriverLocalService.getUrl(), dc);
      } else {
        appiumDriver = new AndroidDriver(appiumDriverLocalService.getUrl(), dc);
      }
      LOGGER.info("Application started successfully");
    } catch (Exception e) {
      LOGGER.error("Failed to start application {}. Root cause: {}", appPath, e.getMessage());
    }
  }

  public void startApplication(String platformName, String platformVersion,  String udid, String deviceName, String bundleIdOrAppPackage, String appActivity) {
    startAppiumServer();
    try {
      LOGGER.info("Starting application with with bundleIdOrAppPackage: {}, appActivity: {}", bundleIdOrAppPackage, appActivity);
      DesiredCapabilities dc = new DesiredCapabilities();
      if (platformName.equalsIgnoreCase("ios")) {
        dc.setPlatform(Platform.IOS);
        dc.setCapability("appium:automationName", "XCUITest");
        dc.setCapability("appium:bundleId", bundleIdOrAppPackage);
      } else {
        dc.setPlatform(Platform.ANDROID);
        dc.setCapability("appium:automationName", "UiAutomator2");
        dc.setCapability("appium:appPackage", bundleIdOrAppPackage);
        dc.setCapability("appium:appActivity", appActivity);
      }
      dc.setCapability("appium:deviceName", deviceName);
      dc.setCapability("appium:platformVersion", platformVersion);
      dc.setCapability("appium:udid", udid);
      if (platformName.equalsIgnoreCase("ios")) {
        appiumDriver = new IOSDriver(new URL(APPIUM_SERVER_URL), dc);
      } else {
        appiumDriver = new AndroidDriver(new URL(APPIUM_SERVER_URL), dc);
      }
      LOGGER.info("Application started successfully");
    } catch (Exception e) {
      LOGGER.error("Failed to start application with bundleIdOrAppPackage: {}, appActivity: {}. Root cause: {}", bundleIdOrAppPackage, appActivity, e.getMessage());
    }
  }

  public void delayInSeconds(int seconds) {
    try {
      LOGGER.info("Delaying for {} seconds", seconds);
      Thread.sleep(seconds * 1000L);
      LOGGER.info("Delay completed");
    } catch (InterruptedException e) {
      LOGGER.error("Delay interrupted. Root cause: {}", e.getMessage());
    }
  }

  public void delayInMilliseconds(int milliseconds) {
    try {
      LOGGER.info("Delaying for {} milliseconds", milliseconds);
      Thread.sleep(milliseconds);
      LOGGER.info("Delay completed");
    } catch (InterruptedException e) {
      LOGGER.error("Delay interrupted. Root cause: {}", e.getMessage());
    }
  }

  public void stopApplication() {
    try {
      LOGGER.info("Stopping application");
      appiumDriver.quit();
      LOGGER.info("Application stopped successfully");
    } catch (Exception e) {
      LOGGER.error("Failed to stop application. Root cause: {}", e.getMessage());
    }
    stopAppiumServer();
  }

  private By findBy(String locator) {
    String prefix = StringUtils.substringBefore(locator, ":");
    String locatorValue = StringUtils.substringAfter(locator, ":");
    switch (prefix.toLowerCase()) {
      case "id":
        return By.id(locatorValue);
      case "name":
        return By.name(locatorValue);
      case "css":
        return By.cssSelector(locatorValue);
      case "xpath":
        return By.xpath(locatorValue);
      case "class":
        return By.className(locatorValue);
      case "link_text":
        return By.linkText(locatorValue);
      case "partial_link_text":
        return By.partialLinkText(locatorValue);
      case "tag":
        return By.tagName(locatorValue);
      default:
        return By.xpath(locator);
    }
  }

  public WebElement findMobileElement(String locator, int... timeout) {

    int waitTime = timeout.length > 0 ? timeout[0] : DEFAULT_TIMEOUT;
    try {
      LOGGER.info("Finding mobile element located by '{}' within {} second(s)", locator, waitTime);
      Wait<WebDriver> wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(waitTime));
      WebElement we = wait.until(ExpectedConditions.presenceOfElementLocated(findBy(locator)));
//      endTime = System.currentTimeMillis();
      if (we != null) {
        LOGGER.info("Found 1 mobile element located by '{}'", locator);
//        totalTime = (endTime - startTime) / 1000.0;
//        LOGGER.info("Total time {}", totalTime);
        return we;
      }
    } catch (Exception e) {
//      endTime = System.currentTimeMillis();
      LOGGER.error("Failed to find mobile element locate by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
//    totalTime = (endTime - startTime) / 1000.0;
//    LOGGER.info("Total time {}", totalTime);
    return null;
  }

  public WebElement findMobileElement(String locator, String param) {
    locator = StringUtils.replace(locator, "${param}", param);
    try {
      LOGGER.info("Finding mobile element located by '{}'", locator);
      WebElement we = appiumDriver.findElement(findBy(locator));
      if (we != null) {
        LOGGER.info("Found 1 mobile element located by '{}'", locator);
        return we;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to find mobile element locate by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
    return null;
  }

  public WebElement findMobileElement(By by) {
    try {
      LOGGER.info("Finding mobile element located by '{}'", by);
      WebElement we = appiumDriver.findElement(by);
      if (we != null) {
        LOGGER.info("Found 1 mobile element located by '{}'", by);
        return we;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to find mobile element locate by '{}'. Root cause: {}", by, e.getMessage());
    }
    return null;
  }

  public List<WebElement> findMobileElements(String locator, int... timeout) {
    int waitTime = timeout.length > 0 ? timeout[0] : DEFAULT_TIMEOUT;
    try {
      LOGGER.info("Finding mobile elements located by '{}' within {} second(s)", locator, waitTime);
      Wait<WebDriver> wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(waitTime));
      List<WebElement> wes = wait.until(
          ExpectedConditions.presenceOfAllElementsLocatedBy(findBy(locator)));
      if (wes != null) {
        LOGGER.info("Found {} mobile elements located by '{}'", wes.size(), locator);
        return wes;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to find mobile elements located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
    return null;
  }

  public void inputText(String locator, String text, int... timeout) {
    WebElement we = findMobileElement(locator, timeout);
    try {
      LOGGER.info("Inputting text '{}' into mobile element located by '{}'", text, locator);
      we.sendKeys(text);
      LOGGER.info("Inputted text '{}' into mobile element located by '{}' successfully", text,
          locator);
    } catch (Exception e) {
      LOGGER.error("Failed to input text. Root cause: {}", e.getMessage());
    }
  }

  public void inputText(WebElement we, String text, int... timeout) {
    try {
      LOGGER.info("Inputting text '{}' into mobile element '{}'", text, we);
      we.sendKeys(text);
      LOGGER.info("Inputted text '{}' into mobile element '{}' successfully", text,
          we);
    } catch (Exception e) {
      LOGGER.error("Failed to input text in mobile element '{}'. Root cause: {}", we, e.getMessage());
    }
  }

  public void clearText(String locator, int... timeout) {
    WebElement we = findMobileElement(locator, timeout);
    try {
      LOGGER.info("Clearing text in element located by '{}'", locator);
      we.clear();
      LOGGER.info("Text cleared");
    } catch (Exception e) {
      LOGGER.error("Failed to clear text. Root cause: {}", e.getMessage());
    }
  }

  public void clearText(WebElement we, int... timeout) {
    try {
      LOGGER.info("Clearing text in web element '{}'", we);
      we.clear();
      LOGGER.info("Text cleared in web element '{}' successfully", we);
    } catch (Exception e) {
      LOGGER.error("Failed to clear text in web element '{}'. Root cause: {}", we, e.getMessage());
    }
  }

  public void clickOn(String locator, int... timeout) {
    WebElement we = findMobileElement(locator, timeout);
    try {
      LOGGER.info("Clicking on mobile element located by '{}'", locator);
      we.click();
      LOGGER.info("Clicked on mobile element located by '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to click on mobile element located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public void clickOn(WebElement we) {
    try {
      LOGGER.info("Clicking on mobile element '{}'", we);
      we.click();
      LOGGER.info("Clicked on mobile element '{}'", we);
    } catch (Exception e) {
      LOGGER.error("Failed to click on mobile element '{}'. Root cause: {}", we, e.getMessage());
    }
  }

  public void startAppiumServer() {
    try {
      LOGGER.info("Starting Appium server");
      AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
      appiumServiceBuilder.usingDriverExecutable(new File(NODE_EXECUTE_PATH))
          .withAppiumJS(new File(APPIUM_JS_PATH))
          .withIPAddress(DEFAULT_IP_ADDRESS)
          .usingAnyFreePort()
          .withLogFile(new File(APPIUM_LOG_FILE_PATH));
      appiumDriverLocalService = AppiumDriverLocalService.buildService(appiumServiceBuilder);
      appiumDriverLocalService.start();
      LOGGER.info("Appium server started");
    } catch (Exception e) {
      LOGGER.error("Failed to start Appium server. Root cause: {}", e.getMessage());
    }
  }

  public void stopAppiumServer() {
    try {
      LOGGER.info("Stopping Appium server");
      appiumDriverLocalService.stop();
      LOGGER.info("Appium server stopped");
    } catch (Exception e) {
      LOGGER.info("Failed to stop Appium server. Root cause: {}", e.getMessage());
    }
  }
}
