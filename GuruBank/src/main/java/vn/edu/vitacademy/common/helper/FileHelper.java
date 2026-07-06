package vn.edu.vitacademy.common.helper;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
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

  public static void saveToJsonFile(String filePath, String key, String value) {
    String jsonContent = null;
    try {
      jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
    } catch (Exception e) {
      LOGGER.error("Failed to read content from '{}'. Root cause: {}", filePath, e.getMessage());
    }

    DocumentContext jsonContext = JsonPath.parse(jsonContent);
    jsonContext.set("$." + key, value);
    try {
      Files.write(Paths.get(filePath), jsonContext.jsonString().getBytes());
    } catch (Exception e) {
      LOGGER.error("Failed to save data to '{}'. Root cause: {}", filePath, e.getMessage());
    }
  }

  public static String getValueOfKeyFromJsonFile(String jsonFilePath, String key) {
    try {
      File file = new File(jsonFilePath);
      return JsonPath.parse(file).read("$." + key, String.class);
    } catch (Exception e) {
      LOGGER.error("Failed to read value of key '{}' from '{}'. Root cause: {}", key, jsonFilePath, e.getMessage());
    }
    return null;
  }
}
