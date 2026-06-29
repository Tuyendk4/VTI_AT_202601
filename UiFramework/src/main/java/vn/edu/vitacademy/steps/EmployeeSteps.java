package vn.edu.vitacademy.steps;

import static org.testng.Assert.assertTrue;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.EmployeesPage;

public class EmployeeSteps extends BaseSteps {

  @Given("I access to Employee page")
  public void accessToEmployeePage() {
//    webUI = new WebUI();
//    webUI.openBrowser("Chrome");
//    webUI.maximizeWindow();
//    webUI.navigateToUrl(EMPLOYEE_URL);
    employeesPage = new EmployeesPage(webUI);
  }

  @When("I click Add button")
  public void clickAddButton() {
    employeesPage.clickAddButton();
  }

  @When("I input {string} into First name text box")
  public void inputIntoFirstNameTextBox(String firstName) {
    employeesPage.onAddEmployeePopup().inputFirstName(firstName);
  }

  @When("I input {string} into Last name text box")
  public void inputIntoLastNameTextBox(String lastName) {
    employeesPage.onAddEmployeePopup().inputLastName(lastName);
  }

  @And("I input {string} into email text box")
  public void inputIntoEmailTextBox(String email) {
    employeesPage.onAddEmployeePopup().inputEmail(email);
  }

  @And("I input {string} into age text box")
  public void inputIntoAgeTextBox(String age) {
    employeesPage.onAddEmployeePopup().inputAge(age);
  }

  @And("I input {string}$ into salary text box")
  public void input$IntoSalaryTextBox(String salary) {
    employeesPage.onAddEmployeePopup().inputSalary(salary);
  }

  @And("I input {string} into department text box")
  public void inputIntoDepartmentTextBox(String department) {
    employeesPage.onAddEmployeePopup().inputDepartment(department);
  }

  @And("I click Submit button")
  public void clickSubmitButton() {
    employeesPage.onAddEmployeePopup().clickSubmitButton();
  }

  @Then("I should see {string} at First name column in Employee table")
  public void shouldSeeAtFirstNameColumnInEmployeeTable(String firstName) {
    assertTrue(employeesPage.shouldShowFirstNameInEmployeesTable(firstName));
  }

  @And("I should see {string} at Last name column in Employee table")
  public void shouldSeeAtLastNameColumnInEmployeeTable(String lastName) {
    assertTrue(employeesPage.shouldShowLastNameInEmployeesTable(lastName));
  }

  @When("I create a new employee with first name {string}, last name {string}, email {string}, age {string}, salary {string}, department {string}")
  public void createANewEmployeeWithFirstNameLastNameEmailAgeSalaryDepartment(String firstName,
      String lastName, String email, String age, String salary, String department) {
    employeesPage.clickAddButton();
    employeesPage.onAddEmployeePopup().inputFirstName(firstName);
    employeesPage.onAddEmployeePopup().inputLastName(lastName);
    employeesPage.onAddEmployeePopup().inputEmail(email);
    employeesPage.onAddEmployeePopup().inputAge(age);
    employeesPage.onAddEmployeePopup().inputSalary(salary);
    employeesPage.onAddEmployeePopup().inputDepartment(department);
    employeesPage.onAddEmployeePopup().clickSubmitButton();
  }

  @When("I create a new employee with information as below")
  public void createANewEmployeeWithInformationAsBelow(DataTable dataTable) {
    List<List<String>> data = dataTable.asLists(String.class);
    employeesPage.clickAddButton();
    employeesPage.onAddEmployeePopup().inputFirstName(data.getFirst().getFirst());
    employeesPage.onAddEmployeePopup().inputLastName(data.getFirst().get(1));
    employeesPage.onAddEmployeePopup().inputEmail(data.getFirst().get(2));
    employeesPage.onAddEmployeePopup().inputAge(data.getFirst().get(3));
    employeesPage.onAddEmployeePopup().inputSalary(data.getFirst().get(4));
    employeesPage.onAddEmployeePopup().inputDepartment(data.getFirst().get(5));
    employeesPage.onAddEmployeePopup().clickSubmitButton();
  }

  @When("I create a new employee with full information as below")
  public void createANewEmployeeWithFullInformationAsBelow(DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
    employeesPage.clickAddButton();
    employeesPage.onAddEmployeePopup().inputFirstName(data.getFirst().get("first_name"));
    employeesPage.onAddEmployeePopup().inputLastName(data.getFirst().get("last_name"));
    employeesPage.onAddEmployeePopup().inputEmail(data.getFirst().get("email"));
    employeesPage.onAddEmployeePopup().inputAge(data.getFirst().get("age"));
    employeesPage.onAddEmployeePopup().inputSalary(data.getFirst().get("salary"));
    employeesPage.onAddEmployeePopup().inputDepartment(data.getFirst().get("department"));
    employeesPage.onAddEmployeePopup().clickSubmitButton();
  }
}
