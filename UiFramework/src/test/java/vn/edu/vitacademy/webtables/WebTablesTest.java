package vn.edu.vitacademy.webtables;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.keywords.WebUI;

public class WebTablesTest {

  private static final String BROWSER_NAME = "CHROME";
  private static final String URL = "https://demoqa.com/webtables";

  private static final String MODAL = "//div[@class='modal-content']";
  private static final String TXT_FIRST_NAME = "//input[@id='firstName']";
  private static final String TXT_LAST_NAME = "//input[@id='lastName']";
  private static final String TXT_EMAIL = "//input[@id='userEmail']";
  private static final String TXT_AGE = "//input[@id='age']";
  private static final String TXT_SALARY = "//input[@id='salary']";
  private static final String TXT_DEPARTMENT = "//input[@id='department']";
  private static final String BTN_SUBMIT = "//button[@id='submit']";

  private static final String ALL_DATA_ROWS = "//tr[.//span[@title='Edit']]";

  private WebUI webUI;

  @BeforeMethod
  public void setUp() {
    webUI = new WebUI();
    webUI.openBrowser(BROWSER_NAME, URL);
    webUI.maximizeWindow();
    webUI.waitForElementVisible(rowByEmail("cierra@example.com"), 15);
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    webUI.closeBrowser();
  }

  @Test(description = "Edit an existing user and verify the row reflects the new values")
  public void TC01_editExistingUser() {
    String targetEmail = "cierra@example.com";
    String newFirstName = "Daniel";
    String newAge = "35";
    String newSalary = "120000";

    webUI.clickOn(editButtonOfRow(targetEmail));
    webUI.waitForElementVisible(MODAL, 10);

    webUI.clearText(TXT_FIRST_NAME);
    webUI.inputText(TXT_FIRST_NAME, newFirstName);
    webUI.clearText(TXT_AGE);
    webUI.inputText(TXT_AGE, newAge);
    webUI.clearText(TXT_SALARY);
    webUI.inputText(TXT_SALARY, newSalary);

    webUI.clickOn(BTN_SUBMIT);
    webUI.waitForElementVisible(rowByEmail(targetEmail), 10);

    assertEquals(webUI.getText(cellOfRow(targetEmail, 1)), newFirstName, "First name should be updated");
    assertEquals(webUI.getText(cellOfRow(targetEmail, 3)), newAge, "Age should be updated");
    assertEquals(webUI.getText(cellOfRow(targetEmail, 5)), newSalary, "Salary should be updated");
  }

  @Test(description = "Delete an existing user and verify the row disappears and total count decreases")
  public void TC02_deleteExistingUser() {
    String targetEmail = "alden@example.com";
    int rowsBefore = webUI.countElements(ALL_DATA_ROWS);

    webUI.clickOn(deleteButtonOfRow(targetEmail));

    assertTrue(webUI.verifyElementNotPresent(rowByEmail(targetEmail)),
        "Deleted user row must no longer be in the table");
    assertEquals(webUI.countElements(ALL_DATA_ROWS), rowsBefore - 1,
        "Total data row count should decrease by 1 after deletion");
  }

  private static String rowByEmail(String email) {
    return String.format("//tr[td[normalize-space()='%s']]", email);
  }

  private static String cellOfRow(String email, int cellIndex) {
    return rowByEmail(email) + "/td[" + cellIndex + "]";
  }

  private static String editButtonOfRow(String email) {
    return rowByEmail(email) + "//span[@title='Edit']";
  }

  private static String deleteButtonOfRow(String email) {
    return rowByEmail(email) + "//span[@title='Delete']";
  }
}
