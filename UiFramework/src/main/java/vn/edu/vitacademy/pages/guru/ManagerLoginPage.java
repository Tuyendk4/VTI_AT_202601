package vn.edu.vitacademy.pages.guru;

import io.qameta.allure.Step;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.BasePage;

/**
 * Guru99 Banking manager login page (https://demo.guru99.com/V4/).
 * Credentials are supplied externally (TestNG suite parameters), never hard-coded.
 */
public class ManagerLoginPage extends BasePage {

  public ManagerLoginPage(WebUI webUI) {
    super(webUI);
    setRepoName(ManagerLoginPage.class.getSimpleName());
  }

  @Step("Login as manager with user id '{0}'")
  public ManagerHomePage login(String userId, String password) {
    webUI.inputText(findTestObject("TXT_USER_ID"), userId, 15);
    webUI.inputText(findTestObject("TXT_PASSWORD"), password, 10);
    webUI.clickOn(findTestObject("BTN_LOGIN"), 10);
    return new ManagerHomePage(webUI);
  }
}
