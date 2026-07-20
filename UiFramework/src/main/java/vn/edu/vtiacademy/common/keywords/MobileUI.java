package vn.edu.vtiacademy.common.keywords;

import static org.openqa.selenium.support.ui.ExpectedConditions.not;

import com.google.common.collect.ImmutableList;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.qameta.allure.Attachment;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vtiacademy.common.helper.Configuration;
import vn.edu.vtiacademy.common.helper.Device;
import vn.edu.vtiacademy.common.helper.PropertyHelper;

public class MobileUI {

  private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
  private static final String DEFAULT_IP_ADDRESS = "127.0.0.1";

  //  private static final String NODE_EXECUTE_PATH = "/usr/local/bin/node";
//  private static final String APPIUM_JS_PATH = "/usr/local/lib/node_modules/appium/build/lib/main.js";
  private static final String APPIUM_LOG_FILE_PATH =
      System.getProperty("user.dir") + File.separator + "testlog" + File.separator + "appium.log";
  private static final Logger LOGGER = LoggerFactory.getLogger(MobileUI.class);
  private final int defaultTimeout;
  private final String nodePath;
  private final String appiumPath;
  private final String appiumJsPath;
  private final Configuration configuration;
  private AppiumDriver appiumDriver;
  private AppiumDriverLocalService appiumDriverLocalService;

  public MobileUI() {
    configuration = new Configuration("configuration");
    defaultTimeout = configuration.getTimeout();
    nodePath = configuration.getNodePath();
    appiumPath = configuration.getAppiumPath();
    appiumJsPath =
        appiumPath + File.separator + "build" + File.separator + "lib" + File.separator + "main.js";
  }

  public void startApplication() {
    startAppiumServer();
    try {
      LOGGER.info("Starting application with path: {}", Device.getAppPath());
      DesiredCapabilities dc = new DesiredCapabilities();
      if (Device.getPlatformName().equalsIgnoreCase("ios")) {
        dc.setPlatform(Platform.IOS);
        dc.setCapability("appium:automationName", "XCUITest");
      } else {
        dc.setPlatform(Platform.ANDROID);
        dc.setCapability("appium:automationName", "UiAutomator2");
      }
      dc.setCapability("appium:deviceName", Device.getDeviceName());
      if (Device.getAppPath() != null && !Device.getAppPath().isEmpty()) {
        dc.setCapability("appium:app", Device.getAppPath());
        dc.setCapability("appium:noReset", false);
        dc.setCapability("appium:fullReset", true);
      }
      if (Device.getPlatformName().equalsIgnoreCase("ios")) {
        dc.setPlatform(Platform.IOS);
        dc.setCapability("appium:automationName", "XCUITest");
        dc.setCapability("appium:bundleId", Device.getBundleId());
      } else {
        dc.setPlatform(Platform.ANDROID);
        dc.setCapability("appium:automationName", "UiAutomator2");
        dc.setCapability("appium:appPackage", Device.getAppPackage());
        dc.setCapability("appium:appActivity", Device.getAppActivity());
      }
      dc.setCapability("appium:platformVersion", Device.getPlatformVersion());
      dc.setCapability("appium:udid", Device.getUdid());
      if (Device.getPlatformName().equalsIgnoreCase("ios")) {
        appiumDriver = new IOSDriver(appiumDriverLocalService.getUrl(), dc);
      } else {
        appiumDriver = new AndroidDriver(appiumDriverLocalService.getUrl(), dc);
      }
      LOGGER.info("Application started successfully");
    } catch (Exception e) {
      LOGGER.error("Failed to start application {}. Root cause: {}",
          PropertyHelper.getProperty("appName"), e.getMessage());
    }
  }

