package pages;

import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openHomePage() {
        driver.get("https://dash-demo.workdo.io");
    }

    public void openLoginPage() {
        driver.get("https://dash-demo.workdo.io/login");
    }

    public void openPricingPage() {
        driver.get("https://dash-demo.workdo.io/pricing");
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}