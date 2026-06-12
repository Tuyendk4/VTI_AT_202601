package vn.edu.vitacademy.tests;

import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
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
import vn.edu.vitacademy.pages.EmployeesPage;

public class EmployeesTest_POM extends BaseTest {

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
    employeesPage.createEmployee(employee);

    //Assertion
    assertTrue(employeesPage.shouldShowFirstNameInEmployeesTable(employee.getFirstName())); //is, has, should, ensureThat
    assertTrue(employeesPage.shouldShowLastNameInEmployeesTable(employee.getLastName()));
  }


  @Test(description = "EM002 - Edit email successfully", groups = {"smoke", "regression"})
  public void EM002_edit_email_successfully() {
//    webUI = new WebUI();
//    webUI.openBrowser(BROWSER, URL);
//    webUI.maximizeWindow();
    SoftAssert softAssert = new SoftAssert();
    employeesPage.editEmployee("kierra@example.com", "John", "Doe", "johndoe@mailinator.com",
        "34", "3000", "IT");
    softAssert.assertTrue(employeesPage.shouldShowFirstNameInEmployeesTable("Jozhn"), "Should show first name in Employees table");
    softAssert.assertTrue(employeesPage.shouldShowLastNameInEmployeesTable("Does"), "Should show last name in Employees table");
    softAssert.assertAll();
//    webUI.closeBrowser();
  }

  @Test(description = "EM003 - Delete email successfully", groups = {"smoke", "regression"})
  public void EM003_delete_email_successfully() {
    employeesPage.deleteEmployee("johndoe@mailinator.com");
//    webUI.delayInSeconds(5);
    assertTrue(employeesPage.shouldNotShowFirstNameInEmployeesTable("John"));
    assertTrue(employeesPage.shouldNotShowLastNameInEmployeesTable("Doe"));
    employeesPage.leftMenu().moveToTextBox();
//    webUI.delayInSeconds(5);
  }

}
