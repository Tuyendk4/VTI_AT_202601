package vn.edu.vitacademy.pages.guru;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.BankTestCase;

/**
 * Guru99 "Balance Enquiry" form (BalEnqInput.php).
 *
 * <p>Reference implementation for the Excel-driven validation pattern: it maps
 * each spreadsheet scenario ("Verify Account Number Field", ...) to
 * the input it exercises and the inline error label that must appear. The
 * generic engine in {@link GuruBankBasePage} does the rest.
 */
public class BalanceEnquiryPage extends GuruBankBasePage {

  public BalanceEnquiryPage(WebUI webUI) {
    super(webUI);
    setRepoName(BalanceEnquiryPage.class.getSimpleName());
  }

  @Override
  protected String[] resolveFieldTarget(BankTestCase testCase) {
    String scenario = testCase.getScenario() == null ? "" : testCase.getScenario().toLowerCase();
    if (scenario.contains("account")) {
      return new String[] {"TXT_ACCOUNT_NUMBER", "LBL_ACCOUNT_NUMBER_ERROR"};
    }
    // "Verify Field Labels" and similar non-field rows are not automatable here.
    return null;
  }
}
