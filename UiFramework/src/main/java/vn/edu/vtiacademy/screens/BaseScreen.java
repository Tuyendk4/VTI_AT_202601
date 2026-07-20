package vn.edu.vtiacademy.screens;

import com.jayway.jsonpath.JsonPath;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vtiacademy.common.keywords.MobileUI;

public class BaseScreen {

  private final String REPO_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
          + File.separator + "resources" + File.separator + "object_repository";
  protected MobileUI mobileUI;
  protected final Logger LOGGER = LoggerFactory.getLogger(BaseScreen.class);

  public BaseScreen(MobileUI mobileUI) {
    this.mobileUI = mobileUI;
//    AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(mobileUI.getDriver(), 30);
//    PageFactory.initElements(factory, this);
  }
  private String repoName;

  protected void setRepoName(String repoName) {
    this.repoName = repoName;
  }

  private String getRepoFilePath() {
    if(mobileUI.isAndroid()) {
      return REPO_FOLDER_PATH + File.separator + "mobile" + File.separator + "android" + File.separator + repoName + ".json";
    } else {
      return REPO_FOLDER_PATH + File.separator + "mobile" + File.separator + "ios" + File.separator + repoName + ".json";
    }
  }

  protected String findTestObject(String objectName) {
    File repoFile = new File(getRepoFilePath());
    try {
      return JsonPath.parse(repoFile).read("$." + objectName);
    } catch (IOException e) {
      LOGGER.error("Failed to find test object '{}' in json file. Root cause: {}", objectName, e.getMessage());
    }
    return null;
  }

  public NavigationBar navigationBar() {
    return new NavigationBar(mobileUI);
  }
}
