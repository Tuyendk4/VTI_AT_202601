package vn.edu.vitacademy.pages.guru;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.BankTestCase;

/**
 * Guru99 "Delete Account" form (deleteaccountpage.php).
 *
 * <p>Reference implementation for the Excel-driven validation pattern: it maps
 * each spreadsheet scenario ("Verify Account Number Field", ...) to
 * the input it exercises and the inline error label that must appear. The
 * generic engine in {@link GuruBankBasePage} does the rest.
 */
public class DeleteAccountPage extends GuruBankBasePage {

  public DeleteAccountPage(WebUI webUI) {
    super(webUI);
    setRepoName(DeleteAccountPage.class.getSimpleName());
  }

  @Override
  protected String[] resolveFieldTarget(BankTestCase testCase) {
    String scenario = testCase.getScenario() == null ? "" : testCase.getScenario().toLowerCase();
    if (scenario.contains("account")) {
      return new String[] {"TXT_ACCOUNT_NUMBER", "LBL_ACCOUNT_NUMBER_ERROR"};
    }
    // Non-field rows are not automatable here.
    return null;
  }
}
