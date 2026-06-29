package vn.edu.vitacademy.pages.guru;

import io.qameta.allure.Step;
import java.util.List;
import org.openqa.selenium.Keys;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.BankTestCase;
import vn.edu.vitacademy.pages.BasePage;

/**
 * Shared base for every Guru99 Banking form page.
 *
 * <p>Holds the generic, data-driven form keywords that all the validation
 * scenarios reuse: filling a field, submitting, and asserting the inline
 * error label that Guru99 renders next to an invalid field. Concrete pages
 * only declare their locator keys (in {@code object_repository/*.json}) and
 * which field a given test row targets.
 */
public abstract class GuruBankBasePage extends BasePage {

  /** Repo key for the form Submit button - same convention across all forms. */
  protected static final String BTN_SUBMIT = "BTN_SUBMIT";
  /** Repo key for the form Reset button. */
  protected static final String BTN_RESET = "BTN_RESET";

  protected GuruBankBasePage(WebUI webUI) {
    super(webUI);
  }

  @Step("Clear field '{0}'")
  public void clearField(String fieldKey) {
    webUI.clearText(findTestObject(fieldKey), 10);
  }

  @Step("Input '{1}' into field '{0}'")
  public void inputField(String fieldKey, String value) {
    webUI.clearText(findTestObject(fieldKey), 10);
    webUI.inputText(findTestObject(fieldKey), value, 10);
  }

  @Step("Submit the form")
  public void clickSubmit() {
    webUI.clickOn(findTestObject(BTN_SUBMIT), 10);
  }

  @Step("Reset the form")
  public void clickReset() {
    webUI.clickOn(findTestObject(BTN_RESET), 10);
  }

  @Step("Get inline error text of '{0}'")
  public String getFieldErrorText(String errorKey) {
    return webUI.getText(findTestObject(errorKey), 10);
  }

  @Step("Verify inline error of '{0}' is visible")
  public boolean isFieldErrorVisible(String errorKey) {
    return webUI.verifyElementVisible(findTestObject(errorKey));
  }

  /** Result of validating one spreadsheet row against the page. */
  public enum ValidationResult {
    /** The expected inline error was raised - the negative test passed. */
    PASSED,
    /** No error was raised where one was expected - the negative test failed. */
    FAILED,
    /** The row is not an automatable field-validation case (e.g. "Verify Field Labels"). */
    SKIPPED
  }

  /**
   * Resolves which input on the concrete page a spreadsheet row targets.
   * Implementations inspect {@code testCase.getScenario()} / test-case text and
   * return {@code [fieldRepoKey, errorRepoKey]}, or {@code null} when the row is
   * not an automatable field-validation case.
   */
  protected abstract String[] resolveFieldTarget(BankTestCase testCase);

  /**
   * Runs the data-driven validation for one {@link BankTestCase} row: resolves
   * the target field, exercises it, and reports whether the expected inline
   * error was raised.
   */
  @Step("Verify validation for test case {0}")
  public ValidationResult verify(BankTestCase testCase) {
    String[] target = resolveFieldTarget(testCase);
    if (target == null) {
      LOGGER.warn("No automatable field for test case '{}' (scenario '{}') - skipped",
          testCase, testCase.getScenario());
      return ValidationResult.SKIPPED;
    }
    boolean rejected = verifyFieldValidation(target[0], target[1], testCase);
    return rejected ? ValidationResult.PASSED : ValidationResult.FAILED;
  }

  /**
   * Data-driven validation of a single field against one {@link BankTestCase}
   * row read from the Excel suite.
   *
   * <ul>
   *   <li>Empty-field rows (blank "Test Data"): clear the field, submit, then
   *       expect the inline error label to appear.</li>
   *   <li>Value rows: type each candidate value (Guru99 validates on key-up)
   *       and expect the inline error label to appear for every value.</li>
   * </ul>
   *
   * @param fieldKey repo key of the input being validated
   * @param errorKey repo key of the inline error label for that input
   * @param testCase the spreadsheet row driving this check
   * @return true when the expected error was raised for all candidate values
   */
  @Step("Verify field '{0}' validation for test case {2}")
  public boolean verifyFieldValidation(String fieldKey, String errorKey, BankTestCase testCase) {
    boolean allRejected = true;
    for (String value : resolveCandidateValues(testCase)) {
      triggerFieldValidation(fieldKey, value);
      boolean rejected = isFieldErrorVisible(errorKey);
      LOGGER.info("Field '{}' with value '{}' rejected? {}", fieldKey,
          value.isEmpty() ? "<empty>" : value, rejected);
      allRejected = allRejected && rejected;
    }
    return allRejected;
  }

  /**
   * Resolves the negative input value(s) for a field. Non-blank "Test Data"
   * cells are used verbatim. A blank cell is derived from the test-case text: a
   * "... space" scenario becomes a single space (Guru99 rejects a leading space),
   * any other blank case becomes the empty string (a genuine "cannot be empty"
   * check).
   */
  private List<String> resolveCandidateValues(BankTestCase testCase) {
    List<String> values = testCase.getTestDataValues();
    if (!values.isEmpty()) {
      return values;
    }
    String text = (safe(testCase.getTestCase()) + " " + safe(testCase.getSteps())).toLowerCase();
    return text.contains("space") ? List.of(" ") : List.of("");
  }

  /**
   * Drives Guru99's inline (onkeyup) validation for one field/value. Guru99 does
   * not validate a blank field on Submit - an empty form submit raises a global
   * "please fill all fields" JS alert instead - so we type into the field to fire
   * its onkeyup handler. An empty value is exercised by typing a character then
   * deleting it, leaving the field empty with onkeyup already fired.
   */
  private void triggerFieldValidation(String fieldKey, String value) {
    clearField(fieldKey);
    if (value.isEmpty()) {
      webUI.inputText(findTestObject(fieldKey), "a", 10);
      webUI.inputText(findTestObject(fieldKey), Keys.BACK_SPACE.toString(), 10);
    } else {
      webUI.inputText(findTestObject(fieldKey), value, 10);
    }
  }

  private static String safe(String value) {
    return value == null ? "" : value;
  }
}
