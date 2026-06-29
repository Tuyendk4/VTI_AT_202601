package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.pages.guru.NewCustomerPage;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "New Customer" form. Every row of the
 * "New Customer" sheet in {@code TestCaseSuite_v2.xlsx} becomes one test
 * iteration via the {@code @DataProvider}.
 */
public class NewCustomerTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "New Customer";
  private NewCustomerPage newCustomerPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    newCustomerPage = homePage.openNewCustomer();
  }

  @DataProvider(name = "newCustomerCases")
  public Object[][] newCustomerCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "newCustomerCases", groups = {"regression"},
      description = "Validate New Customer fields from TestCaseSuite_v2.xlsx")
  public void validateNewCustomerField(BankTestCase testCase) {
    ValidationResult result = newCustomerPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
