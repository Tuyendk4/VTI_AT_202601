package vn.edu.vtiacademy.tests.web;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import vn.edu.vtiacademy.model.Employee;

public class EmployeesTest_POM_Excel02 extends BaseTest_Excel02 {

  //Arrange

  EmployeesTest_POM_Excel02() {
    setSheetName(EmployeesTest_POM_Excel02.class.getSimpleName());
  }

  @Test(description = "EM001 - Create email successfully")
  public void EM001_create_email_successfully() {

    List<HashMap<String, String>> data = getTestData("EM001");
    // Arrange

    for (HashMap<String, String> row : data) {
      Employee employee = new Employee(row.get("First Name"),
          row.get("Last Name"),
          row.get("Email"),
          row.get("Age"),
          row.get("Salary"),
          row.get("Department"));
      //Action - Step
      employeesPage.createEmployee(employee);

      //Assertion
      assertTrue(employeesPage.shouldShowFirstNameInEmployeesTable(employee.getFirstName()));
      assertTrue(employeesPage.shouldShowLastNameInEmployeesTable(employee.getLastName()));
    }
  }


  @Test(description = "EM002 - Edit email successfully")
  public void EM002_edit_email_successfully() {
    SoftAssert softAssert = new SoftAssert();
    List<HashMap<String, String>> data = getTestData("EM001");
    for (HashMap<String, String> row : data) {

      Employee employee = new Employee(row.get("First Name"),
          row.get("Last Name"),
          row.get("New Email"),
          row.get("Age"),
          row.get("Salary"),
          row.get("Department"));
      String oldEmail = row.get("Email");
      employeesPage.editEmployee(oldEmail, employee);
      softAssert.assertTrue(
          employeesPage.shouldShowFirstNameInEmployeesTable(employee.getFirstName()),
          "Should show first name in Employees table");
      softAssert.assertTrue(
          employeesPage.shouldShowLastNameInEmployeesTable(employee.getLastName()),
          "Should show last name in Employees table");
      softAssert.assertAll();
    }
  }

  @Test(description = "EM003 - Delete email successfully")
  public void EM003_delete_email_successfully() {
    List<HashMap<String, String>> data = getTestData("EM001");
    for (HashMap<String, String> row : data) {
      Employee employee = new Employee(row.get("First Name"),
          row.get("Last Name"),
          row.get("Email"),
          row.get("Age"),
          row.get("Salary"),
          row.get("Department"));
      employeesPage.deleteEmployee(employee.getEmail());
//    webUI.delayInSeconds(5);
      assertTrue(employeesPage.shouldNotShowFirstNameInEmployeesTable(employee.getFirstName()));
      assertTrue(employeesPage.shouldNotShowLastNameInEmployeesTable(employee.getLastName()));
    }
  }

}
