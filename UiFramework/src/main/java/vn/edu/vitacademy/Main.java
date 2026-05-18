package vn.edu.vitacademy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {

  public static void main() throws InterruptedException {
    System.setProperty("webdriver.chrome.driver", "/Users/tuyenluu/training-workspace/VTI_AT_202601/UiFramework/drivers/chromedriver-mac-x64/chromedriver");
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*");
    WebDriver driver = new ChromeDriver(options);
    driver.get("https://dantri.com.vn");
    Thread.sleep(5000);
    driver.findElement(By.xpath("//span[@class='dt-whitespace-nowrap'][contains(text(),'Thế giới')]"));
    Thread.sleep(5000);
    driver.close();
  }

}
