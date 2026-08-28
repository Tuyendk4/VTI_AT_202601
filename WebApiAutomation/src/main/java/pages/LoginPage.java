package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("[data-test='login-button']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void open() {
        driver.get("https://dash-demo.workdo.io/login");
    }

    public void enterEmail(String email) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailField)
        );
        element.clear();
        element.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField)
        );
        element.clear();
        element.sendKeys(password);
    }

    public void clickLogin() {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        );
        button.click();
    }

    public boolean isEmailDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailField)
        ).isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginButton)
        ).isDisplayed();
    }
    public boolean isPasswordDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField)
        ).isDisplayed();
    }
}