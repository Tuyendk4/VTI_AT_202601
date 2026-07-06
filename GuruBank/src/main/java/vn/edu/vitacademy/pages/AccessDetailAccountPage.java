package vn.edu.vitacademy.pages;

import io.qameta.allure.Step;
import vn.edu.vitacademy.common.helper.DateTimeHelper;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.RegisteredUser;

public class AccessDetailAccountPage extends BasePage {

  public AccessDetailAccountPage(WebUI webUI) {
    super(webUI);
    setRepoName(AccessDetailAccountPage.class.getSimpleName());
  }

  @Step("Get User ID")
  public String getUserId() {
    return webUI.getText(findTestObject("LBL_USER_ID"));
  }

  @Step("Get Password")
  public String getPassword() {
    return webUI.getText(findTestObject("LBL_PASSWORD"));
  }

  @Step("Save registered user")
  public RegisteredUser saveRegisteredUser() {
    String createdDate = DateTimeHelper.formatCurrentDateAs("yyyyMMdd");
    return RegisteredUser.Builder.aUser()
        .withUserId(getUserId())
        .withPassword(getPassword())
        .withCreatedDate(createdDate)
        .build();
  }
}
