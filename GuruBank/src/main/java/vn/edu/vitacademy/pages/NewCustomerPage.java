package vn.edu.vitacademy.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.Customer;

public class NewCustomerPage extends BasePage {

  public NewCustomerPage(WebUI webUI) {
    super(webUI);
    setRepoName(NewCustomerPage.class.getSimpleName());
  }

  @Step("Input customer name: {0}")
  public NewCustomerPage inputCustomerName(String name) {
    webUI.clearText(findTestObject("TXT_CUSTOMER_NAME"));
    webUI.inputText(findTestObject("TXT_CUSTOMER_NAME"), name + Keys.TAB);
    webUI.takeScreenshotAndMarkElement(findTestObject("TXT_CUSTOMER_NAME"));
    return this;
  }

  @Step("Select gender: {0}")
  public NewCustomerPage selectGender(String gender) {
    String genderObject = "f".equalsIgnoreCase(gender) ? "RDO_GENDER_FEMALE" : "RDO_GENDER_MALE";
    webUI.clickOn(findTestObject(genderObject));
    webUI.takeScreenshotAndMarkElement(findTestObject(genderObject));
    return this;
  }

  @Step("Input date of birth: {0}")
  public NewCustomerPage inputDateOfBirth(String dateOfBirth) {
    if (dateOfBirth == null || dateOfBirth.isEmpty()) {
      webUI.clearText(findTestObject("TXT_DATE_OF_BIRTH"));
      webUI.inputText(findTestObject("TXT_DATE_OF_BIRTH"), Keys.TAB.toString());
    } else {
      WebElement dobField = webUI.findWebElement(findTestObject("TXT_DATE_OF_BIRTH"));
      JavascriptExecutor jsExecutor = (JavascriptExecutor) webUI.getWebDriver();
      jsExecutor.executeScript("arguments[0].value=arguments[1];", dobField, dateOfBirth);
      webUI.inputText(dobField, Keys.TAB.toString());
    }
    webUI.takeScreenshotAndMarkElement(findTestObject("TXT_DATE_OF_BIRTH"));
    return this;
  }

  @Step("Input address: {0}")
  public NewCustomerPage inputAddress(String address) {
    webUI.clearText(findTestObject("TXA_ADDRESS"));
    webUI.inputText(findTestObject("TXA_ADDRESS"), address + Keys.TAB);
    webUI.takeScreenshotAndMarkElement(findTestObject("TXA_ADDRESS"));
    return this;
  }

  @Step("Input city: {0}")
  public NewCustomerPage inputCity(String city) {
    webUI.clearText(findTestObject("TXT_CITY"));
    webUI.inputText(findTestObject("TXT_CITY"), city + Keys.TAB);
    webUI.takeScreenshotAndMarkElement(findTestObject("TXT_CITY"));
    return this;
  }

  @Step("Input state: {0}")
  public NewCustomerPage inputState(String state) {
    webUI.clearText(findTestObject("TXT_STATE"));
    webUI.inputText(findTestObject("TXT_STATE"), state + Keys.TAB);
    webUI.takeScreenshotAndMarkElement(findTestObject("TXT_STATE"));
    return this;
  }

  @Step("Input pin: {0}")
  public NewCustomerPage inputPin(String pin) {
    webUI.clearText(findTestObject("TXT_PIN"));
    webUI.inputText(findTestObject("TXT_PIN"), pin + Keys.TAB);
    webUI.takeScreenshotAndMarkElement(findTestObject("TXT_PIN"));
    return this;
  }

  @Step("Input mobile number: {0}")
  public NewCustomerPage inputMobileNumber(String mobileNumber) {
    webUI.clearText(findTestObject("TXT_MOBILE_NUMBER"));
    webUI.inputText(findTestObject("TXT_MOBILE_NUMBER"), mobileNumber + Keys.TAB);
    webUI.takeScreenshotAndMarkElement(findTestObject("TXT_MOBILE_NUMBER"));
    return this;
  }

  @Step("Input email: {0}")
  public NewCustomerPage inputEmail(String email) {
    webUI.clearText(findTestObject("TXT_EMAIL"));
    webUI.inputText(findTestObject("TXT_EMAIL"), email + Keys.TAB);
    webUI.takeScreenshotAndMarkElement(findTestObject("TXT_EMAIL"));
    return this;
  }

