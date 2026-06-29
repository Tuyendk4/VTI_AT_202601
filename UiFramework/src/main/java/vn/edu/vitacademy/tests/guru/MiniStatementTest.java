package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.pages.guru.MiniStatementPage;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "Mini Statement" form. Every row of
 * the "Mini statement" sheet in {@code TestCaseSuite_v2.xlsx} becomes one test
 * iteration via the {@code @DataProvider}.
 */
public class MiniStatementTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "Mini statement";
  private MiniStatementPage miniStatementPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    miniStatementPage = homePage.openMiniStatement();
  }

  @DataProvider(name = "miniStatementCases")
  public Object[][] miniStatementCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "miniStatementCases", groups = {"regression"},
      description = "Validate Mini Statement fields from TestCaseSuite_v2.xlsx")
  public void validateMiniStatementField(BankTestCase testCase) {
    ValidationResult result = miniStatementPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
