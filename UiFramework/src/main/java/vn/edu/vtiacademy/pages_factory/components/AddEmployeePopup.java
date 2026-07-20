package vn.edu.vtiacademy.pages_factory.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import vn.edu.vtiacademy.common.keywords.WebUI;

public class AddEmployeePopup {

  @FindBy(xpath = "//div[@class='modal-content']")
  private WebElement dlgRegistrationForm;

  @FindBy(xpath = "//input[@id='firstName']")
  private WebElement txtFirstName;

  @FindBy(xpath = "//input[@id='lastName']")
  private WebElement txtLastName;

  @FindBy(xpath = "//input[@id='userEmail']")
  private WebElement txtEmail;

  @FindBy(xpath = "//input[@id='age']")
  private WebElement txtAge;

  @FindBy(xpath = "//input[@id='salary']")
  private WebElement txtSalary;

  @FindBy(xpath = "//input[@id='department']")
  private WebElement txtDepartment;

  @FindBy(xpath = "//button[@id='submit']")
  private WebElement btnSubmit;

  private WebUI webUI;

  public AddEmployeePopup(WebUI webUI) {
    this.webUI = webUI;
    PageFactory.initElements(new AjaxElementLocatorFactory(webUI.getWebDriver(), 30), this);
  }

  @Step("Show add employee popup")
  public boolean isVisible() {
    return webUI.waitForElementVisible(dlgRegistrationForm, 30);
  }

  @Step("Input first name '{0}' on registration form")
  public void inputFirstNameOnRegistrationForm(String firstName) {
    webUI.clearText(txtFirstName, 20);
    webUI.inputText(txtFirstName, firstName, 10);
    webUI.attachmentScreenshotWhichMarkElement(txtFirstName);
  }

  @Step("Input last name '{0}' on registration form")
  public void inputLastNameOnRegistrationForm(String lastName) {
    webUI.clearText(txtLastName);
    webUI.inputText(txtLastName, lastName);
    webUI.attachmentScreenshotWhichMarkElement(txtLastName);
  }

  @Step("Input age '{0}' on registration form")
  public void inputAgeOnRegistrationForm(String age) {
    webUI.clearText(txtAge);
    webUI.inputText(txtAge, age);
    webUI.attachmentScreenshotWhichMarkElement(txtAge);
  }

  @Step("Input email '{0}' on registration form")
  public void inputEmailOnRegistrationForm(String email) {
    webUI.clearText(txtEmail);
    webUI.inputText(txtEmail, email);
    webUI.attachmentScreenshotWhichMarkElement(txtEmail);
  }

  @Step("Input salary '{0}' on registration form")
  public void inputSalaryOnRegistrationForm(String salary) {
    webUI.clearText(txtSalary);
    webUI.inputText(txtSalary, salary);
    webUI.attachmentScreenshotWhichMarkElement(txtSalary);
  }

  @Step("Input department '{0}' on registration form")
  public void inputDepartmentOnRegistrationForm(String department) {
    webUI.clearText(txtDepartment);
    webUI.inputText(txtDepartment, department);
    webUI.attachmentScreenshotWhichMarkElement(txtDepartment);
  }

  @Step("Click submit button on registration form")
  public void clickSubmitButtonOnRegistrationForm() {
    webUI.attachmentScreenshotWhichMarkElement(btnSubmit);
    webUI.clickOn(btnSubmit);
    webUI.delayInSeconds(1);
    webUI.attachmentScreenshot();
  }

}
