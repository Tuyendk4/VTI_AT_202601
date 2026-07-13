package vn.edu.vitacademy.common.keywords;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
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

public class MobileUI {

  private AppiumDriver appiumDriver;
  private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";

  private static final Logger LOGGER = LoggerFactory.getLogger(MobileUI.class);

  private static final int DEFAULT_TIMEOUT = 30;

  public void startApplication(String platformName, String platformVersion, String appPath,  String udid, String deviceName) {
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
        appiumDriver = new IOSDriver(new URL(APPIUM_SERVER_URL), dc);
      } else {
        appiumDriver = new AndroidDriver(new URL(APPIUM_SERVER_URL), dc);
      }
      LOGGER.info("Application started successfully");
    } catch (Exception e) {
      LOGGER.error("Failed to start application {}. Root cause: {}", appPath, e.getMessage());
    }
  }

  public void startApplication(String platformName, String platformVersion,  String udid, String deviceName, String bundleIdOrAppPackage, String appActivity) {
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
}
