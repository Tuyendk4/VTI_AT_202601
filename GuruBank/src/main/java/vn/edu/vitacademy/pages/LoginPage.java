package vn.edu.vitacademy.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vitacademy.common.helper.DateTimeHelper;
import vn.edu.vitacademy.common.helper.FileHelper;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.Employee;
import vn.edu.vitacademy.pages.components.AddEmployeePopup;

// is-a: Eployees page is a web page
public class LoginPage extends BasePage {

  public static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);
  //Has-a: Employees page has an Add Employee popup
  private AddEmployeePopup addEmployeePopup;

  public LoginPage(WebUI webUI) {
    super(webUI);
    setRepoName(LoginPage.class.getSimpleName());
  }

  @Step("Input email: {0}")
  private void inputEmail(String userId) {
    webUI.inputText(findTestObject("TXT_USER_ID"), userId);
    webUI.takeScreenshotAndMarkElement(findTestObject("TXT_USER_ID"));
  }

  @Step("Input password: {0}")
  private void inputPassword(String password) {
    webUI.inputText(findTestObject("TXT_PASSWORD"), password);
    webUI.takeScreenshotAndMarkElement(findTestObject("TXT_PASSWORD"));
  }

  @Step("Click Login button")
  private void clickLoginButton() {
    webUI.takeScreenshotAndMarkElement(findTestObject("BTN_LOGIN"));
    webUI.click(findTestObject("BTN_LOGIN"));
    webUI.delayInSeconds(2);
    webUI.takeScreenshot();
  }

  @Step("Login with email: {0} and password: {1}")
  public ManagerPage loginWith(String userId, String password) {
    inputEmail(userId);
    inputPassword(password);
    clickLoginButton();
    return new ManagerPage(webUI);
  }

  @Step("Click Here link")
  public RegisterEmailPage clickHereLink() {
    webUI.takeScreenshotAndMarkElement(findTestObject("LNK_HERE"));
    webUI.click(findTestObject("LNK_HERE"));
    webUI.delayInSeconds(2);
    webUI.takeScreenshot();
    return new RegisterEmailPage(webUI);
  }
}
