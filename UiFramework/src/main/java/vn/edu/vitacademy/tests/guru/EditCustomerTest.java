package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.EditCustomerPage;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "Edit Customer" form. Every row of the
 * "Edit Customer" sheet in {@code TestCaseSuite_v2.xlsx} becomes one test
 * iteration via the {@code @DataProvider}.
 */
public class EditCustomerTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "Edit Customer";
  private EditCustomerPage editCustomerPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    editCustomerPage = homePage.openEditCustomer();
  }

  @DataProvider(name = "editCustomerCases")
  public Object[][] editCustomerCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "editCustomerCases", groups = {"regression"},
      description = "Validate Edit Customer fields from TestCaseSuite_v2.xlsx")
  public void validateEditCustomerField(BankTestCase testCase) {
    ValidationResult result = editCustomerPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
