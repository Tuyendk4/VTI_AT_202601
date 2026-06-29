package vn.edu.vitacademy.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vitacademy.common.helper.DateTimeHelper;
import vn.edu.vitacademy.common.helper.FileHelper;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.Employee;
import vn.edu.vitacademy.pages.components.AddEmployeePopup;

// is-a: Eployees page is a web page
public class EmployeesPage extends BasePage {

  public static final Logger LOGGER = LoggerFactory.getLogger(EmployeesPage.class);
  //Has-a: Employees page has an Add Employee popup
  private AddEmployeePopup addEmployeePopup;

  public EmployeesPage(WebUI webUI) {
    super(webUI);
    addEmployeePopup = new AddEmployeePopup(webUI);
    setRepoName(EmployeesPage.class.getSimpleName());
  }

  public AddEmployeePopup onAddEmployeePopup() {
    return new AddEmployeePopup(webUI);
  }

  @Step("Create new employee with first name '{0}', last name '{1}', email '{2}', age '{3}', salary '{4}', department '{5}'")
  public void createEmployee(String newFirstName, String newLastName, String newEmail,
      String newAge, String newSalary, String newDepartment) {
    clickAddButton();
    if (addEmployeePopup.isVisible()) {
      addEmployeePopup.inputFirstName(newFirstName);
      addEmployeePopup.inputLastName(newLastName);
      addEmployeePopup.inputAge(newAge);
      addEmployeePopup.inputEmail(newEmail);
      addEmployeePopup.inputSalary(newSalary);
      addEmployeePopup.inputDepartment(newDepartment);
      addEmployeePopup.clickSubmitButton();
    }
  }

  @Step("Create new employee: {0}")
  public void createEmployee(Employee employee) {
    clickAddButton();
    if (addEmployeePopup.isVisible()) {
      addEmployeePopup.inputFirstName(employee.getFirstName());
      addEmployeePopup.inputLastName(employee.getLastName());
      addEmployeePopup.inputAge(employee.getAge());
      addEmployeePopup.inputEmail(employee.getEmail());
      addEmployeePopup.inputSalary(employee.getSalary());
      addEmployeePopup.inputDepartment(employee.getDepartment());
      addEmployeePopup.clickSubmitButton();
      webUI.delayInSeconds(3);
    }
  }


  @Step("Edit employee with email '{0}' by new first name '{1}', new last name '{2}', new email '{3}', new age '{4}', new salary '{5}', new department '{6}'")
  public void editEmployee(String email, String newFirstName, String newLastName, String newEmail,
      String newAge, String newSalary, String newDepartment) {
    clickEditButtonOfEmail(email);
    if (addEmployeePopup.isVisible()) {
      addEmployeePopup.inputFirstName(newFirstName);
      addEmployeePopup.inputLastName(newLastName);
      addEmployeePopup.inputAge(newAge);
      addEmployeePopup.inputEmail(newEmail);
      addEmployeePopup.inputSalary(newSalary);
      addEmployeePopup.inputDepartment(newDepartment);
      addEmployeePopup.clickSubmitButton();
    }
  }

  @Step("Edit employee with email '{0}' by new employee '{1}'")
  public void editEmployee(String email, Employee employee) {
    clickEditButtonOfEmail(email);
    if (addEmployeePopup.isVisible()) {
      addEmployeePopup.inputFirstName(employee.getFirstName());
      addEmployeePopup.inputLastName(employee.getLastName());
      addEmployeePopup.inputAge(employee.getAge());
      addEmployeePopup.inputEmail(employee.getEmail());
      addEmployeePopup.inputSalary(employee.getSalary());
      addEmployeePopup.inputDepartment(employee.getDepartment());
      addEmployeePopup.clickSubmitButton();
    }
  }

  @Step("Delete employee with email '{0}'")
  public void deleteEmployee(String email) {
    clickDeleteButtonOfEmail(email);
  }

  @Step("Nhấn nút Thêm")
  public void clickAddButton() {
    webUI.clickOn(findTestObject("BTN_ADD"));
  }

