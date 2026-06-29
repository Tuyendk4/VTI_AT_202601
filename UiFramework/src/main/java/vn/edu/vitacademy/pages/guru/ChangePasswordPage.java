package vn.edu.vitacademy.pages.guru;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.BankTestCase;

/**
 * Guru99 "Change Password" form (changepassword.php).
 *
 * <p>Reference implementation for the Excel-driven validation pattern: it maps
 * each spreadsheet scenario ("Verify Old Password Field", "Verify New Password
 * Field", "Verify Confirm Password Field", ...) to the input it exercises and
 * the inline error label that must appear. The generic engine in
 * {@link GuruBankBasePage} does the rest.
 */
public class ChangePasswordPage extends GuruBankBasePage {

  public ChangePasswordPage(WebUI webUI) {
    super(webUI);
    setRepoName(ChangePasswordPage.class.getSimpleName());
  }

  @Override
  protected String[] resolveFieldTarget(BankTestCase testCase) {
    String scenario = testCase.getScenario() == null ? "" : testCase.getScenario().toLowerCase();
    if (scenario.contains("old password")) {
      return new String[] {"TXT_OLD_PASSWORD", "LBL_OLD_PASSWORD_ERROR"};
    }
    if (scenario.contains("new password")) {
      return new String[] {"TXT_NEW_PASSWORD", "LBL_NEW_PASSWORD_ERROR"};
    }
    if (scenario.contains("confirm")) {
      return new String[] {"TXT_CONFIRM_PASSWORD", "LBL_CONFIRM_PASSWORD_ERROR"};
    }
    // Non-field rows (e.g. "Verify Field Labels") are not automatable here.
    return null;
  }
}