  public void startApplication(String platformName, String platformVersion, String appPath,
      String udid, String deviceName) {
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

  public void startApplication(String platformName, String platformVersion, String udid,
      String deviceName, String bundleIdOrAppPackage, String appActivity) {
    startAppiumServer();
    try {
      LOGGER.info("Starting application with with bundleIdOrAppPackage: {}, appActivity: {}",
          bundleIdOrAppPackage, appActivity);
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
      LOGGER.error(
          "Failed to start application with bundleIdOrAppPackage: {}, appActivity: {}. Root cause: {}",
          bundleIdOrAppPackage, appActivity, e.getMessage());
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
      case "xpath":
        return By.xpath(locatorValue);
      case "class":
        return By.className(locatorValue);
      default:
        return By.xpath(locator);
    }
  }

  public WebElement findMobileElement(String locator, int... timeout) {

    int waitTime = timeout.length > 0 ? timeout[0] : defaultTimeout;
    try {
      LOGGER.info("Finding mobile element located by '{}' within {} second(s)", locator, waitTime);
      Wait<WebDriver> wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(waitTime));
      WebElement we = wait.until(ExpectedConditions.presenceOfElementLocated(findBy(locator)));
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
      LOGGER.error("Failed to find mobile element locate by '{}'. Root cause: {}", by,
          e.getMessage());
    }
    return null;
  }

