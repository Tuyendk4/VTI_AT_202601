package vn.edu.vitacademy.pages.components;

import io.qameta.allure.Step;
import vn.edu.vitacademy.common.keywords.WebUI;

public class AddEmployeePopup {

  private static final String DLG_REGISTRATION_FORM = "//div[@class='modal-content']";
  private static final String REGISTRATION_FORM_TXT_FIRST_NAME = "//input[@id='firstName']";
  private static final String REGISTRATION_FORM_TXT_LAST_NAME = "//input[@id='lastName']";
  private static final String REGISTRATION_FORM_TXT_EMAIL = "//input[@id='userEmail']";
  private static final String REGISTRATION_FORM_TXT_AGE = "//input[@id='age']";
  private static final String REGISTRATION_FORM_TXT_SALARY = "//input[@id='salary']";
  private static final String REGISTRATION_FORM_TXT_DEPARTMENT = "//input[@id='department']";
  private static final String REGISTRATION_FORM_BTN_SUBMIT = "//button[@id='submit']";

  private WebUI webUI;

  public AddEmployeePopup(WebUI webUI) {
    this.webUI = webUI;
  }

  @Step("Show add employee popup")
  public boolean isVisible() {
    return webUI.waitForElementVisible(DLG_REGISTRATION_FORM, 30);
  }

  @Step("Input first name '{0}' on registration form")
  public void inputFirstNameOnRegistrationForm(String firstName) {
    webUI.clearText(REGISTRATION_FORM_TXT_FIRST_NAME, 20);
    webUI.inputText(REGISTRATION_FORM_TXT_FIRST_NAME, firstName, 10);
    webUI.attachmentScreenshotWhichMarkElement(REGISTRATION_FORM_TXT_FIRST_NAME);
  }

  @Step("Input last name '{0}' on registration form")
  public void inputLastNameOnRegistrationForm(String lastName) {
    webUI.clearText(REGISTRATION_FORM_TXT_LAST_NAME);
    webUI.inputText(REGISTRATION_FORM_TXT_LAST_NAME, lastName);
    webUI.attachmentScreenshotWhichMarkElement(REGISTRATION_FORM_TXT_LAST_NAME);
  }

  @Step("Input age '{0}' on registration form")
  public void inputAgeOnRegistrationForm(String age) {
    webUI.clearText(REGISTRATION_FORM_TXT_AGE);
    webUI.inputText(REGISTRATION_FORM_TXT_AGE, age);
    webUI.attachmentScreenshotWhichMarkElement(REGISTRATION_FORM_TXT_AGE);
  }

  @Step("Input email '{0}' on registration form")
  public void inputEmailOnRegistrationForm(String email) {
    webUI.clearText(REGISTRATION_FORM_TXT_EMAIL);
    webUI.inputText(REGISTRATION_FORM_TXT_EMAIL, email);
    webUI.attachmentScreenshotWhichMarkElement(REGISTRATION_FORM_TXT_EMAIL);
  }

  @Step("Input salary '{0}' on registration form")
  public void inputSalaryOnRegistrationForm(String salary) {
    webUI.clearText(REGISTRATION_FORM_TXT_SALARY);
    webUI.inputText(REGISTRATION_FORM_TXT_SALARY, salary);
    webUI.attachmentScreenshotWhichMarkElement(REGISTRATION_FORM_TXT_SALARY);
  }

  @Step("Input department '{0}' on registration form")
  public void inputDepartmentOnRegistrationForm(String department) {
    webUI.clearText(REGISTRATION_FORM_TXT_DEPARTMENT);
    webUI.inputText(REGISTRATION_FORM_TXT_DEPARTMENT, department);
    webUI.attachmentScreenshotWhichMarkElement(REGISTRATION_FORM_TXT_DEPARTMENT);
  }

  @Step("Click submit button on registration form")
  public void clickSubmitButtonOnRegistrationForm() {
    webUI.attachmentScreenshotWhichMarkElement(REGISTRATION_FORM_BTN_SUBMIT);
    webUI.submit(REGISTRATION_FORM_BTN_SUBMIT);
    webUI.delayInSeconds(1);
    webUI.attachmentScreenshot();
  }

}
