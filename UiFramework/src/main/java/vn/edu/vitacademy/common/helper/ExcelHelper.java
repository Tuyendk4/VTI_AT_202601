package vn.edu.vitacademy.common.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExcelHelper.class);
  private static XSSFWorkbook workbook;
  private static XSSFSheet sheet;
  private static XSSFCell cell;

  public static void setExcelFile(String excelFilePath, String sheetName) {
    try {
      LOGGER.info("Setting excel file with path '{}' and sheet name '{}'", excelFilePath, sheetName);
      workbook = new XSSFWorkbook(excelFilePath);
      sheet = workbook.getSheet(sheetName);
      LOGGER.info("Excel file with path '{}' and sheet name '{}' has been set successfully", excelFilePath, sheetName);
    } catch (Exception e) {
      LOGGER.error("Failed to set excel file with path '{}', and sheet name '{}'. Root cause: {}",
          excelFilePath, sheetName, e.getMessage());
    }
  }
  
  public static XSSFWorkbook getWorkbook(String excelFilePath) {
    try {
      LOGGER.info("Getting workbook with path '{}'", excelFilePath);
      XSSFWorkbook workbook = new XSSFWorkbook(excelFilePath);
      LOGGER.info("Workbook with path '{}' has been retrieved successfully", excelFilePath);
      return workbook;
    } catch (Exception e) {
      LOGGER.error("Failed to get workbook with path '{}'. Root cause: {}", excelFilePath, e.getMessage());
    }
    return null;
  }

  public static XSSFSheet getSheet(XSSFWorkbook workbook, String sheetName) {
    return workbook.getSheet(sheetName);
  }

  public static String getCellValue(XSSFSheet sheet, int rowIndex, int colIndex) {
    String cellValue = "";
    try {
      XSSFCell cell = sheet.getRow(rowIndex).getCell(colIndex);
      if (cell == null) {
        return "";
      }
      switch (cell.getCellType()) {
        case STRING:
          cellValue = cell.getStringCellValue();
          break;
        case NUMERIC:
          cellValue = String.valueOf(cell.getNumericCellValue());
          break;
        case BOOLEAN:
          cellValue = String.valueOf(cell.getBooleanCellValue());
          break;
        case FORMULA:
          cellValue = cell.getCellFormula();
          break;
        default:
          cellValue = "";
      }
    } catch (Exception e) {
      LOGGER.error("Failed to get cell value at row '{}' and column '{}'. Root cause: {}", rowIndex, colIndex, e.getMessage());
    }
    return cellValue;
  }

  public static Cell findFirstCellWithStringValue(XSSFSheet sheet, String value) {
    for (Row row : sheet) {
      for (Cell cell : row) {
        if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().equals(value)) {
          return cell;
        }
      }
    }
    return null;
  }

  public static Cell findLastCellWithStringValue(XSSFSheet sheet, String value) {
    Cell lastCell = null;
    for (Row row : sheet) {
      for (Cell cell : row) {
        if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().equals(value)) {
          lastCell = cell;
        }
      }
    }
    return lastCell;
  }

  public static int getLastRowNumber(XSSFSheet sheet) {
    return sheet.getLastRowNum();
  }

  public static int getRowFromCell(Cell cell) {
    return cell.getRow().getRowNum();
  }

  public static XSSFRow getRowByIndex(XSSFSheet sheet, int rowIndex) {
    return sheet.getRow(rowIndex);
  }

  public static int getColumnFromCell(Cell cell) {
    return cell.getColumnIndex();
  }

  public static void saveWorkbook(String excelFilePath, XSSFWorkbook workbook) {
    try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
      workbook.write(fileOut);
      fileOut.flush();
      fileOut.close();
      LOGGER.info("Workbook saved successfully to '{}'", excelFilePath);
    } catch (IOException e) {
      LOGGER.error("Failed to save workbook to '{}'. Root cause: {}", excelFilePath, e.getMessage());
    }
  }
}