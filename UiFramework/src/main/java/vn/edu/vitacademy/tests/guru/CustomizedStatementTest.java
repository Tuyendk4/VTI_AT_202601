package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.pages.guru.CustomizedStatementPage;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "Customized Statement" form. Every row
 * of the "Customized statement" sheet in {@code TestCaseSuite_v2.xlsx} becomes
 * one test iteration via the {@code @DataProvider}.
 */
public class CustomizedStatementTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "Customized statement";
  private CustomizedStatementPage customizedStatementPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    customizedStatementPage = homePage.openCustomizedStatement();
  }

  @DataProvider(name = "customizedStatementCases")
  public Object[][] customizedStatementCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "customizedStatementCases", groups = {"regression"},
      description = "Validate Customized Statement fields from TestCaseSuite_v2.xlsx")
  public void validateCustomizedStatementField(BankTestCase testCase) {
    ValidationResult result = customizedStatementPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
