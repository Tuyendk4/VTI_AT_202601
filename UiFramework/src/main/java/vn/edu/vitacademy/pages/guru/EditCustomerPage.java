package vn.edu.vitacademy.pages.guru;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.BankTestCase;

/**
 * Guru99 "Edit Customer" form (editcustomerpage.php).
 *
 * <p>Maps each spreadsheet scenario ("Verify Customer ID Field",
 * "Verify Address Field", ...) to the input it exercises and the inline error
 * label that must appear. The generic engine in {@link GuruBankBasePage} does
 * the rest.
 */
public class EditCustomerPage extends GuruBankBasePage {

  public EditCustomerPage(WebUI webUI) {
    super(webUI);
    setRepoName(EditCustomerPage.class.getSimpleName());
  }

  @Override
  protected String[] resolveFieldTarget(BankTestCase testCase) {
    String scenario = testCase.getScenario() == null ? "" : testCase.getScenario().toLowerCase();
    if (scenario.contains("customer id")) {
      return new String[] {"TXT_CUSTOMER_ID", "LBL_CUSTOMER_ID_ERROR"};
    }
    if (scenario.contains("address")) {
      return new String[] {"TXT_ADDRESS", "LBL_ADDRESS_ERROR"};
    }
    if (scenario.contains("city")) {
      return new String[] {"TXT_CITY", "LBL_CITY_ERROR"};
    }
    if (scenario.contains("state")) {
      return new String[] {"TXT_STATE", "LBL_STATE_ERROR"};
    }
    if (scenario.contains("pin")) {
      return new String[] {"TXT_PIN", "LBL_PIN_ERROR"};
    }
    if (scenario.contains("telephone") || scenario.contains("mobile")) {
      return new String[] {"TXT_TELEPHONE", "LBL_TELEPHONE_ERROR"};
    }
    if (scenario.contains("email")) {
      return new String[] {"TXT_EMAIL", "LBL_EMAIL_ERROR"};
    }
    // "Verify Field Labels" and similar non-field rows are not automatable here.
    return null;
  }
}
