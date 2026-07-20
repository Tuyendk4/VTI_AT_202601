package vn.edu.vtiacademy.screens;

import io.qameta.allure.Step;
import vn.edu.vtiacademy.common.keywords.MobileUI;

public class LoginScreen extends BaseScreen {

  public LoginScreen(MobileUI mobileUI) {
    super(mobileUI);
    setRepoName(LoginScreen.class.getSimpleName());
  }

  @Step("Input email: {0}")
  public void inputEmail(String email) {
    mobileUI.inputText(findTestObject("TXT_EMAIL"), email);
    mobileUI.takeScreenshot();
    mobileUI.takeScreenshot(findTestObject("TXT_EMAIL"));
    mobileUI.takeScreenshotAndMarkElement(findTestObject("TXT_EMAIL"));
  }

  @Step("Input password: {0}")
  public void inputPassword(String password) {
    mobileUI.inputText(findTestObject("TXT_PASSWORD"), password);
    mobileUI.takeScreenshot();
  }

  @Step("Click login button")
  public void clickLoginButton() {
    mobileUI.takeScreenshot();
    mobileUI.tapOn(findTestObject("BTN_LOGIN"));
    mobileUI.delayInSeconds(2);
    mobileUI.takeScreenshot();
  }

  @Step("Should show email error message: {0}")
  public boolean shouldShowEmailErrorMessage(String expectedErrorMessage) {
    if(mobileUI.verifyElementText(findTestObject("LBL_EMAIL_ERROR_MESSAGE"), expectedErrorMessage)) {
      mobileUI.takeScreenshot();
      return true;
    }
    mobileUI.takeScreenshot();
    return false;
  }

  @Step("Should show password error message: {0}")
  public boolean shouldShowPasswordErrorMessage(String expectedErrorMessage) {
    if(mobileUI.verifyElementText(findTestObject("LBL_PASSWORD_ERROR_MESSAGE"), expectedErrorMessage)) {
      mobileUI.takeScreenshot();
      return true;
    }
    mobileUI.takeScreenshot();
    return false;
  }
}
