package vn.edu.vitacademy.pages.components;

import io.qameta.allure.Step;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.BasePage;
import vn.edu.vitacademy.pages.ManagerPage;
import vn.edu.vitacademy.pages.NewCustomerPage;
import vn.edu.vitacademy.pages.RegisterEmailPage;

public class LeftMenu extends BasePage {

  public LeftMenu(WebUI webUI) {
    super(webUI);
    setRepoName(LeftMenu.class.getSimpleName());
  }

  @Step("Move to Manager")
  public ManagerPage moveToManager() {
    webUI.takeScreenshotAndMarkElement(findTestObject("LNK_MANAGER"));
    webUI.clickOn(findTestObject("LNK_MANAGER"));
    webUI.delayInSeconds(3);
    return new ManagerPage(webUI);
  }

  @Step("Move to New Customer")
  public NewCustomerPage moveToNewCustomer() {
    webUI.takeScreenshotAndMarkElement(findTestObject("LNK_NEW_CUSTOMER"));
    webUI.clickOn(findTestObject("LNK_NEW_CUSTOMER"));
    webUI.delayInSeconds(3);
    return new NewCustomerPage(webUI);
  }
}
