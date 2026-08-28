package web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class OpenWebsiteTest {

    @Test
    public void openWorkDoWebsite() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("https://dash-demo.workdo.io");

        Thread.sleep(5000);

        driver.quit();
    }
}