  @Step("Input password: {0}")
  public NewCustomerPage inputPassword(String password) {
    webUI.clearText(findTestObject("TXT_PASSWORD"));
    webUI.inputText(findTestObject("TXT_PASSWORD"), password + Keys.TAB);
    webUI.takeScreenshotAndMarkElement(findTestObject("TXT_PASSWORD"));
    return this;
  }

  @Step("Get customer name error message")
  public String getCustomerNameErrorMessage() {
    return webUI.getText(findTestObject("LBL_CUSTOMER_NAME_ERROR"));
  }

  @Step("Get date of birth error message")
  public String getDateOfBirthErrorMessage() {
    return webUI.getText(findTestObject("LBL_DATE_OF_BIRTH_ERROR"));
  }

  @Step("Get address error message")
  public String getAddressErrorMessage() {
    return webUI.getText(findTestObject("LBL_ADDRESS_ERROR"));
  }

  @Step("Get city error message")
  public String getCityErrorMessage() {
    return webUI.getText(findTestObject("LBL_CITY_ERROR"));
  }

  @Step("Get state error message")
  public String getStateErrorMessage() {
    return webUI.getText(findTestObject("LBL_STATE_ERROR"));
  }

  @Step("Get pin error message")
  public String getPinErrorMessage() {
    return webUI.getText(findTestObject("LBL_PIN_ERROR"));
  }

  @Step("Get mobile number error message")
  public String getMobileNumberErrorMessage() {
    return webUI.getText(findTestObject("LBL_MOBILE_NUMBER_ERROR"));
  }

  @Step("Get email error message")
  public String getEmailErrorMessage() {
    return webUI.getText(findTestObject("LBL_EMAIL_ERROR"));
  }

  @Step("Get password error message")
  public String getPasswordErrorMessage() {
    return webUI.getText(findTestObject("LBL_PASSWORD_ERROR"));
  }

  @Step("Verify all fields are empty")
  public boolean areAllFieldsEmpty() {
    return webUI.getAttributeValue(findTestObject("TXT_CUSTOMER_NAME"), "value").isEmpty()
        && webUI.getAttributeValue(findTestObject("TXT_DATE_OF_BIRTH"), "value").isEmpty()
        && webUI.getAttributeValue(findTestObject("TXA_ADDRESS"), "value").isEmpty()
        && webUI.getAttributeValue(findTestObject("TXT_CITY"), "value").isEmpty()
        && webUI.getAttributeValue(findTestObject("TXT_STATE"), "value").isEmpty()
        && webUI.getAttributeValue(findTestObject("TXT_PIN"), "value").isEmpty()
        && webUI.getAttributeValue(findTestObject("TXT_MOBILE_NUMBER"), "value").isEmpty()
        && webUI.getAttributeValue(findTestObject("TXT_EMAIL"), "value").isEmpty()
        && webUI.getAttributeValue(findTestObject("TXT_PASSWORD"), "value").isEmpty();
  }

  @Step("Click Submit button")
  public CustomerRegisteredPage clickSubmit() {
    webUI.takeScreenshotAndMarkElement(findTestObject("BTN_SUBMIT"));
    webUI.click(findTestObject("BTN_SUBMIT"));
    webUI.delayInSeconds(2);
    webUI.takeScreenshot();
    return new CustomerRegisteredPage(webUI);
  }

  @Step("Click Reset button")
  public NewCustomerPage clickReset() {
    webUI.takeScreenshotAndMarkElement(findTestObject("BTN_RESET"));
    webUI.click(findTestObject("BTN_RESET"));
    return this;
  }

  @Step("Add new customer: {0}")
  public CustomerRegisteredPage addNewCustomer(Customer customer) {
    inputCustomerName(customer.getName());
    selectGender(customer.getGender());
    inputDateOfBirth(customer.getDateOfBirth());
    inputAddress(customer.getAddress());
    inputCity(customer.getCity());
    inputState(customer.getState());
    inputPin(customer.getPin());
    inputMobileNumber(customer.getMobileNumber());
    inputEmail(customer.getEmail());
    inputPassword(customer.getPassword());
    return clickSubmit();
  }
}
