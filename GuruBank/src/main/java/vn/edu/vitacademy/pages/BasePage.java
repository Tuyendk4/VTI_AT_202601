package vn.edu.vitacademy.pages;

import com.jayway.jsonpath.JsonPath;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.components.LeftMenu;

public class BasePage {

  protected final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);
  protected final String IMAGES_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
          + File.separator + "resources" + File.separator
          + "images"; // /Users/tuyenluu/training-workspace/VTI_AT_202601/UiFramework/src/main/resources/images
  private final String OBJECT_REPOSITORY_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
          + File.separator + "resources" + File.separator + "object_repository";

  protected WebUI webUI;
  private String repoName;

  public BasePage(WebUI webUI) {
    this.webUI = webUI;
  }

  private String getRepoName() {
    return OBJECT_REPOSITORY_FOLDER_PATH + File.separator + repoName + ".json"; // /Users/tuyenluu/training-workspace/VTI_AT_202601/UiFramework/src/main/resources/object_repository/LoginPage.json
  }

  protected void setRepoName(String repoName) {
    this.repoName = repoName;
  }

  protected String findTestObject(String objectName) {
    LOGGER.info("Finding test object '{}' in '{}' file", objectName, getRepoName());
    File repoFile = new File(getRepoName());
    try {
      return JsonPath.parse(repoFile).read(objectName);
    } catch (IOException e) {
      LOGGER.error("Failed to find test object '{}' in json file. Root cause: {}", objectName, e.getMessage());
    }
    return null;
  }

  public LeftMenu isOnLeftMenu() {
    return new LeftMenu(webUI);
  }

}
