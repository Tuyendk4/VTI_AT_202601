package vn.edu.vitacademy.pages.guru;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.BankTestCase;

/**
 * Guru99 "Deposit" form (DepositInput.php).
 *
 * <p>Reference implementation for the Excel-driven validation pattern: it maps
 * each spreadsheet scenario ("Verify Account Number Field", "Verify Amount Field", ...) to
 * the input it exercises and the inline error label that must appear. The
 * generic engine in {@link GuruBankBasePage} does the rest.
 */
public class DepositPage extends GuruBankBasePage {

  public DepositPage(WebUI webUI) {
    super(webUI);
    setRepoName(DepositPage.class.getSimpleName());
  }

  @Override
  protected String[] resolveFieldTarget(BankTestCase testCase) {
    String scenario = testCase.getScenario() == null ? "" : testCase.getScenario().toLowerCase();
    if (scenario.contains("account")) {
      return new String[] {"TXT_ACCOUNT_NUMBER", "LBL_ACCOUNT_NUMBER_ERROR"};
    }
    if (scenario.contains("amount")) {
      return new String[] {"TXT_AMOUNT", "LBL_AMOUNT_ERROR"};
    }
    // "Verify Field Labels" and similar non-field rows are not automatable here.
    return null;
  }
}
