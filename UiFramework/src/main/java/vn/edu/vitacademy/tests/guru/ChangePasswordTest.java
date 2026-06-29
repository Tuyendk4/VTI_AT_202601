package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.pages.guru.ChangePasswordPage;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "Change Password" form. Every row of
 * the "Change  password" sheet in {@code TestCaseSuite_v2.xlsx} becomes one
 * test iteration via the {@code @DataProvider}.
 */
public class ChangePasswordTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "Change  password";
  private ChangePasswordPage changePasswordPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    changePasswordPage = homePage.openChangePassword();
  }

  @DataProvider(name = "changePasswordCases")
  public Object[][] changePasswordCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "changePasswordCases", groups = {"regression"},
      description = "Validate Change Password fields from TestCaseSuite_v2.xlsx")
  public void validateChangePasswordField(BankTestCase testCase) {
    ValidationResult result = changePasswordPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
