package vn.edu.vitacademy.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.model.Customer;
import vn.edu.vitacademy.pages.CustomerRegisteredPage;
import vn.edu.vitacademy.pages.ManagerPage;
import vn.edu.vitacademy.pages.NewCustomerPage;

public class NewCustomerTest extends BaseTest {

  private NewCustomerPage newCustomerPage;

  @Test(description = "NC001 - Login successfully")
  public void NC001_Login_successfully() {
    ManagerPage managerPage = loginPage.loginWith(registeredUser.getUserId(),
        registeredUser.getPassword());
    newCustomerPage = managerPage.isOnLeftMenu().moveToNewCustomer();
  }

  // NC002 navigates away to the confirmation page on success, so every other test must
  // re-navigate to a fresh Add New Customer page rather than reuse a possibly stale reference.
  @BeforeMethod(alwaysRun = true)
  public void navigateToFreshNewCustomerPage(Method method) {
    if (newCustomerPage != null && !"NC001_Login_successfully".equals(method.getName())) {
      newCustomerPage = newCustomerPage.isOnLeftMenu().moveToNewCustomer();
    }
  }

  @Test(description = "NC002 - Add new customer successfully", dependsOnMethods = "NC001_Login_successfully")
  public void NC002_Add_new_customer_successfully() {
    newCustomerPage.clickReset();
    // E-mail field has maxlength=30 on the real site; keep the unique local-part short
    // enough that "<local-part>@mailinator.com" (16 chars) never gets truncated mid-domain.
    String uniqueEmail = "qa" + (System.currentTimeMillis() % 100000000L) + "@mailinator.com";
    Customer customer = Customer.Builder.aCustomer()
        .withName("TestAutoQA")
        .withGender("m")
        .withDateOfBirth("2000-01-15")
        .withAddress("123 Test Street")
        .withCity("Hanoi")
        .withState("Hanoi")
        .withPin("100000")
        .withMobileNumber("0123456789")
        .withEmail(uniqueEmail)
        .withPassword("Password@123")
        .build();

    CustomerRegisteredPage customerRegisteredPage = newCustomerPage.addNewCustomer(customer);

    assertEquals(customerRegisteredPage.getSuccessMessage(), "Customer Registered Successfully!!!");
    assertFalse(customerRegisteredPage.getCustomerId().isEmpty());
  }

  @DataProvider(name = "invalidFieldData")
  public Object[][] invalidFieldData() {
    return new Object[][]{
        {"NAME", "", "Customer name must not be blank"},
        {"NAME", "12345", "Numbers are not allowed"},
        {"NAME", "Test@123", "Special characters are not allowed"},
        {"NAME", " Test", "First character can not have space"},

        {"DOB", "", "Date Field must not be blank"},

        {"ADDRESS", "", "Address Field must not be blank"},
        {"ADDRESS", " Test", "First character can not have space"},
        {"ADDRESS", "Test@123", "Special characters are not allowed"},

        {"CITY", "", "City Field must not be blank"},
        {"CITY", "12345", "Numbers are not allowed"},
        {"CITY", "Test@123", "Special characters are not allowed"},
        {"CITY", " Test", "First character can not have space"},

        {"STATE", "", "State must not be blank"},
        {"STATE", "12345", "Numbers are not allowed"},
        {"STATE", "Test@123", "Special characters are not allowed"},
        {"STATE", " Test", "First character can not have space"},

        {"PIN", "", "PIN Code must not be blank"},
        {"PIN", "abcde", "Characters are not allowed"},
        {"PIN", "12@45", "Special characters are not allowed"},
        {"PIN", " 12345", "First character can not have space"},
        {"PIN", "12345", "PIN Code must have 6 Digits"},

        {"MOBILE", "", "Mobile no must not be blank"},
        {"MOBILE", "abcde12345", "Characters are not allowed"},
        {"MOBILE", "01234@6789", "Special characters are not allowed"},
        {"MOBILE", " 123456789", "First character can not have space"},

        {"EMAIL", "", "Email-ID must not be blank"},
        {"EMAIL", " test@test.com", "First character can not have space"},
        {"EMAIL", "plainaddress", "Email-ID is not valid"},

        {"PASSWORD", "", "Password must not be blank"},
    };
  }

  @Test(description = "NC003 - Field validation shows expected error message",
      dependsOnMethods = "NC001_Login_successfully", dataProvider = "invalidFieldData")
  public void NC003_Field_validation_shows_expected_error(String field, String value,
      String expectedError) {
    newCustomerPage.clickReset();
    assertEquals(inputFieldAndGetErrorMessage(field, value), expectedError);
  }

  @Test(description = "NC004 - Reset button clears all fields",
      dependsOnMethods = "NC001_Login_successfully")
  public void NC004_Reset_button_clears_all_fields() {
    newCustomerPage.inputCustomerName("TestAutoQA")
        .inputDateOfBirth("2000-01-15")
        .inputAddress("123 Test Street")
        .inputCity("Hanoi")
        .inputState("Hanoi")
        .inputPin("100000")
        .inputMobileNumber("0123456789")
        .inputEmail("qaauto@mailinator.com")
        .inputPassword("Password@123")
        .clickReset();

    assertTrue(newCustomerPage.areAllFieldsEmpty());
  }

  private String inputFieldAndGetErrorMessage(String field, String value) {
    switch (field) {
      case "NAME":
        return newCustomerPage.inputCustomerName(value).getCustomerNameErrorMessage();
      case "DOB":
        return newCustomerPage.inputDateOfBirth(value).getDateOfBirthErrorMessage();
      case "ADDRESS":
        return newCustomerPage.inputAddress(value).getAddressErrorMessage();
      case "CITY":
        return newCustomerPage.inputCity(value).getCityErrorMessage();
      case "STATE":
        return newCustomerPage.inputState(value).getStateErrorMessage();
      case "PIN":
        return newCustomerPage.inputPin(value).getPinErrorMessage();
      case "MOBILE":
        return newCustomerPage.inputMobileNumber(value).getMobileNumberErrorMessage();
      case "EMAIL":
        return newCustomerPage.inputEmail(value).getEmailErrorMessage();
      case "PASSWORD":
        return newCustomerPage.inputPassword(value).getPasswordErrorMessage();
      default:
        throw new IllegalArgumentException("Unknown field: " + field);
    }
  }
}