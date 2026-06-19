package vn.edu.vitacademy.tests;

import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.Employee;

public class EmployeesTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesTest.class);
  private static final String BROWSER = "Chrome";
  private static final String URL = "https://demoqa.com/webtables";

  private static final String BTN_ADD = "//button[@id='addNewRecordButton']";
  private static final String BTN_EDIT = "//td[normalize-space()='${param}']/following-sibling::td//span[starts-with(@id,'edit-record')]";

  private static final String EMPLOYEE_TABLE_BTN_EDITS = "//span[starts-with(@id,'edit-record')]";
  private static final String EMPLOYEE_TABLE_BTN_ACTIONS = "//div[@class='action-buttons']";
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

  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    LOGGER.info("==================Start suite");
  }

  @Parameters({"browser", "url"})
  @BeforeTest(alwaysRun = true)
  public void beforeTest(String browser, String url) {
    LOGGER.info("------------------Start test");
    webUI = new WebUI();
    webUI.openBrowser(browser, url);
    webUI.maximizeWindow();
  }


  @BeforeClass(alwaysRun = true)
  public void beforeClass() {
    LOGGER.info("Running test class: {}", this.getClass().getSimpleName());
  }

  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method method) {
    LOGGER.info("Running test method: {}", method.getName());
  }

  //Arrange
  @DataProvider(name = "EM001")
  private Object[][] dataProvider() {
    return new Object[][] {
        { new Employee("Helle", "Doe", "helledoe@mailinator.com", "34", "3000", "IT")},
        { new Employee("Long", "Liu", "longliu@mailinator.com", "34", "3000", "IT")}
    };
  }

  @Test(description = "EM001 - Create email successfully", dataProvider = "EM001", groups = {"regression"})
  public void EM001_create_email_successfully(Employee employee) {
    //Action - Step
    createEmployee(employee);

    //Assertion
    assertTrue(shouldShowFirstNameInEmployeesTable(employee.getFirstName())); //is, has, should, ensureThat
    assertTrue(shouldShowLastNameInEmployeesTable(employee.getLastName()));
  }


  @Test(description = "EM002 - Edit email successfully", groups = {"smoke", "regression"})
  public void EM002_edit_email_successfully() {
//    webUI = new WebUI();
//    webUI.openBrowser(BROWSER, URL);
//    webUI.maximizeWindow();
    SoftAssert softAssert = new SoftAssert();
    editEmployee("kierra@example.com", "John", "Doe", "johndoe@mailinator.com",
        "34", "3000", "IT");
    softAssert.assertTrue(shouldShowFirstNameInEmployeesTable("Jozhn"), "Should show first name in Employees table");
    softAssert.assertTrue(shouldShowLastNameInEmployeesTable("Does"), "Should show last name in Employees table");
    softAssert.assertAll();
//    webUI.closeBrowser();
  }

  @Test(description = "EM003 - Delete email successfully", groups = {"smoke", "regression"})
  public void EM003_delete_email_successfully() {
    deleteEmployee("johndoe@mailinator.com");
    webUI.delayInSeconds(5);
    assertTrue(shouldNotShowFirstNameInEmployeesTable("John"));
    assertTrue(shouldNotShowLastNameInEmployeesTable("Doe"));
  }

  @AfterMethod(alwaysRun = true)
  public void afterMethod(Method method) {
    LOGGER.info("Ended test method: {}", method.getName());
  }

  @AfterClass(alwaysRun = true)
  public void afterClass() {
    webUI.closeBrowser();
    LOGGER.info("Ended test class: {}", this.getClass().getSimpleName());
  }

  @AfterTest(alwaysRun = true)
  public void afterTest() {
    webUI.closeBrowser();
    LOGGER.info("------------------Ended test");
  }

  @AfterSuite(alwaysRun = true)
  public void afterSuite() {
    LOGGER.info("==================Ended suite");
  }


  public void createEmployee(String newFirstName, String newLastName, String newEmail,
      String newAge, String newSalary, String newDepartment) {
    clickAddButton();
    if (isRegistrationFormVisible()) {
      inputFirstNameOnRegistrationForm(newFirstName);
      inputLastNameOnRegistrationForm(newLastName);
      inputAgeOnRegistrationForm(newAge);
      inputEmailOnRegistrationForm(newEmail);
      inputSalaryOnRegistrationForm(newSalary);
      inputDepartmentOnRegistrationForm(newDepartment);
      clickSubmitButtonOnRegistrationForm();
    }
  }

  public void createEmployee(Employee employee) {
    clickAddButton();
    if (isRegistrationFormVisible()) {
      inputFirstNameOnRegistrationForm(employee.getFirstName());
      inputLastNameOnRegistrationForm(employee.getLastName());
      inputAgeOnRegistrationForm(employee.getAge());
      inputEmailOnRegistrationForm(employee.getEmail());
      inputSalaryOnRegistrationForm(employee.getSalary());
      inputDepartmentOnRegistrationForm(employee.getDepartment());
      clickSubmitButtonOnRegistrationForm();
      webUI.delayInSeconds(3);
    }
  }


  public void editEmployee(String email, String newFirstName, String newLastName, String newEmail,
      String newAge, String newSalary, String newDepartment) {
    clickEditButtonOfEmail(email);
    if (isRegistrationFormVisible()) {
      inputLastNameOnRegistrationForm(newLastName);
      inputAgeOnRegistrationForm(newAge);
      inputEmailOnRegistrationForm(newEmail);
      inputSalaryOnRegistrationForm(newSalary);
      inputDepartmentOnRegistrationForm(newDepartment);
      clickSubmitButtonOnRegistrationForm();
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

  public boolean isRegistrationFormVisible() {
    return webUI.waitForElementVisible(DLG_REGISTRATION_FORM, 30);
  }

  public void inputFirstNameOnRegistrationForm(String firstName) {
    if(firstName.isEmpty()) {
      webUI.clickOn(REGISTRATION_FORM_TXT_FIRST_NAME);
      webUI.inputText(REGISTRATION_FORM_TXT_FIRST_NAME, Keys.chord(Keys.TAB));
    } else {
      webUI.clearText(REGISTRATION_FORM_TXT_FIRST_NAME, 20);
      webUI.inputText(REGISTRATION_FORM_TXT_FIRST_NAME, firstName, 10);
    }
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
