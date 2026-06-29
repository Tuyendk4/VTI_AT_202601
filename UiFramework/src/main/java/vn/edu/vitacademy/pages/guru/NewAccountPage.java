package vn.edu.vitacademy.pages.guru;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.BankTestCase;

/**
 * Guru99 "New Account" form (addaccountpage.php).
 *
 * <p>Reference implementation for the Excel-driven validation pattern: it maps
 * each spreadsheet scenario ("Verify Customer ID Field", "Verify Deposit Field", ...) to
 * the input it exercises and the inline error label that must appear. The
 * generic engine in {@link GuruBankBasePage} does the rest.
 */
public class NewAccountPage extends GuruBankBasePage {

  public NewAccountPage(WebUI webUI) {
    super(webUI);
    setRepoName(NewAccountPage.class.getSimpleName());
  }

  @Override
  protected String[] resolveFieldTarget(BankTestCase testCase) {
    String scenario = testCase.getScenario() == null ? "" : testCase.getScenario().toLowerCase();
    if (scenario.contains("customer id")) {
      return new String[] {"TXT_CUSTOMER_ID", "LBL_CUSTOMER_ID_ERROR"};
    }
    if (scenario.contains("deposit")) {
      return new String[] {"TXT_INITIAL_DEPOSIT", "LBL_INITIAL_DEPOSIT_ERROR"};
    }
    // "Verify Field Labels" and similar non-field rows are not automatable here.
    return null;
  }
}
