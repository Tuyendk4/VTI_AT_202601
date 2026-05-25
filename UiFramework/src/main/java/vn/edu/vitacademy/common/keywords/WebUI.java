package vn.edu.vitacademy.common.keywords;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUI {

  private WebDriver driver;
  private static final Logger LOGGER = LoggerFactory.getLogger(WebUI.class);

  public void openBrowser(String browserName, String... url) {
    try {
      LOGGER.info("Opening browser: {}", browserName.toUpperCase());
      switch (browserName.toUpperCase()) {
        case "CHROME":
          WebDriverManager.chromedriver().setup();
          ChromeOptions options = new ChromeOptions();
          options.addArguments("--remote-allow-origins=*");
          driver = new ChromeDriver(options);
          break;
        case "FIREFOX":
          WebDriverManager.firefoxdriver().setup();
          driver = new FirefoxDriver();
          break;
        case "SAFARI":
          WebDriverManager.safaridriver().setup();
          driver = new SafariDriver();
          break;
      }
      LOGGER.info("Browser {} opened successfully", browserName.toUpperCase());
    } catch (Exception e) {
      LOGGER.error("Failed to open browser: {}. Root cause: {}", browserName, e.getMessage());
    }

    String rawUrl = url.length > 0 ? url[0] : "";
    if(!rawUrl.isEmpty()) {
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
      Thread.sleep(seconds * 1000);
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
  public WebElement findWebElement(String locator) {
    long startTime = System.currentTimeMillis();
    long endTime = 0;
    double totalTime = 0;
    try {
      LOGGER.info("Finding web element located by '{}'", locator);
      WebElement we = driver.findElement(findBy(locator));
      endTime = System.currentTimeMillis();
      if(we != null) {
        LOGGER.info("Found 1 web element located by '{}'", locator);
        totalTime = (endTime - startTime) / 1000.0;
        LOGGER.info("Total time {}", totalTime);
        return we;
      }
    } catch (Exception e) {
      endTime = System.currentTimeMillis();
      LOGGER.error("Failed to find web element locate by '{}'. Root cause: {}", locator, e.getMessage());
    }
    totalTime = (endTime - startTime) / 1000.0;
    LOGGER.info("Total time {}", totalTime);
    return null;
  }

  public WebElement findWebElement(String locator, String param) {
    locator = StringUtils.replace(locator, "${param}", param);
    try {
      LOGGER.info("Finding web element located by '{}'", locator);
      WebElement we = driver.findElement(findBy(locator));
      if(we != null) {
        LOGGER.info("Found 1 web element located by '{}'", locator);
        return we;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to find web element locate by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return null;
  }

  public WebElement findWebElement(By by) {
    try {
      LOGGER.info("Finding web element located by '{}'", by);
      WebElement we = driver.findElement(by);
      if(we != null) {
        LOGGER.info("Found 1 web element located by '{}'", by);
        return we;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to find web element locate by '{}'. Root cause: {}", by, e.getMessage());
    }
    return null;
  }

  public List<WebElement> findWebElements(String locator) {
    try {
      LOGGER.info("Finding web elements located by '{}'", locator);
      List<WebElement> wes = driver.findElements(findBy(locator));
      if(wes != null) {
        LOGGER.info("Found {} web elements located by '{}'", wes.size(), locator);
        return wes;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to find web elements located by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return null;
  }

  public void inputText(String locator, String text) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Inputting text '{}' into web element located by '{}'", text, locator);
      we.sendKeys(text);
      LOGGER.info("Inputted text '{}' into web element located by '{}' successfully", text, locator);
    } catch (Exception e) {
      LOGGER.error("Failed to input text. Root cause: {}", e.getMessage());
    }
  }

  public void clearText(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Clearing text in element located by '{}'", locator);
      we.clear();
      LOGGER.info("Text cleared");
    } catch (Exception e) {
      LOGGER.error("Failed to clear text. Root cause: {}", e.getMessage());
    }
  }

  public void copy(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Copying text from web element located by '{}'", locator);
      String osName = System.getProperty("os.name");
      if(osName.toLowerCase().contains("mac")) {
        we.sendKeys(Keys.chord(Keys.COMMAND, "c"));
      } else {
        we.sendKeys(Keys.chord(Keys.CONTROL, "c"));
      }
      LOGGER.info("Copied text from web element located by '{}' successfully", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to copy text from web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
  }

  public void selectAllText(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Selecting all text in web element located by '{}'", locator);
      String osName = System.getProperty("os.name");
      if(osName.toLowerCase().contains("mac")) {
        we.sendKeys(Keys.chord(Keys.COMMAND, "a"));
      } else {
        we.sendKeys(Keys.chord(Keys.CONTROL, "a"));
      }
      LOGGER.info("Selected all text in web element located by '{}' successfully", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to select all text in web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
  }

  public void paste(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Pasting text in web element located by '{}'", locator);
      String osName = System.getProperty("os.name");
      we.click();
      if(osName.toLowerCase().contains("mac")) {
        we.sendKeys(Keys.chord(Keys.COMMAND, "v"));
      } else {
        we.sendKeys(Keys.chord(Keys.CONTROL, "v"));
      }
      LOGGER.info("Pasted text in web element located by '{}' successfully", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to paste text in web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
  }

  public void clickOn(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Clicking on web element located by '{}'", locator);
      we.click();
      LOGGER.info("Clicked on web element located by '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to click on web element located by '{}'. Root cause: {}", locator, e.getMessage());
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

  public void submit(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Submitting web element located by '{}'", locator);
      we.submit();
      LOGGER.info("Submitted web element located by '{}'", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to submit web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
  }

  public String getText(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Getting text from web element located by '{}'", locator);
      String text = we.getText();
      LOGGER.info("Retrieved text '{}' from web element located by '{}'", text, locator);
      return text;
    } catch (Exception e) {
      LOGGER.error("Failed to get text from web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return null;
  }

  public String getTagName(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Getting tag name from web element located by '{}'", locator);
      String tagName = we.getTagName();
      LOGGER.info("Retrieved tag name '{}' from web element located by '{}'", tagName, locator);
      return tagName;
    } catch (Exception e) {
      LOGGER.error("Failed to get tag name from web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return null;
  }

  public String getCssValue(String locator, String css) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Getting CSS value from web element located by '{}'", locator);
      String cssValue = we.getCssValue(css);
      LOGGER.info("Retrieved CSS value '{}' from web element located by '{}'", cssValue, locator);
      return cssValue;
    } catch (Exception e) {
      LOGGER.error("Failed to get CSS value from web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return null;
  }

  public String getAttributeValue(String locator, String attributeName) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Getting attribute value from web element located by '{}' for attribute '{}'", locator, attributeName);
      String attributeValue = we.getAttribute(attributeName);
      LOGGER.info("Retrieved attribute value '{}' from web element located by '{}' for attribute '{}'", attributeValue, locator, attributeName);
      return attributeValue;
    } catch (Exception e) {
      LOGGER.error("Failed to get attribute value from web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return null;
  }

  public int getElementHeigh(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Getting height of web element located by '{}'", locator);
      int height = we.getSize().getHeight();
      LOGGER.info("Retrieved height '{}' from web element located by '{}'", height, locator);
      return height;
    } catch (Exception e) {
      LOGGER.error("Failed to get height from web element located by '{}'. Root cause: {}", locator, e.getMessage());
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
      LOGGER.error("Failed to get width from web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return -1;
  }

  public int getHorizontalPosition(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Getting horizontal position of web element located by '{}'", locator);
      int position = we.getLocation().getX();
      LOGGER.info("Retrieved horizontal position '{}' from web element located by '{}'", position, locator);
      return position;
    } catch (Exception e) {
      LOGGER.error("Failed to get horizontal position from web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return -1;
  }

  public int getVerticalPosition(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Getting vertical position of web element located by '{}'", locator);
      int position = we.getLocation().getY();
      LOGGER.info("Retrieved vertical position '{}' from web element located by '{}'", position, locator);
      return position;
    } catch (Exception e) {
      LOGGER.error("Failed to get vertical position from web element located by '{}'. Root cause: {}", locator, e.getMessage());
      return -1;
    }
  }

  public boolean verifyPageTitle(String expectedTitle) {
    try {
      LOGGER.info("Verifying page title");
      String actualTitle = driver.getTitle();
      if(actualTitle.equals(expectedTitle)) {
        LOGGER.info("Actual page title '{}' and expected title '{}' are the same", actualTitle, expectedTitle);
        return true;
      }
      LOGGER.error("Actual page title '{}' and expected title '{}' are not the same", actualTitle, expectedTitle);
    } catch (Exception e) {
      LOGGER.error("Failed to verify page title. Root cause: {}", e.getMessage());
    }
    return false;
  }

  public boolean verifyUrl(String expectedUrl) {
    try {
      LOGGER.info("Verifying url");
      String actualUrl = driver.getCurrentUrl();
      if(actualUrl.equals(expectedUrl)) {
        LOGGER.info("Actual url '{}' and expected url '{}' are the same", actualUrl, expectedUrl);
        return true;
      }
      LOGGER.error("Actual url '{}' and expected url '{}' are not the same", actualUrl, expectedUrl);
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
      if(actualText.equals(expectedText)) {
        LOGGER.info("Actual text '{}' and expected text '{}' are the same", actualText, expectedText);
        return true;
      }
      LOGGER.error("Actual text '{}' and expected text '{}' are not the same", actualText, expectedText);
    } catch (Exception e) {
      LOGGER.error("Failed to verify text of web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return false;
  }

  public boolean verifyElementText(WebElement we, String expectedText) {
    try {
      LOGGER.info("Verifying text of web element '{}'", we);
      String actualText = we.getText();
      if(actualText.equals(expectedText)) {
        LOGGER.info("Actual text '{}' and expected text '{}' are the same", actualText, expectedText);
        return true;
      }
      LOGGER.error("Actual text '{}' and expected text '{}' are not the same", actualText, expectedText);
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
      if(actualText.contains(expectedText)) {
        LOGGER.info("Actual text '{}' contains expected text '{}'", actualText, expectedText);
        return true;
      }
      LOGGER.error("Actual text '{}' does not contain expected text '{}'", actualText, expectedText);
    } catch (Exception e) {
      LOGGER.error("Failed to verify to contain text of web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return false;
  }

  public boolean verifyElementVisible(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Verifying visibility of web element located by '{}'", locator);
      if(we.isDisplayed()) {
        LOGGER.info("Web element located by '{}' is visible", locator);
        return true;
      }
      LOGGER.error("Web element located by '{}' is not visible", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to verify visibility of web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return false;
  }

  public boolean verifyElementNotVisible(String locator) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Verifying invisibility of web element located by '{}'", locator);
      if(!we.isDisplayed()) {
        LOGGER.info("Web element located by '{}' is not visible", locator);
        return true;
      }
      LOGGER.error("Web element located by '{}' is visible", locator);
    } catch (Exception e) {
      LOGGER.error("Failed to verify invisibility of web element located by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return false;
  }

  public boolean verifyElementPresent(String locator) {
    WebElement we = findWebElement(locator);
    if(we != null) {
      LOGGER.info("Web element located by '{}' is present", locator);
      return true;
    }
    LOGGER.error("Web element located by '{}' is not present", locator);
    return false;
  }

  public boolean verifyElementNotPresent(String locator) {
    WebElement we = findWebElement(locator);
    if(we == null) {
      LOGGER.info("Web element located by '{}' is not present", locator);
      return true;
    }
    LOGGER.error("Web element located by '{}' is present", locator);
    return false;
  }
}
