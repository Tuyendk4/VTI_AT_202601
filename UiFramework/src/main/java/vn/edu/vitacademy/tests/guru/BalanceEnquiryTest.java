package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.pages.guru.BalanceEnquiryPage;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "Balance Enquiry" form. Every row of the
 * "BalanceEnquiry" sheet in {@code TestCaseSuite_v2.xlsx} becomes one test
 * iteration via the {@code @DataProvider}.
 */
public class BalanceEnquiryTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "BalanceEnquiry";
  private BalanceEnquiryPage balanceEnquiryPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    balanceEnquiryPage = homePage.openBalanceEnquiry();
  }

  @DataProvider(name = "balanceEnquiryCases")
  public Object[][] balanceEnquiryCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "balanceEnquiryCases", groups = {"regression"},
      description = "Validate Balance Enquiry fields from TestCaseSuite_v2.xlsx")
  public void validateBalanceEnquiryField(BankTestCase testCase) {
    ValidationResult result = balanceEnquiryPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
