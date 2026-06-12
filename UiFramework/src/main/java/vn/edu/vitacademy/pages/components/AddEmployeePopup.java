package vn.edu.vitacademy.pages.components;

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

  public boolean isVisible() {
    return webUI.waitForElementVisible(DLG_REGISTRATION_FORM, 30);
  }

  public void inputFirstNameOnRegistrationForm(String firstName) {
    webUI.clearText(REGISTRATION_FORM_TXT_FIRST_NAME, 20);
    webUI.inputText(REGISTRATION_FORM_TXT_FIRST_NAME, firstName, 10);
  }

  public void inputLastNameOnRegistrationForm(String lastName) {
    webUI.clearText(REGISTRATION_FORM_TXT_LAST_NAME);
    webUI.inputText(REGISTRATION_FORM_TXT_LAST_NAME, lastName);
  }

  public void inputAgeOnRegistrationForm(String age) {
    webUI.clearText(REGISTRATION_FORM_TXT_AGE);
    webUI.inputText(REGISTRATION_FORM_TXT_AGE, age);
  }

  public void inputEmailOnRegistrationForm(String email) {
    webUI.clearText(REGISTRATION_FORM_TXT_EMAIL);
    webUI.inputText(REGISTRATION_FORM_TXT_EMAIL, email);
  }

  public void inputSalaryOnRegistrationForm(String salary) {
    webUI.clearText(REGISTRATION_FORM_TXT_SALARY);
    webUI.inputText(REGISTRATION_FORM_TXT_SALARY, salary);
  }

  public void inputDepartmentOnRegistrationForm(String department) {
    webUI.clearText(REGISTRATION_FORM_TXT_DEPARTMENT);
    webUI.inputText(REGISTRATION_FORM_TXT_DEPARTMENT, department);
  }

  public void clickSubmitButtonOnRegistrationForm() {
    webUI.submit(REGISTRATION_FORM_BTN_SUBMIT);
  }

}