  @Step("Nhấn nút Chỉnh sửa của email '{0}'")
  public void clickEditButtonOfEmail(String email) {
    // Solution 1: use xpath to find the edit button
//    WebElement btnEdit = webUI.findWebElement(BTN_EDIT, email);
//    webUI.clickOn(btnEdit);

    // Solution 2: use list web elements to find the edit button
    List<WebElement> lblEmails = webUI.findWebElements(findTestObject("EMPLOYEE_TABLE_LBL_EMAILS"));
    List<WebElement> btnEdits = webUI.findWebElements(findTestObject("EMPLOYEE_TABLE_BTN_ACTIONS"));
    for (int i = 0; i < lblEmails.size(); i++) {
      if (webUI.verifyElementText(lblEmails.get(i), email)) {
        webUI.clickOffset(btnEdits.get(i), -36, 0);
        break;
      }
    }
  }

  @Step("Click delete button of employee with email '{0}'")
  public void clickDeleteButtonOfEmail(String email) {
    // Solution 1: use xpath to find the edit button
//    WebElement btnEdit = webUI.findWebElement(BTN_EDIT, email);
//    webUI.clickOn(btnEdit);

    // Solution 2: use list web elements to find the edit button
    List<WebElement> lblEmails = webUI.findWebElements(findTestObject("EMPLOYEE_TABLE_LBL_EMAILS"));
    List<WebElement> btnActions = webUI.findWebElements(findTestObject("EMPLOYEE_TABLE_BTN_ACTIONS"));
    for (int i = 0; i < lblEmails.size(); i++) {
      if (webUI.verifyElementText(lblEmails.get(i), email)) {
        webUI.clickOffset(btnActions.get(i), -12, 0);
        break;
      }
    }
  }

  @Step("Should show first name '{0}' in employees table")
  public boolean shouldShowFirstNameInEmployeesTable(String expectedFirstName) {
    List<WebElement> firstNames = webUI.findWebElements(findTestObject("EMPLOYEE_TABLE_LBL_FIRST_NAMES"));
    for (WebElement firstName : firstNames) {
      if (webUI.verifyElementText(firstName, expectedFirstName)) {
        String targetFilePath = IMAGES_FOLDER_PATH + File.separator + "element_marked_" + DateTimeHelper.formatCurrentDateAs("yyyyMMddHHmmss") + ".png";
        FileHelper.saveFile(webUI.takeScreenshotAndMarkElement(firstName), targetFilePath);
        try (InputStream is = Files.newInputStream(Paths.get(targetFilePath))) {
          Allure.addAttachment("Screenshot", is);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        return true;
      }
    }
    return false;
  }

  @Step("Should show last name '{0}' in employees table")
  public boolean shouldShowLastNameInEmployeesTable(String expectedLastName) {
    List<WebElement> lastNames = webUI.findWebElements(findTestObject("EMPLOYEE_TABLE_LBL_LAST_NAMES"));
    for (WebElement lastName : lastNames) {
      if (webUI.verifyElementText(lastName, expectedLastName)) {
        return true;
      }
    }
    return false;
  }

  @Step("Should not show first name '{0}' in employees table")
  public boolean shouldNotShowFirstNameInEmployeesTable(String expectedFirstName) {
    List<WebElement> firstNames = webUI.findWebElements(findTestObject("EMPLOYEE_TABLE_LBL_FIRST_NAMES"));
    for (WebElement firstName : firstNames) {
      if (webUI.verifyElementText(firstName, expectedFirstName)) {
        return false;
      }
    }
    return true;
  }

  @Step("Should not show last name '{0}' in employees table")
  public boolean shouldNotShowLastNameInEmployeesTable(String expectedLastName) {
    List<WebElement> lastNames = webUI.findWebElements(findTestObject("EMPLOYEE_TABLE_LBL_LAST_NAMES"));
    for (WebElement lastName : lastNames) {
      if (webUI.verifyElementText(lastName, expectedLastName)) {
        return false;
      }
    }
    return true;
  }
}
