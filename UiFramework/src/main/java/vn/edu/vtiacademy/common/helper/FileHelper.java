package vn.edu.vtiacademy.common.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

  public static void createFile(String filePath) {
    try {
      LOGGER.info("Creating file '{}'", filePath);
      Path newFilePath = Paths.get(filePath);
      Path parentDir = newFilePath.getParent();
      if (parentDir != null) {
        Files.createDirectories(parentDir);
      }
      Files.createFile(newFilePath);
      LOGGER.info("File '{}' created successfully", filePath);
    } catch (FileAlreadyExistsException ex) {
      LOGGER.info("File '{}' already exists", filePath);
    } catch (IOException | UnsupportedOperationException e) {
      LOGGER.error("Failed to create file at path '{}'. Root cause: {}", filePath, e.getMessage());
    }
  }

  public static void saveFile(File file, String targetPath) {
    try {
      LOGGER.info("Saving file '{}' to path '{}'", file.getName(), targetPath);
      Path target = Paths.get(targetPath);
      Path parentDir = target.getParent();
      if (parentDir != null) {
        Files.createDirectories(parentDir);
      }
      Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      LOGGER.error("Failed to save file '{}' to path '{}'. Root cause: {}", file.getName(), targetPath, e.getMessage());
    }
  }

  public static void deleteFolder(String folderPath) {
    Path path = Paths.get(folderPath);
    LOGGER.info("Deleting folder '{}'", folderPath);
    if (Files.exists(path)) {
      try {
        Files.walk(path)
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
        LOGGER.info("Deleted folder '{}' successfully", folderPath);
      } catch (IOException e) {
        LOGGER.error("Failed to delete folder '{}'. Root cause: {}", folderPath, e.getMessage());
      }
    }
  }

  public static String getCellValueFromExcel(String excelFilePath, String sheetName, String testCaseId, String columnName) {
    XSSFWorkbook workbook = ExcelHelper.getWorkbook(excelFilePath);
    if (workbook == null) {
      return "";
    }
    XSSFSheet sheet = workbook.getSheet(sheetName);
    if (sheet == null) {
      LOGGER.error("Sheet '{}' not found in file '{}'", sheetName, excelFilePath);
      return "";
    }

    // Find column index
    int colIndex = -1;
    XSSFRow headerRow = sheet.getRow(0);
    for (int i = 0; i < headerRow.getLastCellNum(); i++) {
      if (ExcelHelper.getCellValue(sheet, 0, i).equalsIgnoreCase(columnName)) {
        colIndex = i;
        break;
      }
    }
    if (colIndex == -1) {
      LOGGER.error("Column '{}' not found in sheet '{}'", columnName, sheetName);
      return "";
    }

    // Find row index
    int rowIndex = -1;
    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      if (ExcelHelper.getCellValue(sheet, i, 0).equalsIgnoreCase(testCaseId)) {
        rowIndex = i;
        break;
      }
    }
    if (rowIndex == -1) {
      LOGGER.error("Test Case ID '{}' not found in sheet '{}'", testCaseId, sheetName);
      return "";
    }

    return ExcelHelper.getCellValue(sheet, rowIndex, colIndex);
  }

  public static List<HashMap<String, String>> getExcelData(String excelFilePath, String sheetName, String testCaseId) {
    List<HashMap<String, String>> data = new ArrayList<>();
    XSSFWorkbook workbook = ExcelHelper.getWorkbook(excelFilePath);
    if (workbook == null) {
      return data;
    }
    XSSFSheet sheet = ExcelHelper.getSheet(workbook, sheetName);
    if (sheet == null) {
      LOGGER.error("Sheet '{}' not found in file '{}'", sheetName, excelFilePath);
      return data;
    }

    Cell startCell = ExcelHelper.findFirstCellWithStringValue(sheet, testCaseId);
    if (startCell == null) {
      LOGGER.error("Test Case ID '{}' not found in sheet '{}'", testCaseId, sheetName);
      return data;
    }

    Cell endCell = ExcelHelper.findLastCellWithStringValue(sheet, testCaseId);

    int startRow = ExcelHelper.getRowFromCell(startCell);
    int endRow = ExcelHelper.getRowFromCell(endCell);
    int startCol = ExcelHelper.getColumnFromCell(startCell);
    int endCol = ExcelHelper.getColumnFromCell(endCell);

//    for (int i = 0; i <= ExcelHelper.getLastRowNumber(sheet); i++) {
//      XSSFRow row = ExcelHelper.getRowByIndex(sheet, i);
//      if (row != null) {
//        for (int j = 0; j < row.getLastCellNum(); j++) {
//          if (ExcelHelper.getCellValue(sheet, i, j).equalsIgnoreCase(testCaseId)) {
//            if (startRow == -1) {
//              startRow = i;
//            }
//            endRow = i;
//            if (startCol == -1) {
//              startCol = j;
//            }
//            endCol = j;
//          }
//        }
//      }
//    }

    if (startRow != -1) {
      XSSFRow headerRow = ExcelHelper.getRowByIndex(sheet, startRow);
      if (headerRow == null) {
        LOGGER.error("Header row not found for test case '{}' in sheet '{}'", testCaseId, sheetName);
        return data;
      }

      for (int i = startRow + 1; i <= endRow; i++) {
        HashMap<String, String> rowMap = new HashMap<>();
        XSSFRow dataRow = ExcelHelper.getRowByIndex(sheet, i);
        if (dataRow != null) {
          for (int j = startCol; j <= endCol; j++) {
            String header = ExcelHelper.getCellValue(sheet, startRow, j);
            String cellValue = ExcelHelper.getCellValue(sheet, i, j);
            if (header != null && !header.isEmpty()) {
              rowMap.put(header, cellValue);
            }
          }
        }
        data.add(rowMap);
      }
    }

    try {
      workbook.close();
    } catch (IOException e) {
      LOGGER.error("Failed to close workbook. Root cause: {}", e.getMessage());
    }

    return data;
  }
}
