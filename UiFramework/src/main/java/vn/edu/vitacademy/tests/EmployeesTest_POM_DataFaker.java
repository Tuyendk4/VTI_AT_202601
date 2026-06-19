package vn.edu.vitacademy.tests;

import static org.testng.Assert.assertTrue;

import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import vn.edu.vitacademy.model.Employee;
import vn.edu.vitacademy.pages.TextBoxPage;

public class EmployeesTest_POM_DataFaker extends BaseTest {

  public EmployeesTest_POM_DataFaker() {
    super();
    setDataSource(EmployeesTest_POM_DataFaker.class.getSimpleName());
  }

  //Arrange
  @DataProvider(name = "EM001")
  private Object[][] dataProvider() {
    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = faker.internet().emailAddress();
    String age = faker.number().digits(2);
    String salary = faker.number().digits(4);
    String department = faker.job().position();

    Employee employee02 = Employee.Builder.aEmployee()
        .withFirstName(faker.name().firstName())
        .withLastName(faker.name().lastName())
        .withEmail(faker.internet().emailAddress())
        .withAge(String.valueOf(faker.number().numberBetween(18, 60)))
        .withSalary(faker.number().digits(4))
        .withDepartment(faker.job().position())
        .build();

    Employee employee03 = Employee.Builder.aEmployee()
        .withFirstName(faker.name().firstName())
        .withLastName(faker.name().lastName())
        .withEmail(faker.internet().emailAddress())
        .build();
    return new Object[][] {
        { new Employee(firstName, lastName, email, age, salary, department)},
        { employee02}
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
    Employee employee = Employee.Builder.aEmployee()
        .withFirstName(findTestData("EC002.firstName"))
        .withLastName(findTestData("EC002.lastName"))
        .withEmail(findTestData("EC002.email"))
        .withAge(findTestData("EC002.age"))
        .withSalary(findTestData("EC002.salary"))
        .withDepartment(findTestData("EC002.department"))
        .build();
    SoftAssert softAssert = new SoftAssert();
    employeesPage.editEmployee("kierra@example.com", employee);
    softAssert.assertTrue(employeesPage.shouldShowFirstNameInEmployeesTable(findTestData("EC002.firstName")), "Should show first name in Employees table");
    softAssert.assertTrue(employeesPage.shouldShowLastNameInEmployeesTable(findTestData("EC002.lastName")), "Should show last name in Employees table");
    softAssert.assertAll();
//    webUI.closeBrowser();
  }

  @Test(description = "EM003 - Delete email successfully", groups = {"smoke", "regression"})
  public void EM003_delete_email_successfully() {
    employeesPage.deleteEmployee("johndoe@mailinator.com");
//    webUI.delayInSeconds(5);
    assertTrue(employeesPage.shouldNotShowFirstNameInEmployeesTable("John"));
    assertTrue(employeesPage.shouldNotShowLastNameInEmployeesTable("Doe"));
    TextBoxPage textBoxPage = employeesPage.leftMenu().moveToTextBox();
    textBoxPage.inputFullName("John Doe");
  }

}
