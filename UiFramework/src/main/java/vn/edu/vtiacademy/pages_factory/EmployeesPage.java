package vn.edu.vtiacademy.pages_factory;

import io.qameta.allure.Step;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import vn.edu.vtiacademy.common.keywords.WebUI;
import vn.edu.vtiacademy.model.Employee;
import vn.edu.vtiacademy.pages_factory.components.AddEmployeePopup;

// is-a: Eployees page is a web page
public class EmployeesPage extends BasePage {

  @FindBy(xpath = "//button[@id='addNewRecordButton']")
  private WebElement btnAdd;

  @FindBy(xpath = "//td[normalize-space()='${param}']/following-sibling::td//span[starts-with(@id,'edit-record')]")
  private WebElement btnEdit;

  @FindBy(xpath = "//span[starts-with(@id,'edit-record')]")
  private List<WebElement> employeeTable_BtnEdits;

  @FindBy(xpath = "//div[@class='action-buttons']")
  private List<WebElement> employeeTable_BtnActions;

  @FindBy(xpath = "//td[1]")
  private List<WebElement> employeeTable_LblFirstNames;

  @FindBy(xpath = "//td[2]")
  private List<WebElement> employeeTable_LblLastNames;

  @FindBy(xpath = "//td[3]")
  private List<WebElement> employeeTable_LblAges;

  @FindBy(xpath = "//td[4]")
  private List<WebElement> employeeTable_LblEmails;

  @FindBy(xpath = "//td[5]")
  private List<WebElement> employeeTable_LblSalaries;

  @FindBy(xpath = "//td[6]")
  private List<WebElement> employeeTable_LblDepartments;

  //Has-a: Employees page has an Add Employee popup
  private final AddEmployeePopup addEmployeePopup;

  public EmployeesPage(WebUI webUI) {
    super(webUI);
    addEmployeePopup = new AddEmployeePopup(webUI);
  }

  @Step("Create new employee with first name '{0}', last name '{1}', email '{2}', age '{3}', salary '{4}', department '{5}'")
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

  @Step("Create new employee: {0}")
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


  @Step("Edit employee with email '{0}' by new first name '{1}', new last name '{2}', new email '{3}', new age '{4}', new salary '{5}', new department '{6}'")
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

  @Step("Delete employee with email '{0}'")
  public void deleteEmployee(String email) {
    clickDeleteButtonOfEmail(email);
  }

  @Step("Nhấn nút Thêm")
  public void clickAddButton() {
    webUI.clickOn(btnAdd);
  }

  @Step("Nhấn nút Chỉnh sửa của email '{0}'")
  public void clickEditButtonOfEmail(String email) {
    for (int i = 0; i < employeeTable_LblEmails.size(); i++) {
      if (webUI.verifyElementText(employeeTable_LblEmails.get(i), email)) {
        webUI.clickOffset(employeeTable_BtnEdits.get(i), -36, 0);
        break;
      }
    }
  }

  @Step("Click delete button of employee with email '{0}'")
  public void clickDeleteButtonOfEmail(String email) {
    for (int i = 0; i < employeeTable_LblEmails.size(); i++) {
      if (webUI.verifyElementText(employeeTable_LblEmails.get(i), email)) {
        webUI.clickOffset(employeeTable_BtnActions.get(i), -12, 0);
        break;
      }
    }
  }

  @Step("Should show first name '{0}' in employees table")
  public boolean shouldShowFirstNameInEmployeesTable(String expectedFirstName) {
    for (WebElement firstName : employeeTable_LblFirstNames) {
      if (webUI.verifyElementText(firstName, expectedFirstName)) {
        webUI.takeScreenshotAndMarkElement(firstName);
        return true;
      }
    }
    return false;
  }

  @Step("Should show last name '{0}' in employees table")
  public boolean shouldShowLastNameInEmployeesTable(String expectedLastName) {
    for (WebElement lastName : employeeTable_LblLastNames) {
      if (webUI.verifyElementText(lastName, expectedLastName)) {
        webUI.takeScreenshotAndMarkElement(lastName);
        return true;
      }
    }
    return false;
  }

  @Step("Should not show first name '{0}' in employees table")
  public boolean shouldNotShowFirstNameInEmployeesTable(String expectedFirstName) {
    for (WebElement firstName : employeeTable_LblFirstNames) {
      if (webUI.verifyElementText(firstName, expectedFirstName)) {
        return false;
      }
    }
    webUI.attachmentScreenshot();
    return true;
  }

  @Step("Should not show last name '{0}' in employees table")
  public boolean shouldNotShowLastNameInEmployeesTable(String expectedLastName) {
    for (WebElement lastName : employeeTable_LblLastNames) {
      if (webUI.verifyElementText(lastName, expectedLastName)) {
        return false;
      }
    }
    webUI.attachmentScreenshot();
    return true;
  }
}
