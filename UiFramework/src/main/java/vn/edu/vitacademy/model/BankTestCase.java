package vn.edu.vitacademy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * One row of a scenario sheet in {@code TestCaseSuite_v2.xlsx} (Guru99 Banking).
 *
 * <p>Columns are mapped from the spreadsheet header: TestCase# / SR# (id),
 * Test Scenario, Test Cases, Test Steps, Test Data, Expected Result. The
 * "Test Data" cell often holds several candidate values separated by line
 * breaks - {@link #getTestDataValues()} splits them so a data-driven test can
 * try each one.
 */
public class BankTestCase {

  private final String id;
  private final String scenario;
  private final String testCase;
  private final String steps;
  private final String testData;
  private final String expectedResult;

  public BankTestCase(String id, String scenario, String testCase, String steps, String testData,
      String expectedResult) {
    this.id = id;
    this.scenario = scenario;
    this.testCase = testCase;
    this.steps = steps;
    this.testData = testData;
    this.expectedResult = expectedResult;
  }

  public String getId() {
    return id;
  }

  public String getScenario() {
    return scenario;
  }

  public String getTestCase() {
    return testCase;
  }

  public String getSteps() {
    return steps;
  }

  public String getTestData() {
    return testData;
  }

  public String getExpectedResult() {
    return expectedResult;
  }

  /**
   * Splits the multiline "Test Data" cell into individual candidate values.
   * Returns an empty list when the cell is blank (e.g. "cannot be empty" cases).
   */
  public List<String> getTestDataValues() {
    List<String> values = new ArrayList<>();
    if (testData == null || testData.isBlank()) {
      return values;
    }
    for (String value : testData.split("\\r?\\n")) {
      if (!value.isBlank()) {
        values.add(value.trim());
      }
    }
    return values;
  }

  /** True when this is a "field cannot be empty / blank space" negative case. */
  public boolean isEmptyFieldCase() {
    return getTestDataValues().isEmpty();
  }

  @Override
  public String toString() {
    return (id == null ? "" : id) + " - " + (testCase == null ? "" : testCase);
  }
}
