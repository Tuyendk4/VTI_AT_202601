package vn.edu.vtiacademy.tests.web;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import vn.edu.vtiacademy.model.Employee;

public class EmployeesTest_POM_Excel extends BaseTest_Excel {

  //Arrange

  EmployeesTest_POM_Excel() {
    setSheetName(EmployeesTest_POM_Excel.class.getSimpleName());
  }

  @Test(description = "EM001 - Create email successfully")
  public void EM001_create_email_successfully() {
    // Arrange
    Employee employee = new Employee(findTestData("EM001", "First Name"),
        findTestData("EM001", "Last Name"),
        findTestData("EM001", "Email"),
        findTestData("EM001", "Age"),
        findTestData("EM001", "Salary"),
        findTestData("EM001", "Department"));
    //Action - Step
    employeesPage.createEmployee(employee);

    //Assertion
    assertTrue(employeesPage.shouldShowFirstNameInEmployeesTable(employee.getFirstName()));
    assertTrue(employeesPage.shouldShowLastNameInEmployeesTable(employee.getLastName()));
  }


  @Test(description = "EM002 - Edit email successfully")
  public void EM002_edit_email_successfully() {
    SoftAssert softAssert = new SoftAssert();
    Employee employee = new Employee(findTestData("EM002", "First Name"),
        findTestData("EM002", "Last Name"),
        findTestData("EM002", "New Email"),
        findTestData("EM002", "Age"),
        findTestData("EM002", "Salary"),
        findTestData("EM002", "Department"));
    String oldEmail = findTestData("EM002", "Email");
    employeesPage.editEmployee(oldEmail, employee);
    softAssert.assertTrue(employeesPage.shouldShowFirstNameInEmployeesTable(employee.getFirstName()), "Should show first name in Employees table");
    softAssert.assertTrue(employeesPage.shouldShowLastNameInEmployeesTable(employee.getLastName()), "Should show last name in Employees table");
    softAssert.assertAll();
  }

  @Test(description = "EM003 - Delete email successfully")
  public void EM003_delete_email_successfully() {
    Employee employee = new Employee(findTestData("EM003", "First Name"),
        findTestData("EM003", "Last Name"),
        findTestData("EM003", "Email"),
        findTestData("EM003", "Age"),
        findTestData("EM003", "Salary"),
        findTestData("EM003", "Department"));
    employeesPage.deleteEmployee(employee.getEmail());
//    webUI.delayInSeconds(5);
    assertTrue(employeesPage.shouldNotShowFirstNameInEmployeesTable(employee.getFirstName()));
    assertTrue(employeesPage.shouldNotShowLastNameInEmployeesTable(employee.getLastName()));
  }

}
