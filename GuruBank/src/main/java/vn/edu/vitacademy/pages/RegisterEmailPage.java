package vn.edu.vitacademy.pages;

import io.qameta.allure.Step;
import vn.edu.vitacademy.common.keywords.WebUI;

public class RegisterEmailPage extends BasePage {

  public RegisterEmailPage(WebUI webUI) {
    super(webUI);
    setRepoName(RegisterEmailPage.class.getSimpleName());
  }

  @Step("Input email: {0}")
  private void inputEmail(String email) {
    webUI.inputText(findTestObject("TXT_EMAIL"), email);
    webUI.takeScreenshotAndMarkElement(findTestObject("TXT_EMAIL"));
  }

  @Step("Click Submit button")
  private void clickSubmitButton() {
    webUI.takeScreenshotAndMarkElement(findTestObject("BTN_SUBMIT"));
    webUI.click(findTestObject("BTN_SUBMIT"));
    webUI.delayInSeconds(2);
    webUI.takeScreenshot();
  }

  @Step("Register email: {0}")
  public AccessDetailAccountPage registerEmail(String email) {
    inputEmail(email);
    clickSubmitButton();
    return new AccessDetailAccountPage(webUI);
  }
}
