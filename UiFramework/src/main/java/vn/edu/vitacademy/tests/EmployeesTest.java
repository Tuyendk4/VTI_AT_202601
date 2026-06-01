package vn.edu.vitacademy.tests;

import static org.testng.Assert.assertTrue;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.keywords.WebUI;

public class EmployeesTest {

  private static final String BROWSER = "Chrome";
  private static final String URL = "https://demoqa.com/webtables";
  private static final String BTN_EDIT = "//td[normalize-space()='${param}']/following-sibling::td//span[starts-with(@id,'edit-record')]";

  private static final String EMPLOYEE_TABLE_BTN_EDITS = "//span[starts-with(@id,'edit-record')]";
  private static final String DLG_REGISTRATION_FORM = "//div[@class='modal-content']";
  private static final String REGISTRATION_FORM_TXT_FIRST_NAME = "//input[@id='firstName']";
  private static final String REGISTRATION_FORM_TXT_LAST_NAME = "//input[@id='lastName']";
  private static final String REGISTRATION_FORM_TXT_EMAIL = "//input[@id='userEmail']";
  private static final String REGISTRATION_FORM_TXT_AGE = "//input[@id='age']";
  private static final String REGISTRATION_FORM_TXT_SALARY = "//input[@id='salary']";
  private static final String REGISTRATION_FORM_TXT_DEPARTMENT = "//input[@id='department']";
  private static final String REGISTRATION_FORM_BTN_SUBMIT = "//button[@id='submit']";

  private static final String EMPLOYEE_TABLE_LBL_FIRST_NAMES = "//td[1]";
  private static final String EMPLOYEE_TABLE_LBL_LAST_NAMES = "//td[2]";
  private static final String EMPLOYEE_TABLE_LBL_AGES = "//td[3]";
  private static final String EMPLOYEE_TABLE_LBL_EMAILS = "//td[4]";
  private static final String EMPLOYEE_TABLE_LBL_SALARIES = "//td[5]";
  private static final String EMPLOYEE_TABLE_LBL_DEPARTMENTS = "//td[6]";
  private WebUI webUI;

  @Test(description = "EM001 - Edit email successfully")
  public void TC01_edit_email_successfully() {
    webUI = new WebUI();
    webUI.openBrowser(BROWSER, URL);
    editEmail("kierra@example.com", "John", "Doe", "johndoe@mailinator.com",
        "34", "3000", "IT");
    assertTrue(shouldShowFirstNameInEmployeesTable("John"));
    assertTrue(shouldShowLastNameInEmployeesTable("Doe"));
    webUI.closeBrowser();
  }


  public void editEmail(String email, String newFirstName, String newLastName, String newEmail,
      String newAge, String newSalary, String newDepartment) {
    clickEditButtonOfEmail(email);
    if(isRegistrationFormVisible()) {
      inputFirstNameOnRegistrationForm(newFirstName);
      inputLastNameOnRegistrationForm(newLastName);
      inputAgeOnRegistrationForm(newAge);
      inputEmailOnRegistrationForm(newEmail);
      inputSalaryOnRegistrationForm(newSalary);
      inputDepartmentOnRegistrationForm(newDepartment);
      clickSubmitButtonOnRegistrationForm();
    }
  }

  public void clickEditButtonOfEmail(String email) {
    // Solution 1: use xpath to find the edit button
//    WebElement btnEdit = webUI.findWebElement(BTN_EDIT, email);
//    webUI.clickOn(btnEdit);

    // Solution 2: use list web elements to find the edit button
    List<WebElement> lblEmails = webUI.findWebElements(EMPLOYEE_TABLE_LBL_EMAILS);
    List<WebElement> btnEdits = webUI.findWebElements(EMPLOYEE_TABLE_BTN_EDITS);
    for (int i = 0; i < lblEmails.size(); i++) {
      if (webUI.verifyElementText(lblEmails.get(i), email)) {
        webUI.clickOn(btnEdits.get(i));
        break;
      }
    }
  }

  public boolean isRegistrationFormVisible() {
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

  public boolean shouldShowFirstNameInEmployeesTable(String expectedFirstName) {
    List<WebElement> firstNames = webUI.findWebElements(EMPLOYEE_TABLE_LBL_FIRST_NAMES);
    for (WebElement firstName : firstNames) {
      if(webUI.verifyElementText(firstName, expectedFirstName)) {
        return true;
      }
    }
    return false;
  }

  public boolean shouldShowLastNameInEmployeesTable(String expectedLastName) {
    List<WebElement> lastNames = webUI.findWebElements(EMPLOYEE_TABLE_LBL_LAST_NAMES);
    for (WebElement lastName : lastNames) {
      if(webUI.verifyElementText(lastName, expectedLastName)) {
        return true;
      }
    }
    return false;
  }
}
