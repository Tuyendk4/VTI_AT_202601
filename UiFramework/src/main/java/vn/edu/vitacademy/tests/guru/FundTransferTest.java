package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.pages.guru.FundTransferPage;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "Fund Transfer" form. Every row of the
 * "FundTransfer" sheet in {@code TestCaseSuite_v2.xlsx} becomes one test
 * iteration via the {@code @DataProvider}.
 */
public class FundTransferTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "FundTransfer";
  private FundTransferPage fundTransferPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    fundTransferPage = homePage.openFundTransfer();
  }

  @DataProvider(name = "fundTransferCases")
  public Object[][] fundTransferCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "fundTransferCases", groups = {"regression"},
      description = "Validate Fund Transfer fields from TestCaseSuite_v2.xlsx")
  public void validateFundTransferField(BankTestCase testCase) {
    ValidationResult result = fundTransferPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