  public List<WebElement> findMobileElements(String locator, int... timeout) {
    int waitTime = timeout.length > 0 ? timeout[0] : defaultTimeout;
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
      LOGGER.error("Failed to input text in mobile element '{}'. Root cause: {}", we,
          e.getMessage());
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
      appiumServiceBuilder.usingDriverExecutable(new File(nodePath))
          .withAppiumJS(new File(appiumJsPath))
          .withIPAddress(DEFAULT_IP_ADDRESS)
          .usingAnyFreePort()
          .withArgument(() -> "--allow-insecure", "*:chromedriver_autodownload")
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

  public String getText(String locator, int... timeout) {
    WebElement we = findMobileElement(locator, timeout);
    try {
      LOGGER.info("Getting text from web element located by '{}'", locator);
      String text = we.getText();
      LOGGER.info("Retrieved text '{}' from web element located by '{}'", text, locator);
      return text;
    } catch (Exception e) {
      LOGGER.error("Failed to get text from web element located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
    return null;
  }

  public String getTagName(String locator, int... timeout) {
    WebElement we = findMobileElement(locator, timeout);
    try {
      LOGGER.info("Getting tag name from web element located by '{}'", locator);
      String tagName = we.getTagName();
      LOGGER.info("Retrieved tag name '{}' from web element located by '{}'", tagName, locator);
      return tagName;
    } catch (Exception e) {
      LOGGER.error("Failed to get tag name from web element located by '{}'. Root cause: {}",
          locator, e.getMessage());
    }
    return null;
  }

  public String getAttributeValue(String locator, String attributeName, int... timeout) {
    WebElement we = findMobileElement(locator, timeout);
    try {
      LOGGER.info("Getting attribute value from web element located by '{}' for attribute '{}'",
          locator, attributeName);
      String attributeValue = we.getAttribute(attributeName);
      LOGGER.info(
          "Retrieved attribute value '{}' from web element located by '{}' for attribute '{}'",
          attributeValue, locator, attributeName);
      return attributeValue;
    } catch (Exception e) {
      LOGGER.error("Failed to get attribute value from web element located by '{}'. Root cause: {}",
          locator, e.getMessage());
    }
    return null;
  }

  public int getElementHeigh(String locator, int... timeout) {
    WebElement we = findMobileElement(locator, timeout);
    try {
      LOGGER.info("Getting height of web element located by '{}'", locator);
      int height = we.getSize().getHeight();
      LOGGER.info("Retrieved height '{}' from web element located by '{}'", height, locator);
      return height;
    } catch (Exception e) {
      LOGGER.error("Failed to get height from web element located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
    return -1;
  }

  public int getElementWidth(String locator) {
    WebElement we = findMobileElement(locator);
    try {
      LOGGER.info("Getting width of web element located by '{}'", locator);
      int width = we.getSize().getWidth();
      LOGGER.info("Retrieved width '{}' from web element located by '{}'", width, locator);
      return width;
    } catch (Exception e) {
      LOGGER.error("Failed to get width from web element located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
    return -1;
  }

  public int getHorizontalPosition(String locator) {
    WebElement we = findMobileElement(locator);
    try {
      LOGGER.info("Getting horizontal position of web element located by '{}'", locator);
      int position = we.getLocation().getX();
      LOGGER.info("Retrieved horizontal position '{}' from web element located by '{}'", position,
          locator);
      return position;
    } catch (Exception e) {
      LOGGER.error(
          "Failed to get horizontal position from web element located by '{}'. Root cause: {}",
          locator, e.getMessage());
    }
    return -1;
  }

  public int getVerticalPosition(String locator) {
    WebElement we = findMobileElement(locator);
    try {
      LOGGER.info("Getting vertical position of web element located by '{}'", locator);
      int position = we.getLocation().getY();
      LOGGER.info("Retrieved vertical position '{}' from web element located by '{}'", position,
          locator);
      return position;
    } catch (Exception e) {
      LOGGER.error(
          "Failed to get vertical position from web element located by '{}'. Root cause: {}",
          locator, e.getMessage());
      return -1;
    }
  }

  public boolean verifyElementText(String locator, String expectedText) {
    WebElement we = findMobileElement(locator);
    try {
      LOGGER.info("Verifying text of web element located by '{}'", locator);
      String actualText = we.getText();
      if (actualText.equals(expectedText)) {
        LOGGER.info("Actual text '{}' and expected text '{}' are the same", actualText,
            expectedText);
        return true;
      }
      LOGGER.error("Actual text '{}' and expected text '{}' are not the same", actualText,
          expectedText);
    } catch (Exception e) {
      LOGGER.error("Failed to verify text of web element located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
    return false;
  }

  public boolean verifyElementText(WebElement we, String expectedText) {
    try {
      LOGGER.info("Verifying text of web element '{}'", we);
      String actualText = we.getText();
      if (actualText.equals(expectedText)) {
        LOGGER.info("Actual text '{}' and expected text '{}' are the same", actualText,
            expectedText);
        return true;
      }
      LOGGER.error("Actual text '{}' and expected text '{}' are not the same", actualText,
          expectedText);
    } catch (Exception e) {
      LOGGER.error("Failed to verify text of web element '{}'. Root cause: {}", we, e.getMessage());
    }
    return false;
  }

  public boolean verifyElementContainsText(String locator, String expectedText) {
    WebElement we = findMobileElement(locator);
    try {
      LOGGER.info("Verifying text of web element located by '{}'", locator);
      String actualText = we.getText();
      if (actualText.contains(expectedText)) {
        LOGGER.info("Actual text '{}' contains expected text '{}'", actualText, expectedText);
        return true;
      }
      LOGGER.error("Actual text '{}' does not contain expected text '{}'", actualText,
          expectedText);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to verify to contain text of web element located by '{}'. Root cause: {}",
          locator, e.getMessage());
    }
    return false;
  }

  public boolean verifyElementVisible(String locator) {
    WebElement we = findMobileElement(locator);
    try {
      LOGGER.info("Verifying visibility of web element located by '{}'", locator);
      if (we.isDisplayed()) {
        LOGGER.info("Web element located by '{}' is visible", locator);
        return true;
      }
      LOGGER.error("Web element located by '{}' is not visible", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to verify visibility of web element located by '{}'. Root cause: {}",
          locator, e.getMessage());
    }
    return false;
  }

  public boolean verifyElementNotVisible(String locator) {
    WebElement we = findMobileElement(locator);
    try {
      LOGGER.info("Verifying invisibility of web element located by '{}'", locator);
      if (!we.isDisplayed()) {
        LOGGER.info("Web element located by '{}' is not visible", locator);
        return true;
      }
      LOGGER.error("Web element located by '{}' is visible", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to verify invisibility of web element located by '{}'. Root cause: {}",
          locator, e.getMessage());
    }
    return false;
  }

  public boolean verifyElementPresent(String locator) {
    WebElement we = findMobileElement(locator);
    if (we != null) {
      LOGGER.info("Web element located by '{}' is present", locator);
      return true;
    }
    LOGGER.error("Web element located by '{}' is not present", locator);
    return false;
  }

  public boolean verifyElementNotPresent(String locator) {
    WebElement we = findMobileElement(locator);
    if (we == null) {
      LOGGER.info("Web element located by '{}' is not present", locator);
      return true;
    }
    LOGGER.error("Web element located by '{}' is present", locator);
    return false;
  }

  public boolean waitForElementPresent(String locator, int timeout) {
    try {
      LOGGER.info("Waiting for web element located by '{}' to be present within {} second(s)",
          locator, timeout);
      Wait<WebDriver> wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(timeout));
      WebElement we = wait.until(ExpectedConditions.presenceOfElementLocated(findBy(locator)));
      if (we != null) {
        LOGGER.info("Web element located by '{}' is present", locator);
        return true;
      }
      LOGGER.error("Web element located by '{}' is not present within {} second(s)", locator,
          timeout);
    } catch (Exception e) {
      LOGGER.error("Failed to wait for web element located by '{}' to be present. Root cause: {}",
          locator, e.getMessage());
    }
    return false;
  }

  public boolean waitForElementNotPresent(String locator, int timeout) {
    try {
      LOGGER.info("Waiting for web element located by '{}' to be not present within {} second(s)",
          locator, timeout);
      Wait<WebDriver> wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(timeout));
      boolean isNotPresent = wait.until(
          not(ExpectedConditions.presenceOfElementLocated(findBy(locator))));
      if (isNotPresent) {
        LOGGER.info("Web element located by '{}' is not present within {} second(s)", locator,
            timeout);
        return true;
      }
      LOGGER.error("Web element located by '{}' is still present after {} second(s)", locator,
          timeout);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to wait for web element located by '{}' to be not present. Root cause: {}",
          locator, e.getMessage());
    }
    return false;
  }

  public boolean waitForElementVisible(String locator, int timeout) {
    try {
      LOGGER.info("Waiting for web element located by '{}' to be visible within {} second(s)",
          locator, timeout);
      Wait<WebDriver> wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(timeout));
      WebElement we = wait.until(ExpectedConditions.visibilityOfElementLocated(findBy(locator)));
      if (we != null) {
        LOGGER.info("Web element located by '{}' is visible", locator);
        return true;
      }
      LOGGER.error("Web element located by '{}' is not visible within {} second(s)", locator,
          timeout);
    } catch (Exception e) {
      LOGGER.error("Failed to wait for web element located by '{}' to be visible. Root cause: {}",
          locator, e.getMessage());
    }
    return false;
  }

  public boolean waitForElementVisible(WebElement we, int timeout) {
    try {
      LOGGER.info("Waiting for web element '{}' to be visible within {} second(s)",
          we, timeout);
      Wait<WebDriver> wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(timeout));
      WebElement foundElement = wait.until(ExpectedConditions.visibilityOf(we));
      if (foundElement != null) {
        LOGGER.info("Web element '{}' is visible", we);
        return true;
      }
      LOGGER.error("Web element '{}' is not visible within {} second(s)", we,
          timeout);
    } catch (Exception e) {
      LOGGER.error("Failed to wait for web element '{}' to be visible. Root cause: {}",
          we, e.getMessage());
    }
    return false;
  }

  public boolean waitForElementInvisible(String locator, int timeout) {
    try {
      LOGGER.info("Waiting for mobile element located by '{}' to be invisible within {} second(s)",
          locator, timeout);
      Wait<WebDriver> wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(timeout));
      boolean isNotVisible = wait.until(
          ExpectedConditions.invisibilityOfElementLocated(findBy(locator)));
      if (isNotVisible) {
        LOGGER.info("Mobile element located by '{}' is invisible within {} second(s)", locator,
            timeout);
        return true;
      }
      LOGGER.error("Mobile element located by '{}' is still visible after {} second(s)", locator,
          timeout);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to wait for mobile element located by '{}' to be invisible. Root cause: {}",
          locator, e.getMessage());
    }
    return false;
  }

  public boolean waitForElementClickable(String locator, int timeout) {
    try {
      LOGGER.info("Waiting for mobile element located by '{}' to be clickable within {} second(s)",
          locator, timeout);
      Wait<WebDriver> wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(timeout));
      WebElement we = wait.until(ExpectedConditions.elementToBeClickable(findBy(locator)));
      if (we != null) {
        LOGGER.info("Mobile element located by '{}' is clickable", locator);
        return true;
      }
      LOGGER.error("Mobile element located by '{}' is not clickable within {} second(s)", locator,
          timeout);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to wait for mobile element located by '{}' to be clickable. Root cause: {}",
          locator, e.getMessage());
    }
    return false;
  }

  public void tapOn(Point point) {
    try {
      LOGGER.info("Tapping on point '{}, {}'", point.getX(), point.getY());
      PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
      Sequence sequence = new Sequence(input, 0);
      sequence.addAction(
          input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), point.getX(),
              point.getY()));
      sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
      sequence.addAction(new Pause(input, Duration.ofMillis(300)));
      sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
      appiumDriver.perform(ImmutableList.of(sequence));
      LOGGER.info("Tapped on point '{}, {}'", point.getX(), point.getY());
    } catch (Exception e) {
      LOGGER.error("Failed to tap on point '{}'. Root cause: {}", point, e.getMessage());
    }
  }

  public void tapOn(String locator, int... timeOut) {
    WebElement we = findMobileElement(locator, timeOut);
    try {
      LOGGER.info("Tapping on mobile element located by '{}'", locator);
      Rectangle rect = we.getRect();
      Point point = new Point(rect.getX() + rect.getWidth() / 2,
          rect.getY() + rect.getHeight() / 2);
      tapOn(point);
      LOGGER.info("Tapped on mobile element located by '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to tap on mobile element located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public void tapOn(WebElement we) {
    try {
      LOGGER.info("Tapping on mobile element '{}'", we);
      Rectangle rect = we.getRect();
      Point point = new Point(rect.getX() + rect.getWidth() / 2,
          rect.getY() + rect.getHeight() / 2);
      tapOn(point);
      LOGGER.info("Tapped on mobile element '{}'", we);
    } catch (Exception e) {
      LOGGER.error("Failed to tap on mobile element '{}'. Root cause: {}", we, e.getMessage());
    }
  }

  public void tapOn(Point point, Duration duration) {
    try {
      LOGGER.info("Tapping on point '{}, {}'", point.getX(), point.getY());
      PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
      Sequence sequence = new Sequence(input, 0);
      sequence.addAction(
          input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), point.getX(),
              point.getY()));
      sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
      sequence.addAction(new Pause(input, duration));
      sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
      appiumDriver.perform(ImmutableList.of(sequence));
      LOGGER.info("Tapped on point '{}, {}'", point.getX(), point.getY());
    } catch (Exception e) {
      LOGGER.error("Failed to tap on point '{}'. Root cause: {}", point, e.getMessage());
    }
  }

  public void tapAndHold(String locator, int seconds, int... timeOut) {
    WebElement we = findMobileElement(locator, timeOut);
    try {
      LOGGER.info("Tapping and holding within '{}' second(s) on mobile element located by '{}'",
          seconds, locator);
      Rectangle rect = we.getRect();
      Point point = new Point(rect.getX() + rect.getWidth() / 2,
          rect.getY() + rect.getHeight() / 2);
      tapOn(point, Duration.ofSeconds(seconds));
      LOGGER.info("Tapped and held within '{}' second(s) on mobile element located by '{}'",
          seconds, locator);
    } catch (Exception e) {
      LOGGER.error("Failed to tap and hold on mobile element '{}'. Root cause: {}", we,
          e.getMessage());
    }
  }

  public void doubleTap(String locator, int... timeOut) {
    WebElement we = findMobileElement(locator, timeOut);
    try {
      LOGGER.info("Double tapping on mobile element located by '{}'", locator);
      Rectangle rect = we.getRect();
      Point point = new Point(rect.getX() + rect.getWidth() / 2,
          rect.getY() + rect.getHeight() / 2);
      PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
      Sequence sequence = new Sequence(input, 0);
      sequence.addAction(
          input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), point.getX(),
              point.getY()));
      sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
      sequence.addAction(new Pause(input, Duration.ofMillis(50)));
      sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
      sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
      sequence.addAction(new Pause(input, Duration.ofMillis(50)));
      sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
      appiumDriver.perform(ImmutableList.of(sequence));
      LOGGER.info("Double tapped on mobile element located by '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to double tap on mobile element '{}'. Root cause: {}", we,
          e.getMessage());
    }
  }

  public void swipe(Point start, Point end) {
    try {
      LOGGER.info("Swiping from '{}, {}' to '{}, {}'", start.getX(), start.getY(), end.getX(),
          end.getY());
      boolean isAndroid = appiumDriver instanceof AndroidDriver;
      Duration duration = Duration.ofMillis(1000L);
      PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
      Sequence sequence = new Sequence(input, 0);
      sequence.addAction(
          input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.getX(),
              start.getY()));
      sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
      if (isAndroid) {
        duration = duration.dividedBy(3);
      } else {
        sequence.addAction(new Pause(input, duration));
        duration = Duration.ZERO;
      }
      sequence.addAction(
          input.createPointerMove(duration, PointerInput.Origin.viewport(), end.getX(),
              end.getY()));
//      sequence.addAction(new Pause(input, Duration.ofMillis(1000L)));
      sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
      appiumDriver.perform(ImmutableList.of(sequence));
      LOGGER.info("Swiped from '{}, {}' to '{}, {}'", start.getX(), start.getY(), end.getX(),
          end.getY());
    } catch (Exception e) {
      LOGGER.error("Failed to swipe from '{}, {}' to '{}, {}'. Root cause: {}", start.getX(),
          start.getY(), end.getX(), end.getY(), e.getMessage());
    }
  }

  public void scroll(String locator, ScrollDirection direction) {
    WebElement we = findMobileElement(locator);
    try {
      LOGGER.info("Scrolling {} on mobile element located by '{}'", direction.toString(), locator);
      Dimension size = appiumDriver.manage().window().getSize();
      int top = we.getLocation().getY() - (int) ((size.getHeight() * 0.8) * 0.5);
      int bottom = we.getLocation().getY() + (int) ((size.getHeight() * 0.8) * 0.5);
      int left = we.getLocation().getX() - (int) ((size.getWidth() * 0.8) * 0.5);
      int right = we.getLocation().getX() + (int) ((size.getWidth() * 0.8) * 0.5);
      switch (direction) {
        case UP:
          swipe(new Point(we.getLocation().getX(), top),
              new Point(we.getLocation().getX(), bottom));
          break;
        case DOWN:
          swipe(new Point(we.getLocation().getX(), bottom),
              new Point(we.getLocation().getX(), top));
          break;
        case LEFT:
          swipe(new Point(left, we.getLocation().getY()),
              new Point(right, we.getLocation().getY()));
          break;
        case RIGHT:
          swipe(new Point(right, we.getLocation().getY()),
              new Point(left, we.getLocation().getY()));
          break;
      }
      LOGGER.info("Scrolled {} on mobile element located by '{}'", direction, locator);
    } catch (Exception e) {
      LOGGER.error("Failed to scroll {} on mobile element located by '{}'. Root cause: {}",
          direction.toString(), locator, e.getMessage());
    }
  }

  public void scroll(ScrollDirection direction) {
    try {
      LOGGER.info("Scrolling {} on screen", direction.toString());
      Dimension size = appiumDriver.manage().window().getSize();
      Point midPoint = new Point((int) (size.width / 2), (int) (size.height / 2));
      int top = midPoint.getY() - (int) ((size.getHeight() * 0.8) * 0.5);
      int bottom = midPoint.getY() + (int) ((size.getHeight() * 0.8) * 0.5);
      int left = midPoint.getX() - (int) ((size.getWidth() * 0.8) * 0.5);
      int right = midPoint.getX() + (int) ((size.getWidth() * 0.8) * 0.5);
      switch (direction) {
        case UP:
          swipe(new Point(midPoint.getX(), top),
              new Point(midPoint.getX(), bottom));
          break;
        case DOWN:
          swipe(new Point(midPoint.getX(), bottom),
              new Point(midPoint.getX(), top));
          break;
        case LEFT:
          swipe(new Point(left, midPoint.getY()),
              new Point(right, midPoint.getY()));
          break;
        case RIGHT:
          swipe(new Point(right, midPoint.getY()),
              new Point(left, midPoint.getY()));
          break;
      }
      LOGGER.info("Scrolled {} on screen", direction.toString());
    } catch (Exception e) {
      LOGGER.error("Failed to scroll {} on screen. Root cause: {}", direction.toString(), e.getMessage());
    }
  }

  public Set<String> getContextHandles() {
    try {
      LOGGER.info("Getting available context handles");
      Set<String> contextHandles = ((SupportsContextSwitching) appiumDriver).getContextHandles();
      LOGGER.info("Retrieved context handles: {}", contextHandles);
      return contextHandles;
    } catch (Exception e) {
      LOGGER.error("Failed to get context handles. Root cause: {}", e.getMessage());
    }
    return null;
  }

  public String getContext() {
    try {
      LOGGER.info("Getting current context");
      String context = ((SupportsContextSwitching) appiumDriver).getContext();
      LOGGER.info("Current context is '{}'", context);
      return context;
    } catch (Exception e) {
      LOGGER.error("Failed to get current context. Root cause: {}", e.getMessage());
    }
    return null;
  }

  public void switchToWebView() {
    try {
      LOGGER.info("Switching to WebView context");
      Set<String> contextHandles = ((SupportsContextSwitching) appiumDriver).getContextHandles();
      String webViewContext = contextHandles.stream()
          .filter(context -> context.toUpperCase().contains("WEBVIEW"))
          .findFirst()
          .orElse(null);
      if (webViewContext == null) {
        LOGGER.error("No WebView context found. Available contexts: {}", contextHandles);
        return;
      }
      ((SupportsContextSwitching) appiumDriver).context(webViewContext);
      LOGGER.info("Switched to WebView context '{}'", webViewContext);
    } catch (Exception e) {
      LOGGER.error("Failed to switch to WebView context. Root cause: {}", e.getMessage());
    }
  }

  public void switchToWebView(String contextName) {
    try {
      LOGGER.info("Switching to WebView context '{}'", contextName);
      ((SupportsContextSwitching) appiumDriver).context(contextName);
      LOGGER.info("Switched to WebView context '{}'", contextName);
    } catch (Exception e) {
      LOGGER.error("Failed to switch to WebView context '{}'. Root cause: {}", contextName,
          e.getMessage());
    }
  }

  public void switchToNativeApp() {
    try {
      LOGGER.info("Switching to native app context");
      ((SupportsContextSwitching) appiumDriver).context("NATIVE_APP");
      LOGGER.info("Switched to native app context");
    } catch (Exception e) {
      LOGGER.error("Failed to switch to native app context. Root cause: {}", e.getMessage());
    }
  }

  public void dragAndDrop(Point source, Point target) {
    try {
      LOGGER.info("Dragging from '{}, {}' to '{}, {}'", source.getX(), source.getY(),
          target.getX(), target.getY());
      PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
      Sequence sequence = new Sequence(input, 0);
      sequence.addAction(
          input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), source.getX(),
              source.getY()));
      sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
      sequence.addAction(new Pause(input, Duration.ofMillis(500)));
      sequence.addAction(
          input.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(),
              target.getX(), target.getY()));
      sequence.addAction(new Pause(input, Duration.ofMillis(500)));
      sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
      appiumDriver.perform(ImmutableList.of(sequence));
      LOGGER.info("Dragged from '{}, {}' and dropped to '{}, {}'", source.getX(), source.getY(),
          target.getX(), target.getY());
    } catch (Exception e) {
      LOGGER.error("Failed to drag from '{}, {}' and drop to '{}, {}'. Root cause: {}", source.getX(),
          source.getY(), target.getX(), target.getY(), e.getMessage());
    }
  }

  public void dragAndDrop(String sourceLocator, String targetLocator, int... timeout) {
    WebElement source = findMobileElement(sourceLocator, timeout);
    WebElement target = findMobileElement(targetLocator, timeout);
    try {
      LOGGER.info("Dragging mobile element located by '{}' and dropping onto element located by '{}'",
          sourceLocator, targetLocator);
      dragAndDrop(centerOf(source), centerOf(target));
      LOGGER.info("Dragged mobile element located by '{}' and dropped onto element located by '{}'",
          sourceLocator, targetLocator);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to drag mobile element located by '{}' and drop onto element located by '{}'. Root cause: {}",
          sourceLocator, targetLocator, e.getMessage());
    }
  }

  public void dragAndDrop(WebElement source, WebElement target) {
    try {
      LOGGER.info("Dragging mobile element '{}' and dropping onto element '{}'", source, target);
      dragAndDrop(centerOf(source), centerOf(target));
      LOGGER.info("Dragged mobile element '{}' and dropped onto element '{}'", source, target);
    } catch (Exception e) {
      LOGGER.error("Failed to drag mobile element '{}' and drop onto element '{}'. Root cause: {}",
          source, target, e.getMessage());
    }
  }

  private Point centerOf(WebElement we) {
    Rectangle rect = we.getRect();
    return new Point(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
  }

  public void scrollToText(String text) {
    try {
      LOGGER.info("Scrolling to text '{}'", text);
      if (appiumDriver instanceof AndroidDriver) {
        String uiScrollable = String.format(
            "new UiScrollable(new UiSelector().scrollable(true).instance(0))"
                + ".scrollIntoView(new UiSelector().textContains(\"%s\").instance(0));", text);
        appiumDriver.findElement(AppiumBy.androidUIAutomator(uiScrollable));
      } else if (appiumDriver instanceof IOSDriver) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", "down");
        params.put("predicateString", String.format("label CONTAINS[cd] '%s'", text));
        appiumDriver.executeScript("mobile: scroll", params);
      }
      LOGGER.info("Scrolled to text '{}'", text);
    } catch (Exception e) {
      LOGGER.error("Failed to scroll to text '{}'. Root cause: {}", text, e.getMessage());
    }
  }

  public boolean isAndroid() {
    return appiumDriver instanceof AndroidDriver;
  }

  public boolean isIOS() {
    return appiumDriver instanceof IOSDriver;
  }

  @Attachment(value = "Screenshot", type = "image/png")
  public byte[] takeScreenshot() {
    try {
      LOGGER.info("Taking screenshot");
      byte[] images = appiumDriver.getScreenshotAs(OutputType.BYTES);
      if (images != null) {
        LOGGER.info("Screenshot taken");
        return images;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to take screenshot. Root cause: {}", e.getMessage());
    }
    return null;
  }

  @Attachment(value = "Screenshot", type = "image/png")
  public byte[] takeScreenshot(String locator, int... timeout) {
    WebElement we = findMobileElement(locator, timeout);
    try {
      LOGGER.info("Taking screenshot of mobile element located by '{}'", locator);
      byte[] images = we.getScreenshotAs(OutputType.BYTES);
      if (images != null) {
        LOGGER.info("Screenshot of mobile element located by '{}' taken", locator);
        return images;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to take screenshot of mobile element located by '{}'. Root cause: {}",
          locator, e.getMessage());
    }
    return null;
  }

  @Attachment(value = "Screenshot", type = "image/png")
  public byte[] takeScreenshotAndMarkElement(String locator, int... timeout) {
    WebElement we = findMobileElement(locator, timeout);
    return takeScreenshotAndMarkElement(we);
  }

  @Attachment(value = "Screenshot", type = "image/png")
  public byte[] takeScreenshotAndMarkElement(WebElement we) {
    try {
      LOGGER.info("Taking screenshot and marking element '{}' with a red border", we);
      byte[] screenshotBytes = appiumDriver.getScreenshotAs(OutputType.BYTES);
      BufferedImage screenshot = ImageIO.read(new ByteArrayInputStream(screenshotBytes));
      Rectangle rect = we.getRect();
      Dimension screenSize = appiumDriver.manage().window().getSize();
      double scaleX = (double) screenshot.getWidth() / screenSize.getWidth();
      double scaleY = (double) screenshot.getHeight() / screenSize.getHeight();
      Graphics2D graphics = screenshot.createGraphics();
      graphics.setColor(Color.RED);
      graphics.setStroke(new BasicStroke(4));
      graphics.drawRect((int) (rect.getX() * scaleX), (int) (rect.getY() * scaleY),
          (int) (rect.getWidth() * scaleX), (int) (rect.getHeight() * scaleY));
      graphics.dispose();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ImageIO.write(screenshot, "png", outputStream);
      byte[] images = outputStream.toByteArray();
      LOGGER.info("Screenshot taken and element '{}' marked successfully", we);
      return images;
    } catch (Exception e) {
      LOGGER.error("Failed to take screenshot and mark element '{}'. Root cause: {}", we,
          e.getMessage());
    }
    return null;
  }
}
