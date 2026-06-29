package vn.edu.vitacademy.pages.guru;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.BankTestCase;

/**
 * Guru99 "Customized Statement" form (customisedstatement.php).
 *
 * <p>Reference implementation for the Excel-driven validation pattern: it maps
 * each spreadsheet scenario ("Verify Account Number Field", "Verify Minimum
 * Transaction Value Field", ...) to the input it exercises and the inline error
 * label that must appear. The generic engine in {@link GuruBankBasePage} does
 * the rest.
 */
public class CustomizedStatementPage extends GuruBankBasePage {

  public CustomizedStatementPage(WebUI webUI) {
    super(webUI);
    setRepoName(CustomizedStatementPage.class.getSimpleName());
  }

  @Override
  protected String[] resolveFieldTarget(BankTestCase testCase) {
    String scenario = testCase.getScenario() == null ? "" : testCase.getScenario().toLowerCase();
    if (scenario.contains("account number")) {
      return new String[] {"TXT_ACCOUNT_NUMBER", "LBL_ACCOUNT_NUMBER_ERROR"};
    }
    if (scenario.contains("minimum transaction")) {
      return new String[] {"TXT_MIN_TRANSACTION_VALUE", "LBL_MIN_TRANSACTION_VALUE_ERROR"};
    }
    if (scenario.contains("number of")) {
      return new String[] {"TXT_NUM_TRANSACTION", "LBL_NUM_TRANSACTION_ERROR"};
    }
    // Non-field rows (e.g. "Verify Field Labels") are not automatable here.
    return null;
  }
}
