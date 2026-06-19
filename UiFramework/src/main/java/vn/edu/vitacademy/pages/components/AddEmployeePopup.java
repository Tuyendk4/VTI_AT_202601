package vn.edu.vitacademy.pages.components;

import io.qameta.allure.Step;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.BasePage;

public class AddEmployeePopup extends BasePage {

  public AddEmployeePopup(WebUI webUI) {
    super(webUI);
    setRepoName(AddEmployeePopup.class.getSimpleName());
  }

  @Step("Show add employee popup")
  public boolean isVisible() {
    return webUI.waitForElementVisible(findTestObject("DLG_REGISTRATION_FORM"), 30);
  }

  @Step("Input first name '{0}' on registration form")
  public void inputFirstNameOnRegistrationForm(String firstName) {
    webUI.clearText(findTestObject("REGISTRATION_FORM_TXT_FIRST_NAME"), 20);
    webUI.inputText(findTestObject("REGISTRATION_FORM_TXT_FIRST_NAME"), firstName, 10);
    webUI.attachmentScreenshotWhichMarkElement(findTestObject("REGISTRATION_FORM_TXT_FIRST_NAME"));
  }

  @Step("Input last name '{0}' on registration form")
  public void inputLastNameOnRegistrationForm(String lastName) {
    webUI.clearText(findTestObject("REGISTRATION_FORM_TXT_LAST_NAME"));
    webUI.inputText(findTestObject("REGISTRATION_FORM_TXT_LAST_NAME"), lastName);
    webUI.attachmentScreenshotWhichMarkElement(findTestObject("REGISTRATION_FORM_TXT_LAST_NAME"));
  }

  @Step("Input age '{0}' on registration form")
  public void inputAgeOnRegistrationForm(String age) {
    webUI.clearText(findTestObject("REGISTRATION_FORM_TXT_AGE"));
    webUI.inputText(findTestObject("REGISTRATION_FORM_TXT_AGE"), age);
    webUI.attachmentScreenshotWhichMarkElement(findTestObject("REGISTRATION_FORM_TXT_AGE"));
  }

  @Step("Input email '{0}' on registration form")
  public void inputEmailOnRegistrationForm(String email) {
    webUI.clearText(findTestObject("REGISTRATION_FORM_TXT_EMAIL"));
    webUI.inputText(findTestObject("REGISTRATION_FORM_TXT_EMAIL"), email);
    webUI.attachmentScreenshotWhichMarkElement(findTestObject("REGISTRATION_FORM_TXT_EMAIL"));
  }

  @Step("Input salary '{0}' on registration form")
  public void inputSalaryOnRegistrationForm(String salary) {
    webUI.clearText(findTestObject("REGISTRATION_FORM_TXT_SALARY"));
    webUI.inputText(findTestObject("REGISTRATION_FORM_TXT_SALARY"), salary);
    webUI.attachmentScreenshotWhichMarkElement(findTestObject("REGISTRATION_FORM_TXT_SALARY"));
  }

  @Step("Input department '{0}' on registration form")
  public void inputDepartmentOnRegistrationForm(String department) {
    webUI.clearText(findTestObject("REGISTRATION_FORM_TXT_DEPARTMENT"));
    webUI.inputText(findTestObject("REGISTRATION_FORM_TXT_DEPARTMENT"), department);
    webUI.attachmentScreenshotWhichMarkElement(findTestObject("REGISTRATION_FORM_TXT_DEPARTMENT"));
  }

  @Step("Click submit button on registration form")
  public void clickSubmitButtonOnRegistrationForm() {
    webUI.attachmentScreenshotWhichMarkElement(findTestObject("REGISTRATION_FORM_BTN_SUBMIT"));
    webUI.submit(findTestObject("REGISTRATION_FORM_BTN_SUBMIT"));
    webUI.delayInSeconds(1);
    webUI.attachmentScreenshot();
  }

}
