package vn.edu.vitacademy.common.keywords;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
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

  public WebElement findWebElement(String locator) {
    try {
      LOGGER.info("Finding web element located by '{}'", locator);
      WebElement we = driver.findElement(By.xpath(locator));
      if(we != null) {
        LOGGER.info("Found 1 web element located by '{}'", locator);
        return we;
      }
    } catch (Exception e) {
      LOGGER.error("Failed to find web element locate by '{}'. Root cause: {}", locator, e.getMessage());
    }
    return null;
  }

  public void inputText(String locator, String text) {
    WebElement we = findWebElement(locator);
    try {
      LOGGER.info("Inputting text: {}", text);
      we.sendKeys(text);
      LOGGER.info("Text input completed");
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
}
