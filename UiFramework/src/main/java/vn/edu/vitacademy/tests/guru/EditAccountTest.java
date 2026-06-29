package vn.edu.vitacademy.tests.guru;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.common.helper.ExcelHelper;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.guru.GuruBankBasePage.ValidationResult;
import vn.edu.vitacademy.pages.guru.EditAccountPage;
import vn.edu.vitacademy.tests.GuruBankBaseTest;

/**
 * Excel-driven validation of the Guru99 "Edit Account" form. Every row of the
 * "Edit Account" sheet in {@code TestCaseSuite_v2.xlsx} becomes one test
 * iteration via the {@code @DataProvider}.
 */
public class EditAccountTest extends GuruBankBaseTest {

  private static final String SHEET_NAME = "Edit Account";
  private EditAccountPage editAccountPage;

  @BeforeClass(alwaysRun = true)
  public void openPage() {
    editAccountPage = homePage.openEditAccount();
  }

  @DataProvider(name = "editAccountCases")
  public Object[][] editAccountCases() {
    return ExcelHelper.readSheetAsDataProvider(SHEET_NAME);
  }

  @Test(dataProvider = "editAccountCases", groups = {"regression"},
      description = "Validate Edit Account fields from TestCaseSuite_v2.xlsx")
  public void validateEditAccountField(BankTestCase testCase) {
    ValidationResult result = editAccountPage.verify(testCase);
    if (result == ValidationResult.SKIPPED) {
      throw new SkipException("Not an automatable field-validation row: " + testCase);
    }
    assertEquals(result, ValidationResult.PASSED,
        "Expected validation error for [" + testCase + "] | Expected: " + testCase.getExpectedResult());
  }
}
