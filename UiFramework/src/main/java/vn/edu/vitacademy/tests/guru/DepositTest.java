package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.pages.guru.DepositPage;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "Deposit" form. Every row of the
 * "Deposit" sheet in {@code TestCaseSuite_v2.xlsx} becomes one test
 * iteration via the {@code @DataProvider}.
 */
public class DepositTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "Deposit";
  private DepositPage depositPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    depositPage = homePage.openDeposit();
  }

  @DataProvider(name = "depositCases")
  public Object[][] depositCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "depositCases", groups = {"regression"},
      description = "Validate Deposit fields from TestCaseSuite_v2.xlsx")
  public void validateDepositField(BankTestCase testCase) {
    ValidationResult result = depositPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
