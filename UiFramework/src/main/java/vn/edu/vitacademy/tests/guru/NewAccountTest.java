package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.pages.guru.NewAccountPage;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "New Account" form. Every row of the
 * "New Account" sheet in {@code TestCaseSuite_v2.xlsx} becomes one test
 * iteration via the {@code @DataProvider}.
 */
public class NewAccountTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "New Account";
  private NewAccountPage newAccountPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    newAccountPage = homePage.openNewAccount();
  }

  @DataProvider(name = "newAccountCases")
  public Object[][] newAccountCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "newAccountCases", groups = {"regression"},
      description = "Validate New Account fields from TestCaseSuite_v2.xlsx")
  public void validateNewAccountField(BankTestCase testCase) {
    ValidationResult result = newAccountPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
