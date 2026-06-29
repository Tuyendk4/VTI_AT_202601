package vn.edu.vitacademy.pages.guru;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.BankTestCase;

/**
 * Guru99 "Fund Transfer" form (FundTransInput.php).
 *
 * <p>Reference implementation for the Excel-driven validation pattern: it maps
 * each spreadsheet scenario ("Verify Payers Account Field", "Verify Payees Account Field", ...) to
 * the input it exercises and the inline error label that must appear. The
 * generic engine in {@link GuruBankBasePage} does the rest.
 */
public class FundTransferPage extends GuruBankBasePage {

  public FundTransferPage(WebUI webUI) {
    super(webUI);
    setRepoName(FundTransferPage.class.getSimpleName());
  }

  @Override
  protected String[] resolveFieldTarget(BankTestCase testCase) {
    String scenario = testCase.getScenario() == null ? "" : testCase.getScenario().toLowerCase();
    if (scenario.contains("payers")) {
      return new String[] {"TXT_PAYERS_ACCOUNT", "LBL_PAYERS_ACCOUNT_ERROR"};
    }
    if (scenario.contains("payee")) {
      return new String[] {"TXT_PAYEES_ACCOUNT", "LBL_PAYEES_ACCOUNT_ERROR"};
    }
    if (scenario.contains("amount")) {
      return new String[] {"TXT_AMOUNT", "LBL_AMOUNT_ERROR"};
    }
    // "Verify Field Labels" and similar non-field rows are not automatable here.
    return null;
  }
}
