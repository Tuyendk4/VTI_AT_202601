package vn.edu.vitacademy.common.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vitacademy.model.BankTestCase;

/**
 * Reads scenario sheets from {@code TestCaseSuite_v2.xlsx} into
 * {@link BankTestCase} rows for TestNG {@code @DataProvider} consumption.
 *
 * <p>Each sheet shares the same column layout: an id column (TestCase# / SR#),
 * Test Scenario, Test Cases, Test Steps, Test Data and Expected Result. The
 * "Test Scenario" cell is only filled on the first row of a group, so this
 * helper forward-fills it down the group.
 */
public final class ExcelHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExcelHelper.class);
  private static final DataFormatter FORMATTER = new DataFormatter();

  /** Default location of the Guru99 banking test-case workbook on the classpath/disk. */
  public static final String DEFAULT_WORKBOOK =
      System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
          + File.separator + "resources" + File.separator + "data" + File.separator
          + "TestCaseSuite_v2.xlsx";

  private ExcelHelper() {
  }

  /**
   * Reads a sheet from the default workbook into a TestNG data-provider matrix
   * where each element is a single {@link BankTestCase}.
   */
  public static Object[][] readSheetAsDataProvider(String sheetName) {
    return readSheetAsDataProvider(DEFAULT_WORKBOOK, sheetName);
  }

  public static Object[][] readSheetAsDataProvider(String workbookPath, String sheetName) {
    List<BankTestCase> testCases = readSheet(workbookPath, sheetName);
    Object[][] data = new Object[testCases.size()][1];
    for (int i = 0; i < testCases.size(); i++) {
      data[i][0] = testCases.get(i);
    }
    return data;
  }

  /** Reads every meaningful row of a sheet into {@link BankTestCase} objects. */
  public static List<BankTestCase> readSheet(String workbookPath, String sheetName) {
    List<BankTestCase> testCases = new ArrayList<>();
    LOGGER.info("Reading sheet '{}' from workbook '{}'", sheetName, workbookPath);
    try (InputStream is = new FileInputStream(workbookPath);
        Workbook workbook = new XSSFWorkbook(is)) {
      Sheet sheet = workbook.getSheet(sheetName);
      if (sheet == null) {
        LOGGER.error("Sheet '{}' not found in workbook '{}'", sheetName, workbookPath);
        return testCases;
      }
      int headerRowIndex = findHeaderRowIndex(sheet);
      if (headerRowIndex < 0) {
        LOGGER.error("Could not locate a header row in sheet '{}'", sheetName);
        return testCases;
      }
      Map<String, Integer> columns = mapColumns(sheet.getRow(headerRowIndex));
      String lastScenario = "";
      for (int r = headerRowIndex + 1; r <= sheet.getLastRowNum(); r++) {
        Row row = sheet.getRow(r);
        if (row == null) {
          continue;
        }
        String id = readCell(row, columns.getOrDefault("id", 0));
        String scenario = readCell(row, columns.getOrDefault("scenario", 1));
        String testCase = readCell(row, columns.getOrDefault("testcase", 2));
        String steps = readCell(row, columns.getOrDefault("steps", 3));
        String testData = readCell(row, columns.getOrDefault("testdata", 4));
        String expected = readCell(row, columns.getOrDefault("expected", 5));

        if (!scenario.isBlank()) {
          lastScenario = scenario;
        }
        // Skip fully empty / placeholder rows.
        if (id.isBlank() && testCase.isBlank() && steps.isBlank() && testData.isBlank()) {
          continue;
        }
        testCases.add(new BankTestCase(id, lastScenario, testCase, steps, testData, expected));
      }
      LOGGER.info("Read {} test case rows from sheet '{}'", testCases.size(), sheetName);
    } catch (IOException e) {
      LOGGER.error("Failed to read sheet '{}'. Root cause: {}", sheetName, e.getMessage());
    }
    return testCases;
  }

  /** Header row is the first row that contains a "Test Cases" cell. */
  private static int findHeaderRowIndex(Sheet sheet) {
    for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
      Row row = sheet.getRow(r);
      if (row == null) {
        continue;
      }
      for (Cell cell : row) {
        if (normalize(FORMATTER.formatCellValue(cell)).equals("testcases")) {
          return r;
        }
      }
    }
    return -1;
  }

  /** Maps known logical column names to their physical column index. */
  private static Map<String, Integer> mapColumns(Row headerRow) {
    Map<String, Integer> columns = new HashMap<>();
    for (Cell cell : headerRow) {
      String header = normalize(FORMATTER.formatCellValue(cell));
      int index = cell.getColumnIndex();
      switch (header) {
        case "testcase#":
        case "sr#":
          columns.put("id", index);
          break;
        case "testscenario":
          columns.put("scenario", index);
          break;
        case "testcases":
          columns.put("testcase", index);
          break;
        case "teststeps":
          columns.put("steps", index);
          break;
        case "testdata":
          columns.put("testdata", index);
          break;
        case "expectedresult":
          columns.put("expected", index);
          break;
        default:
          // ignore Actual Result / Retesting / Pass-Fail columns
          break;
      }
    }
    return columns;
  }

  private static String readCell(Row row, int columnIndex) {
    Cell cell = row.getCell(columnIndex);
    return cell == null ? "" : FORMATTER.formatCellValue(cell).trim();
  }

  /** Lower-cases and strips whitespace/newlines so headers compare reliably. */
  private static String normalize(String value) {
    return value == null ? "" : value.toLowerCase().replaceAll("\\s+", "");
  }
}
