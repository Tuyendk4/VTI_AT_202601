package vn.edu.vtiacademy.common.keywords;

import static org.openqa.selenium.support.ui.ExpectedConditions.not;

import io.qameta.allure.Attachment;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vtiacademy.common.helper.PropertyHelper;

public class WebUI {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebUI.class);
  private static final String REMOTE_URL = "http://localhost:4444/";
  private static final int DEFAULT_TIMEOUT = PropertyHelper.getIntegerProperty("timeout", 30);;
  private WebDriver driver;

  public void openBrowser(String browserName, String... url) {
    try {
      LOGGER.info("Opening browser: {}", browserName.toUpperCase());
      switch (browserName.toUpperCase()) {
        case "CHROME":
//          WebDriverManager.chromedriver().setup();
          ChromeOptions options = new ChromeOptions();
          options.addArguments("--remote-allow-origins=*");
          driver = new ChromeDriver(options);
          break;
        case "FIREFOX":
//          WebDriverManager.firefoxdriver().setup();
          driver = new FirefoxDriver();
          break;
        case "SAFARI":
//          WebDriverManager.safaridriver().setup();
          driver = new SafariDriver();
          break;
        case "CHROME_HEADLESS":
          ChromeOptions optionsHeadless = new ChromeOptions();
          optionsHeadless.addArguments("--headless=new");
          driver = new ChromeDriver(optionsHeadless);
          break;
        case "FIREFOX_HEADLESS":
          FirefoxOptions optionsFirefoxHeadless = new FirefoxOptions();
          optionsFirefoxHeadless.addArguments("--headless");
          driver = new FirefoxDriver(optionsFirefoxHeadless);
          break;
        case "CHROME_REMOTE":
          ChromeOptions optionsChromeRemote = new ChromeOptions();
          optionsChromeRemote.addArguments("--remote-allow-origins=*");
          optionsChromeRemote.addArguments("--headless=new");
          optionsChromeRemote.addArguments("--window-size=2560,1600");
          driver = new RemoteWebDriver(new URL(REMOTE_URL), optionsChromeRemote);
//          driver.manage().window().setSize(new Dimension(2560, 1600));
          break;
        case "FIREFOX_REMOTE":
          FirefoxOptions optionsFirefoxRemote = new FirefoxOptions();
          optionsFirefoxRemote.addArguments("--headless");
          optionsFirefoxRemote.addArguments("--width=1920");
          optionsFirefoxRemote.addArguments("--height=1080");
          driver = new RemoteWebDriver(new URL(REMOTE_URL), optionsFirefoxRemote);
          break;
        case "SAFARI_REMOTE":
          SafariOptions optionsSafariRemote = new SafariOptions();
          driver = new RemoteWebDriver(new URL(REMOTE_URL), optionsSafariRemote);
          break;
      }
      LOGGER.info("Browser {} opened successfully", browserName.toUpperCase());
    } catch (Exception e) {
      LOGGER.error("Failed to open browser: {}. Root cause: {}", browserName, e.getMessage());
    }

    String rawUrl = url.length > 0 ? url[0] : "";
    if (!rawUrl.isEmpty()) {
      try {
        LOGGER.info("Navigating to URL: {}", rawUrl);
        driver.get(rawUrl);
        LOGGER.info("Navigated to URL: {}", rawUrl);
      } catch (Exception e) {
        LOGGER.error("Failed to navigate to URL: {}. Root cause: {}", rawUrl, e.getMessage());
      }
    }
  }

  public void closeBrowser() {
    try {
      LOGGER.info("Closing browser");
      driver.quit();
      LOGGER.info("Browser closed successfully");
    } catch (Exception e) {
      LOGGER.error("Failed to close browser. Root cause: {}", e.getMessage());
    }
  }

  public String getTitle() {
    try {
      LOGGER.info("Getting page title");
      String title = driver.getTitle();
      LOGGER.info("Title of the page is {}", title);
      return title;
    } catch (Exception e) {
      LOGGER.error("Failed to get page title. Root cause: {}", e.getMessage());
    }
    return null;
  }

  public String getUrl() {
    try {
      LOGGER.info("Getting page url");
      String url = driver.getCurrentUrl();
      LOGGER.info("Url of the page is {}", url);
      return url;
    } catch (Exception e) {
      LOGGER.error("Failed to get page url. Root cause: {}", e.getMessage());
    }
    return null;
  }

  public String getPageSource() {
    try {
      LOGGER.info("Getting page source");
      String pageSource = driver.getPageSource();
      LOGGER.info("Page source of the page is {}", pageSource);
      return pageSource;
    } catch (Exception e) {
      LOGGER.error("Failed to get page source. Root cause: {}", e.getMessage());
    }
    return null;
  }

  public void navigateToUrl(String url) {
    try {
      LOGGER.info("Navigating to URL: {}", url);
      driver.navigate().to(url);
      LOGGER.info("Navigated to URL: {}", url);
    } catch (Exception e) {
      LOGGER.error("Failed to navigate to URL: {}. Root cause: {}", url, e.getMessage());
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

  public void back() {
    try {
      LOGGER.info("Going back");
      driver.navigate().back();
      LOGGER.info("Back completed");
    } catch (Exception e) {
      LOGGER.error("Failed to go back. Root cause: {}", e.getMessage());
    }
  }

  public void forward() {
    try {
      LOGGER.info("Going forward");
      driver.navigate().forward();
      LOGGER.info("Forward completed");
    } catch (Exception e) {
      LOGGER.error("Failed to go forward. Root cause: {}", e.getMessage());
    }
  }

  public void refresh() {
    try {
      LOGGER.info("Refreshing page");
      driver.navigate().refresh();
      LOGGER.info("Refresh completed");
    } catch (Exception e) {
      LOGGER.error("Failed to refresh page. Root cause: {}", e.getMessage());
    }
  }

  //Explicit locator
  //id:submit, name:firstName, css:input[id="firstName"], xpath://input[@id="firstName"]
  //Implicit locator
  // //input[@id="firstName"]

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

  public WebElement findWebElement(String locator, int... timeout) {
//    long startTime = 0;
//    long endTime = 0;
//    double totalTime = 0;
    int waitTime = timeout.length > 0 ? timeout[0] : DEFAULT_TIMEOUT;
    try {
      LOGGER.info("Finding web element located by '{}' within {} second(s)", locator, waitTime);
//      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//      startTime = System.currentTimeMillis();
//      WebElement we = driver.findElement(findBy(locator));
//      Wait<WebDriver> wait = new FluentWait<>(driver)
//        .withTimeout(Duration.ofSeconds(30))
//        .pollingEvery(Duration.ofMillis(500))
//        .ignoring(NoSuchElementException.class);
//      WebElement we = wait.until(driver -> driver.findElement(findBy(locator)));
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
      WebElement we = wait.until(ExpectedConditions.presenceOfElementLocated(findBy(locator)));
//      endTime = System.currentTimeMillis();
      if (we != null) {
        LOGGER.info("Found 1 web element located by '{}'", locator);
//        totalTime = (endTime - startTime) / 1000.0;
//        LOGGER.info("Total time {}", totalTime);
        return we;
      }
    } catch (Exception e) {
//      endTime = System.currentTimeMillis();
      LOGGER.error("Failed to find web element locate by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
//    totalTime = (endTime - startTime) / 1000.0;
//    LOGGER.info("Total time {}", totalTime);
    return null;
  }

  public WebElement findWebElement(String locator, String param) {
    locator = StringUtils.replace(locator, "${param}", param);
    try {
      LOGGER.info("Finding web element located by '{}'", locator);
      WebElement we = driver.findElement(findBy(locator));
      if (we != null) {
        LOGGER.info("Found 1 web element located by '{}'", locator);
        return we;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to find web element locate by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
    return null;
  }

  public WebElement findWebElement(By by) {
    try {
      LOGGER.info("Finding web element located by '{}'", by);
      WebElement we = driver.findElement(by);
      if (we != null) {
        LOGGER.info("Found 1 web element located by '{}'", by);
        return we;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to find web element locate by '{}'. Root cause: {}", by, e.getMessage());
    }
    return null;
  }

  public List<WebElement> findWebElements(String locator, int... timeout) {
    int waitTime = timeout.length > 0 ? timeout[0] : DEFAULT_TIMEOUT;
    try {
      LOGGER.info("Finding web elements located by '{}' within {} second(s)", locator, waitTime);
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
      List<WebElement> wes = wait.until(
          ExpectedConditions.presenceOfAllElementsLocatedBy(findBy(locator)));
      if (wes != null) {
        LOGGER.info("Found {} web elements located by '{}'", wes.size(), locator);
        return wes;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to find web elements located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
    return null;
  }

  public void inputText(String locator, String text, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Inputting text '{}' into web element located by '{}'", text, locator);
      we.sendKeys(text);
      LOGGER.info("Inputted text '{}' into web element located by '{}' successfully", text,
          locator);
    } catch (Exception e) {
      LOGGER.error("Failed to input text. Root cause: {}", e.getMessage());
    }
  }

  public void inputText(WebElement we, String text, int... timeout) {
    try {
      LOGGER.info("Inputting text '{}' into web element '{}'", text, we);
      we.sendKeys(text);
      LOGGER.info("Inputted text '{}' into web element '{}' successfully", text,
          we);
    } catch (Exception e) {
      LOGGER.error("Failed to input text in web element '{}'. Root cause: {}", we, e.getMessage());
    }
  }

  public void clearText(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
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

  public void copy(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Copying text from web element located by '{}'", locator);
      String osName = System.getProperty("os.name");
      if (osName.toLowerCase().contains("mac")) {
        we.sendKeys(Keys.chord(Keys.COMMAND, "c"));
      } else {
        we.sendKeys(Keys.chord(Keys.CONTROL, "c"));
      }
      LOGGER.info("Copied text from web element located by '{}' successfully", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to copy text from web element located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public void selectAllText(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Selecting all text in web element located by '{}'", locator);
      String osName = System.getProperty("os.name");
      if (osName.toLowerCase().contains("mac")) {
        we.sendKeys(Keys.chord(Keys.COMMAND, "a"));
      } else {
        we.sendKeys(Keys.chord(Keys.CONTROL, "a"));
      }
      LOGGER.info("Selected all text in web element located by '{}' successfully", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to select all text in web element located by '{}'. Root cause: {}",
          locator, e.getMessage());
    }
  }

  public void paste(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Pasting text in web element located by '{}'", locator);
      String osName = System.getProperty("os.name");
      we.click();
      if (osName.toLowerCase().contains("mac")) {
        we.sendKeys(Keys.chord(Keys.COMMAND, "v"));
      } else {
        we.sendKeys(Keys.chord(Keys.CONTROL, "v"));
      }
      LOGGER.info("Pasted text in web element located by '{}' successfully", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to paste text in web element located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public void clickOn(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Clicking on web element located by '{}'", locator);
      we.click();
      LOGGER.info("Clicked on web element located by '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to click on web element located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public void clickOn(WebElement we) {
    try {
      LOGGER.info("Clicking on web element '{}'", we);
      we.click();
      LOGGER.info("Clicked on web element '{}'", we);
    } catch (Exception e) {
      LOGGER.error("Failed to click on web element '{}'. Root cause: {}", we, e.getMessage());
    }
  }

  public void maximizeWindow() {
    try {
      LOGGER.info("Maximizing window");
      driver.manage().window().maximize();
      LOGGER.info("Window maximized");
    } catch (Exception e) {
      LOGGER.error("Failed to maximize window. Root cause: {}", e.getMessage());
    }
  }

  public void submit(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Submitting web element located by '{}'", locator);
      we.submit();
      LOGGER.info("Submitted web element located by '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to submit web element located by '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public String getText(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
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
    WebElement we = findWebElement(locator, timeout);
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

  public String getCssValue(String locator, String css, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Getting CSS value from web element located by '{}'", locator);
      String cssValue = we.getCssValue(css);
      LOGGER.info("Retrieved CSS value '{}' from web element located by '{}'", cssValue, locator);
      return cssValue;
    } catch (Exception e) {
      LOGGER.error("Failed to get CSS value from web element located by '{}'. Root cause: {}",
          locator, e.getMessage());
    }
    return null;
  }

  public String getAttributeValue(String locator, String attributeName, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
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
    WebElement we = findWebElement(locator, timeout);
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
    WebElement we = findWebElement(locator);
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
    WebElement we = findWebElement(locator);
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
    WebElement we = findWebElement(locator);
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

  public boolean verifyPageTitle(String expectedTitle) {
    try {
      LOGGER.info("Verifying page title");
      String actualTitle = driver.getTitle();
      if (actualTitle.equals(expectedTitle)) {
        LOGGER.info("Actual page title '{}' and expected title '{}' are the same", actualTitle,
            expectedTitle);
        return true;
      }
      LOGGER.error("Actual page title '{}' and expected title '{}' are not the same", actualTitle,
          expectedTitle);
    } catch (Exception e) {
      LOGGER.error("Failed to verify page title. Root cause: {}", e.getMessage());
    }
    return false;
  }

  public boolean verifyUrl(String expectedUrl) {
    try {
      LOGGER.info("Verifying url");
      String actualUrl = driver.getCurrentUrl();
      if (actualUrl.equals(expectedUrl)) {
        LOGGER.info("Actual url '{}' and expected url '{}' are the same", actualUrl, expectedUrl);
        return true;
      }
      LOGGER.error("Actual url '{}' and expected url '{}' are not the same", actualUrl,
          expectedUrl);
    } catch (Exception e) {
      LOGGER.error("Failed to verify url. Root cause: {}", e.getMessage());
    }
    return false;
  }

  public boolean verifyElementText(String locator, String expectedText) {
    WebElement we = findWebElement(locator);
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
    WebElement we = findWebElement(locator);
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
    WebElement we = findWebElement(locator);
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
    WebElement we = findWebElement(locator);
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
    WebElement we = findWebElement(locator);
    if (we != null) {
      LOGGER.info("Web element located by '{}' is present", locator);
      return true;
    }
    LOGGER.error("Web element located by '{}' is not present", locator);
    return false;
  }

  public boolean verifyElementNotPresent(String locator) {
    WebElement we = findWebElement(locator);
    if (we == null) {
      LOGGER.info("Web element located by '{}' is not present", locator);
      return true;
    }
    LOGGER.error("Web element located by '{}' is present", locator);
    return false;
  }

  public void selectOptionByIndex(String locator, int index) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Selecting option by index {} for web element located by '{}'", index, locator);
      Select select = new Select(we);
      select.selectByIndex(index);
      LOGGER.info("Option with index {} selected successfully for web element located by '{}'",
          index, locator);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to select option by index {} for web element located by '{}'. Root cause: {}",
          index, locator, e.getMessage());
    }
  }

  public void selectOptionByText(String locator, String text) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Selecting option by text {} for web element located by '{}'", text, locator);
      Select select = new Select(we);
      select.selectByVisibleText(text);
      LOGGER.info("Option with text {} selected successfully for web element located by '{}'", text,
          locator);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to select option by text {} for web element located by '{}'. Root cause: {}",
          text, locator, e.getMessage());
    }
  }

  public void selectOptionByValue(String locator, String value) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Selecting option by value {} for web element located by '{}'", value, locator);
      Select select = new Select(we);
      select.selectByValue(value);
      LOGGER.info("Option with value {} selected successfully for web element located by '{}'",
          value, locator);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to select option by value {} for web element located by '{}'. Root cause: {}",
          value, locator, e.getMessage());
    }
  }

  public boolean waitForElementPresent(String locator, int timeout) {
    try {
      LOGGER.info("Waiting for web element located by '{}' to be present within {} second(s)",
          locator, timeout);
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
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
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
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
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
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
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
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
      LOGGER.info("Waiting for web element located by '{}' to be invisible within {} second(s)",
          locator, timeout);
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
      boolean isNotVisible = wait.until(
          ExpectedConditions.invisibilityOfElementLocated(findBy(locator)));
      if (isNotVisible) {
        LOGGER.info("Web element located by '{}' is invisible within {} second(s)", locator,
            timeout);
        return true;
      }
      LOGGER.error("Web element located by '{}' is still visible after {} second(s)", locator,
          timeout);
    } catch (Exception e) {
      LOGGER.error("Failed to wait for web element located by '{}' to be invisible. Root cause: {}",
          locator, e.getMessage());
    }
    return false;
  }

  public boolean waitForElementClickable(String locator, int timeout) {
    try {
      LOGGER.info("Waiting for web element located by '{}' to be clickable within {} second(s)",
          locator, timeout);
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
      WebElement we = wait.until(ExpectedConditions.elementToBeClickable(findBy(locator)));
      if (we != null) {
        LOGGER.info("Web element located by '{}' is clickable", locator);
        return true;
      }
      LOGGER.error("Web element located by '{}' is not clickable within {} second(s)", locator,
          timeout);
    } catch (Exception e) {
      LOGGER.error("Failed to wait for web element located by '{}' to be clickable. Root cause: {}",
          locator, e.getMessage());
    }
    return false;
  }

  public void switchToWindowByIndex(int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Window index cannot be negative");
    }
    Set<String> windowHandles = driver.getWindowHandles();
    if (index >= windowHandles.size()) {
      throw new IndexOutOfBoundsException("Window index is out of bounds");
    }
    try {
      LOGGER.info("Switching to window with index {}", index);
      List<String> windowList = new ArrayList<>(windowHandles);
      driver.switchTo().window(windowList.get(index));
      LOGGER.info("Successfully switched to window with index {}", index);
    } catch (Exception e) {
      LOGGER.error("Failed to switch to window with index {}. Root cause: {}", index,
          e.getMessage());
    }
  }

  public void switchToWindowByTitle(String title) {
    try {
      LOGGER.info("Switching to window which has title '{}'", title);
      boolean found = false;
      Set<String> windowHandles = driver.getWindowHandles();
      for (String windowHandle : windowHandles) {
        driver.switchTo().window(windowHandle);
        if (driver.getTitle().equals(title)) {
          LOGGER.info("Successfully switched to window with title '{}'", title);
          found = true;
          break;
        }
      }
      if (!found) {
        LOGGER.error("Failed to switch to window with title '{}'", title);
      }
    } catch (Exception e) {
      LOGGER.error("Failed to switch to window with title '{}'. Root cause: {}", title,
          e.getMessage());
    }
  }

  public void switchToWindowByURL(String url) {
    try {
      LOGGER.info("Switching to window which has url '{}'", url);
      boolean found = false;
      Set<String> windowHandles = driver.getWindowHandles();
      for (String windowHandle : windowHandles) {
        driver.switchTo().window(windowHandle);
        if (driver.getCurrentUrl().equals(url)) {
          LOGGER.info("Successfully switched to window with url '{}'", url);
          found = true;
          break;
        }
      }
      if (!found) {
        LOGGER.error("Failed to switch to window with url '{}'", url);
      }
    } catch (Exception e) {
      LOGGER.error("Failed to switch to window with url '{}'. Root cause: {}", url, e.getMessage());
    }
  }

  public void closeWindowByIndex(int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Window index cannot be negative");
    }
    Set<String> windowHandles = driver.getWindowHandles();
    if (index >= windowHandles.size()) {
      throw new IndexOutOfBoundsException("Window index is out of bounds");
    }
    try {
      LOGGER.info("Closing window by index '{}'", index);
      List<String> windowList = new ArrayList<>(windowHandles);
      driver.switchTo().window(windowList.get(index));
      driver.close();
      LOGGER.info("Successfully closed window by index '{}'", index);
    } catch (Exception e) {
      LOGGER.error("Failed to close window by index '{}'. Root cause: {}", index, e.getMessage());
    }
  }

  public void acceptAlert() {
    try {
      LOGGER.info("Accepting alert");
      Alert alert = driver.switchTo().alert();
      alert.accept();
      LOGGER.info("Alert accepted");
    } catch (Exception e) {
      LOGGER.error("Failed to accept alert. Root cause: {}", e.getMessage());
    }
  }

  public void dismissAlert() {
    try {
      LOGGER.info("Dismissing alert");
      Alert alert = driver.switchTo().alert();
      alert.dismiss();
      LOGGER.info("Alert dismissed");
    } catch (Exception e) {
      LOGGER.error("Failed to dismiss alert. Root cause: {}", e.getMessage());
    }
  }

  public String getAlertText() {
    try {
      LOGGER.info("Getting alert text");
      Alert alert = driver.switchTo().alert();
      String text = alert.getText();
      LOGGER.info("Alert text is '{}'", text);
      return text;
    } catch (Exception e) {
      LOGGER.error("Failed to get alert text. Root cause: {}", e.getMessage());
    }
    return null;
  }

  public void setAlertText(String text) {
    try {
      LOGGER.info("Setting alert text to '{}'", text);
      Alert alert = driver.switchTo().alert();
      alert.sendKeys(text);
      LOGGER.info("Alert text set to '{}'", text);
    } catch (Exception e) {
      LOGGER.error("Failed to set alert text to '{}'. Root cause: {}", text, e.getMessage());
    }
  }

  public boolean waitForAlert(int... timeout) {
    int waitTime = timeout.length > 0 ? timeout[0] : DEFAULT_TIMEOUT;
    try {
      LOGGER.info("Waiting for alert to be present within {} second(s)", waitTime);
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
      Alert alert = wait.until(ExpectedConditions.alertIsPresent());
      if (alert != null) {
        LOGGER.info("Alert is present");
        return true;
      }
      LOGGER.error("Alert is not present within {} second(s)", waitTime);
    } catch (Exception e) {
      LOGGER.error("Failed to wait for alert to be present. Root cause: {}", e.getMessage());
    }
    return false;
  }

  public void switchToIframe(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Switching to iframe located by '{}'", locator);
      driver.switchTo().frame(we);
      LOGGER.info("Switched to iframe with locator '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to switch to iframe with locator '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public void switchToDefaultContent() {
    try {
      LOGGER.info("Switching to default content");
      driver.switchTo().defaultContent();
      LOGGER.info("Switched to default content");
    } catch (Exception e) {
      LOGGER.error("Failed to switch to default content. Root cause: {}", e.getMessage());
    }
  }

  public void doubleClick(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Double clicking on web element located by '{}'", locator);
      Actions actions = new Actions(driver);
      actions.doubleClick(we).build().perform();
//      Action action = actions.build();
//      action.perform();
      LOGGER.info("Double click on web element with locator '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to double click on web element with locator '{}'. Root cause: {}",
          locator, e.getMessage());
    }
  }

  public void mouseOver(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Mousing over on web element located by '{}'", locator);
      Actions actions = new Actions(driver);
      actions.moveToElement(we).build().perform();
      LOGGER.info("Mouse over on web element with locator '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to mouse over on web element with locator '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public void rightClick(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Right clicking on web element located by '{}'", locator);
      Actions actions = new Actions(driver);
      actions.contextClick(we).build().perform();
      LOGGER.info("Right click on web element with locator '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to right click on web element with locator '{}'. Root cause: {}",
          locator, e.getMessage());
    }
  }

  public void clickAndHold(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Clicking and holding on web element located by '{}'", locator);
      Actions actions = new Actions(driver);
      actions.clickAndHold(we).build().perform();
      LOGGER.info("Clicked and holded on web element with locator '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to click and hold on web element with locator '{}'. Root cause: {}",
          locator, e.getMessage());
    }
  }

  public void clickAndHoldAndMoveToOffset(String locator, int xOffset, int yOffset,
      int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Clicking and holding on web element located by '{}'", locator);
      Actions actions = new Actions(driver);
      actions.clickAndHold(we).pause(500).moveByOffset(xOffset, yOffset).pause(500).release().build().perform();
      LOGGER.info(
          "Clicked and holded on web element with locator '{}' and moved to offset ({}, {})",
          locator, xOffset, yOffset);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to click and hold on web element with locator '{}' and moved to offset({}, {}). Root cause: {}",
          locator, xOffset, yOffset, e.getMessage());
    }
  }

  public void moveElementToOffset(String locator, int xOffset, int yOffset, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Moving mouse to web element located by '{}'", locator);
      Actions actions = new Actions(driver);
      actions.moveToElement(we, xOffset, yOffset).build().perform();
      LOGGER.info("Moved mouse to web element with locator '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to move mouse to web element with locator '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public void release(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Releasing on web element located by '{}'", locator);
      Actions actions = new Actions(driver);
      actions.release(we).build().perform();
      LOGGER.info("Released on web element with locator '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to release on web element with locator '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public void dragAndDrop(String sourceLocator, String targetLocator, int... timeout) {
    WebElement sourceWe = findWebElement(sourceLocator, timeout);
    WebElement targetWe = findWebElement(targetLocator, timeout);
    try {
      LOGGER.info("Dragging web element located by '{}'", sourceLocator);
      Actions actions = new Actions(driver);
      actions.dragAndDrop(sourceWe, targetWe).build().perform();
      LOGGER.info("Dropped web element with locator '{}' onto '{}'", sourceLocator, targetLocator);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to drag and drop web element with locator '{}' onto '{}'. Root cause: {}",
          sourceLocator, targetLocator, e.getMessage());
    }
  }

  public void dragAndDrop(String sourceLocator, int xOffset, int yOffset, int... timeout) {
    WebElement sourceWe = findWebElement(sourceLocator, timeout);
    try {
      LOGGER.info("Dragging web element located by '{}'", sourceLocator);
      Actions actions = new Actions(driver);
      actions.dragAndDropBy(sourceWe, xOffset, yOffset).build().perform();
      LOGGER.info("Dropped web element with locator '{}' to offset ({}, {})", sourceLocator,
          xOffset, yOffset);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to drag and drop web element with locator '{}' by offset ({}, {}). Root cause: {}",
          sourceLocator, xOffset, yOffset, e.getMessage());
    }
  }

  public void clickOffset(String locator, int xOffset, int yOffset, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Clicking on web element located by '{}'", locator);
      Actions actions = new Actions(driver);
      actions.moveToElement(we, xOffset, yOffset).click().build().perform();
      LOGGER.info("Clicked on web element with locator '{}' at offset ({}, {})", locator, xOffset,
          yOffset);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to click on web element with locator '{}' at offset ({}, {}). Root cause: {}",
          locator, xOffset, yOffset, e.getMessage());
    }
  }

  public void clickOffset(WebElement we, int xOffset, int yOffset, int... timeout) {
    try {
      LOGGER.info("Clicking on web element '{}'", we);
      Actions actions = new Actions(driver);
      actions.moveToElement(we, xOffset, yOffset).click().build().perform();
      LOGGER.info("Clicked on web element '{}' at offset ({}, {})", we, xOffset,
          yOffset);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to click on web element '{}' at offset ({}, {}). Root cause: {}",
          we, xOffset, yOffset, e.getMessage());
    }
  }

  public void click(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Clicking on web element located by '{}'", locator);
      Actions actions = new Actions(driver);
      actions.moveToElement(we).click().build().perform();
      LOGGER.info("Clicked on web element with locator '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to click on web element with locator '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public void scrollToElement(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Scrolling to web element located by '{}'", locator);
      Actions actions = new Actions(driver);
      actions.scrollToElement(we).build().perform();
      LOGGER.info("Scrolled to web element with locator '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to scroll to web element with locator '{}'. Root cause: {}", locator,
          e.getMessage());
    }
  }

  public void enhancedClick(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Click on web element located by '{}'", locator);
      JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
      jsExecutor.executeScript("arguments[0].click();", we);
      LOGGER.info("Clicked on web element with locator '{}'", locator);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to click on web element with locator '{}'. Root cause: {}",
          locator, e.getMessage());
    }
  }

  public void scrollIntoView(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Scrolling into view web element located by '{}'", locator);
      JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
      jsExecutor.executeScript("arguments[0].scrollIntoView(true);", we);
      LOGGER.info("Scrolled into view web element with locator '{}'", locator);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to scroll into view web element with locator '{}'. Root cause: {}",
          locator, e.getMessage());
    }
  }

  public void scrollToElementAtCenterOfPage(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Scrolling to web element located by '{}' to center of page", locator);
      int xOffset = we.getLocation().getX();
      int yOffset = we.getLocation().getY();
      int heightOfBrowser = driver.manage().window().getSize().getHeight();
      JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
      jsExecutor.executeScript("window.scrollTo(arguments[0], arguments[1] - arguments[2] / 2);", xOffset, yOffset, heightOfBrowser);
      LOGGER.info("Scrolled to web element located by '{}' to center of page", locator);
    } catch (Exception e) {
      LOGGER.error(
          "Failed to scroll to web element with locator '{}' to center of page. Root cause: {}",
          locator, e.getMessage());
    }
  }

  public File takeScreenshot() {
    try {
      LOGGER.info("Taking screenshot");
      File images = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      if(images != null) {
        LOGGER.info("Screenshot taken");
        return images;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to take screenshot. Root cause: {}", e.getMessage());
    }
    return null;
  }

  public File takeScreenshot(String locator, int... timeout) {
    WebElement we = findWebElement(locator, timeout);
    try {
      LOGGER.info("Taking screenshot");
      File images = we.getScreenshotAs(OutputType.FILE);
      if(images != null) {
        LOGGER.info("Screenshot taken");
        return images;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to take screenshot. Root cause: {}", e.getMessage());
    }
    return null;
  }

  @Attachment(value = "Screenshot", type = "image/png")
  public byte[] attachmentScreenshot() {
    try {
      LOGGER.info("Taking screenshot");
      byte[] images = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
      if(images != null) {
        LOGGER.info("Screenshot taken");
        return images;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to take screenshot. Root cause: {}", e.getMessage());
    }
    return null;
  }

  public File takeScreenshotAndMarkElement(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Taking screenshot");
      String originStyle = we.getAttribute("style");
      JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
      jsExecutor.executeScript("arguments[0].style.border='4px solid red'", we);
      delayInMilliseconds(200);
      File images = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      delayInMilliseconds(300);
      jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1]);", we, originStyle);
      if (images != null) {
        LOGGER.info("Screenshot taken and marked element successfully");
        return images;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to take screenshot. Root cause: {}", e.getMessage());
    }
    return null;
  }

  public File takeScreenshotAndMarkElement(WebElement we) {
    try {
      LOGGER.info("Taking screenshot");
      String originStyle = we.getAttribute("style");
      JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
      jsExecutor.executeScript("arguments[0].style.border='4px solid red'", we);
      delayInMilliseconds(200);
      File images = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      delayInMilliseconds(300);
      jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1]);", we, originStyle);
      if (images != null) {
        LOGGER.info("Screenshot taken and marked element successfully");
        return images;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to take screenshot. Root cause: {}", e.getMessage());
    }
    return null;
  }

  @Attachment(value = "Screenshot", type = "image/png")
  public byte[] attachmentScreenshotWhichMarkElement(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Taking screenshot");
      String originStyle = we.getAttribute("style");
      JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
      jsExecutor.executeScript("arguments[0].style.border='4px solid red'", we);
      delayInMilliseconds(200);
      byte[] images = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
      delayInMilliseconds(300);
      jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1]);", we, originStyle);
      if (images != null) {
        LOGGER.info("Screenshot taken and marked element successfully");
        return images;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to take screenshot. Root cause: {}", e.getMessage());
    }
    return null;
  }

  @Attachment(value = "Screenshot", type = "image/png")
  public byte[] attachmentScreenshotWhichMarkElement(WebElement we) {
    try {
      LOGGER.info("Taking screenshot");
      String originStyle = we.getAttribute("style");
      JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
      jsExecutor.executeScript("arguments[0].style.border='4px solid red'", we);
      delayInMilliseconds(200);
      byte[] images = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
      delayInMilliseconds(300);
      jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1]);", we, originStyle);
      if (images != null) {
        LOGGER.info("Screenshot taken and marked element successfully");
        return images;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to take screenshot. Root cause: {}", e.getMessage());
    }
    return null;
  }

  public WebDriver getWebDriver() {
    return driver;
  }

}
