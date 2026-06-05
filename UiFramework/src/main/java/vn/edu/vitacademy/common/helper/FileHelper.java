package vn.edu.vitacademy.common.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

  public static void createFile(String filePath) {
    try {
      File file = new File(filePath);
      if (!file.exists()) {
        if(file.createNewFile()) {
          LOGGER.info("File '{}' created successfully", filePath);
        }
      } else {
        LOGGER.info("File '{}' already exists", filePath);
      }
    } catch (IOException e) {
      LOGGER.error("Failed to create file at path '{}'. Root cause: {}", filePath, e.getMessage());
    }
  }

  public static void saveFile(File file, String targetPath) {
    createFile(targetPath);
    LOGGER.info("Saving file '{}' to path '{}'", file.getName(), targetPath);
    try {
      Files.copy(file.toPath(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      LOGGER.error("Failed to save file '{}' to path '{}'. Root cause: {}", file.getName(), targetPath, e.getMessage());
    }
  }
}
