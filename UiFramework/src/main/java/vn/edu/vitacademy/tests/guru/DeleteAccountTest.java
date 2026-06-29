package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.pages.guru.DeleteAccountPage;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "Delete Account" form. Every row of the
 * "Delete Account" sheet in {@code TestCaseSuite_v2.xlsx} becomes one test
 * iteration via the {@code @DataProvider}.
 */
public class DeleteAccountTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "Delete Account";
  private DeleteAccountPage deleteAccountPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    deleteAccountPage = homePage.openDeleteAccount();
  }

  @DataProvider(name = "deleteAccountCases")
  public Object[][] deleteAccountCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "deleteAccountCases", groups = {"regression"},
      description = "Validate Delete Account fields from TestCaseSuite_v2.xlsx")
  public void validateDeleteAccountField(BankTestCase testCase) {
    ValidationResult result = deleteAccountPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
