package vn.edu.vitacademy.pages;

import io.qameta.allure.Step;
import vn.edu.vitacademy.common.keywords.WebUI;

public class CustomerRegisteredPage extends BasePage {

  public CustomerRegisteredPage(WebUI webUI) {
    super(webUI);
    setRepoName(CustomerRegisteredPage.class.getSimpleName());
  }

  @Step("Get success message")
  public String getSuccessMessage() {
    return webUI.getText(findTestObject("LBL_SUCCESS_MESSAGE"));
  }

  @Step("Get generated customer id")
  public String getCustomerId() {
    return webUI.getText(findTestObject("LBL_CUSTOMER_ID"));
  }

  @Step("Click Continue link")
  public ManagerPage clickContinue() {
    webUI.click(findTestObject("LNK_CONTINUE"));
    return new ManagerPage(webUI);
  }
}