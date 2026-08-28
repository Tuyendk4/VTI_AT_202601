package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DemoLoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public DemoLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );
    }

    public void openLoginPage() {
        driver.get("https://dash-demo.workdo.io/login");
    }

    public void loginAsCompanyDemo() {

        WebElement includedAddons = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//*[normalize-space()='Included Add-ons']"
                        )
                )
        );

        includedAddons.click();

        wait.until(
                ExpectedConditions.urlContains("/dashboard")
        );

        System.out.println(
                "URL sau Company Demo Login: "
                        + driver.getCurrentUrl()
        );
    }
}