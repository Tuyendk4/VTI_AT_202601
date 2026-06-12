package vn.edu.vitacademy.pages;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.Employee;
import vn.edu.vitacademy.pages.components.AddEmployeePopup;
import vn.edu.vitacademy.tests.EmployeesTest;

// is-a: Eployees page is a web page
public class EmployeesPage extends BasePage {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesTest.class);
  private static final String BROWSER = "Chrome";
  private static final String URL = "https://demoqa.com/webtables";

  private static final String BTN_ADD = "//button[@id='addNewRecordButton']";
  private static final String BTN_EDIT = "//td[normalize-space()='${param}']/following-sibling::td//span[starts-with(@id,'edit-record')]";

  private static final String EMPLOYEE_TABLE_BTN_EDITS = "//span[starts-with(@id,'edit-record')]";
  private static final String EMPLOYEE_TABLE_BTN_ACTIONS = "//div[@class='action-buttons']";

  private static final String EMPLOYEE_TABLE_LBL_FIRST_NAMES = "//td[1]";
  private static final String EMPLOYEE_TABLE_LBL_LAST_NAMES = "//td[2]";
  private static final String EMPLOYEE_TABLE_LBL_AGES = "//td[3]";
  private static final String EMPLOYEE_TABLE_LBL_EMAILS = "//td[4]";
  private static final String EMPLOYEE_TABLE_LBL_SALARIES = "//td[5]";
  private static final String EMPLOYEE_TABLE_LBL_DEPARTMENTS = "//td[6]";

  //Has-a: Employees page has an Add Employee popup
  private AddEmployeePopup addEmployeePopup;

  public EmployeesPage(WebUI webUI) {
    super(webUI);
    addEmployeePopup = new AddEmployeePopup(webUI);
  }

  public void createEmployee(String newFirstName, String newLastName, String newEmail,
      String newAge, String newSalary, String newDepartment) {
    clickAddButton();
    if (addEmployeePopup.isVisible()) {
      addEmployeePopup.inputFirstNameOnRegistrationForm(newFirstName);
      addEmployeePopup.inputLastNameOnRegistrationForm(newLastName);
      addEmployeePopup.inputAgeOnRegistrationForm(newAge);
      addEmployeePopup.inputEmailOnRegistrationForm(newEmail);
      addEmployeePopup.inputSalaryOnRegistrationForm(newSalary);
      addEmployeePopup.inputDepartmentOnRegistrationForm(newDepartment);
      addEmployeePopup.clickSubmitButtonOnRegistrationForm();
    }
  }

  public void createEmployee(Employee employee) {
    clickAddButton();
    if (addEmployeePopup.isVisible()) {
      addEmployeePopup.inputFirstNameOnRegistrationForm(employee.getFirstName());
      addEmployeePopup.inputLastNameOnRegistrationForm(employee.getLastName());
      addEmployeePopup.inputAgeOnRegistrationForm(employee.getAge());
      addEmployeePopup.inputEmailOnRegistrationForm(employee.getEmail());
      addEmployeePopup.inputSalaryOnRegistrationForm(employee.getSalary());
      addEmployeePopup.inputDepartmentOnRegistrationForm(employee.getDepartment());
      addEmployeePopup.clickSubmitButtonOnRegistrationForm();
      webUI.delayInSeconds(3);
    }
  }


  public void editEmployee(String email, String newFirstName, String newLastName, String newEmail,
      String newAge, String newSalary, String newDepartment) {
    clickEditButtonOfEmail(email);
    if (addEmployeePopup.isVisible()) {
      addEmployeePopup.inputFirstNameOnRegistrationForm(newFirstName);
      addEmployeePopup.inputLastNameOnRegistrationForm(newLastName);
      addEmployeePopup.inputAgeOnRegistrationForm(newAge);
      addEmployeePopup.inputEmailOnRegistrationForm(newEmail);
      addEmployeePopup.inputSalaryOnRegistrationForm(newSalary);
      addEmployeePopup.inputDepartmentOnRegistrationForm(newDepartment);
      addEmployeePopup.clickSubmitButtonOnRegistrationForm();
    }
  }

  public void deleteEmployee(String email) {
    clickDeleteButtonOfEmail(email);
  }

  public void clickAddButton() {
    webUI.clickOn(BTN_ADD);
  }

  public void clickEditButtonOfEmail(String email) {
    // Solution 1: use xpath to find the edit button
//    WebElement btnEdit = webUI.findWebElement(BTN_EDIT, email);
//    webUI.clickOn(btnEdit);

    // Solution 2: use list web elements to find the edit button
    List<WebElement> lblEmails = webUI.findWebElements(EMPLOYEE_TABLE_LBL_EMAILS);
    List<WebElement> btnEdits = webUI.findWebElements(EMPLOYEE_TABLE_BTN_ACTIONS);
    for (int i = 0; i < lblEmails.size(); i++) {
      if (webUI.verifyElementText(lblEmails.get(i), email)) {
        webUI.clickOffset(btnEdits.get(i), -36, 0);
        break;
      }
    }
  }

  public void clickDeleteButtonOfEmail(String email) {
    // Solution 1: use xpath to find the edit button
//    WebElement btnEdit = webUI.findWebElement(BTN_EDIT, email);
//    webUI.clickOn(btnEdit);

    // Solution 2: use list web elements to find the edit button
    List<WebElement> lblEmails = webUI.findWebElements(EMPLOYEE_TABLE_LBL_EMAILS);
    List<WebElement> btnActions = webUI.findWebElements(EMPLOYEE_TABLE_BTN_ACTIONS);
    for (int i = 0; i < lblEmails.size(); i++) {
      if (webUI.verifyElementText(lblEmails.get(i), email)) {
        webUI.clickOffset(btnActions.get(i), -12, 0);
        break;
      }
    }
  }



  public boolean shouldShowFirstNameInEmployeesTable(String expectedFirstName) {
    List<WebElement> firstNames = webUI.findWebElements(EMPLOYEE_TABLE_LBL_FIRST_NAMES);
    for (WebElement firstName : firstNames) {
      if (webUI.verifyElementText(firstName, expectedFirstName)) {
        return true;
      }
    }
    return false;
  }

  public boolean shouldShowLastNameInEmployeesTable(String expectedLastName) {
    List<WebElement> lastNames = webUI.findWebElements(EMPLOYEE_TABLE_LBL_LAST_NAMES);
    for (WebElement lastName : lastNames) {
      if (webUI.verifyElementText(lastName, expectedLastName)) {
        return true;
      }
    }
    return false;
  }

  public boolean shouldNotShowFirstNameInEmployeesTable(String expectedFirstName) {
    List<WebElement> firstNames = webUI.findWebElements(EMPLOYEE_TABLE_LBL_FIRST_NAMES);
    for (WebElement firstName : firstNames) {
      if (webUI.verifyElementText(firstName, expectedFirstName)) {
        return false;
      }
    }
    return true;
  }

  public boolean shouldNotShowLastNameInEmployeesTable(String expectedLastName) {
    List<WebElement> lastNames = webUI.findWebElements(EMPLOYEE_TABLE_LBL_LAST_NAMES);
    for (WebElement lastName : lastNames) {
      if (webUI.verifyElementText(lastName, expectedLastName)) {
        return false;
      }
    }
    return true;
  }
}
