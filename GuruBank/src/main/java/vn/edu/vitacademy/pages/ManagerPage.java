package vn.edu.vitacademy.pages;

import vn.edu.vitacademy.common.keywords.WebUI;

public class ManagerPage extends BasePage {

  public ManagerPage(WebUI webUI) {
    super(webUI);
    setRepoName(ManagerPage.class.getSimpleName());
  }

  public boolean shouldShowWelcomeMessageAs(String expectedMessage) {
    if(webUI.verifyElementText(findTestObject("LBL_WELCOME"), expectedMessage)) {
      webUI.takeScreenshotAndMarkElement(findTestObject("LBL_WELCOME"));
      return true;
    }
    return false;
  }

  public boolean shouldShowUserIdAs(String expectedUserId) {
    if(webUI.verifyElementText(findTestObject("LBL_USER_ID"), expectedUserId)) {
      webUI.takeScreenshotAndMarkElement(findTestObject("LBL_USER_ID"));
      return true;
    }
    return false;
  }
}
