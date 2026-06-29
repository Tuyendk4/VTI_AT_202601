package vn.edu.vitacademy.pages.guru;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.BankTestCase;

/**
 * Guru99 "Delete Customer" form (deletecustomerpage.php).
 *
 * <p>Maps each spreadsheet scenario ("Verify Customer ID Field", ...) to the
 * input it exercises and the inline error label that must appear. The generic
 * engine in {@link GuruBankBasePage} does the rest.
 */
public class DeleteCustomerPage extends GuruBankBasePage {

  public DeleteCustomerPage(WebUI webUI) {
    super(webUI);
    setRepoName(DeleteCustomerPage.class.getSimpleName());
  }

  @Override
  protected String[] resolveFieldTarget(BankTestCase testCase) {
    String scenario = testCase.getScenario() == null ? "" : testCase.getScenario().toLowerCase();
    if (scenario.contains("customer")) {
      return new String[] {"TXT_CUSTOMER_ID", "LBL_CUSTOMER_ID_ERROR"};
    }
    // Non-field rows are not automatable here.
    return null;
  }
}
