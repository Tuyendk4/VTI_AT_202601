package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.DeleteCustomerPage;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "Delete Customer" form. Every row of
 * the "Delete  Customer" sheet in {@code TestCaseSuite_v2.xlsx} becomes one
 * test iteration via the {@code @DataProvider}.
 */
public class DeleteCustomerTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "Delete  Customer";
  private DeleteCustomerPage deleteCustomerPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    deleteCustomerPage = homePage.openDeleteCustomer();
  }

  @DataProvider(name = "deleteCustomerCases")
  public Object[][] deleteCustomerCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "deleteCustomerCases", groups = {"regression"},
      description = "Validate Delete Customer fields from TestCaseSuite_v2.xlsx")
  public void validateDeleteCustomerField(BankTestCase testCase) {
    ValidationResult result = deleteCustomerPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
