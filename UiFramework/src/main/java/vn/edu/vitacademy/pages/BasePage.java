package vn.edu.vitacademy.pages;

import java.io.File;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.components.LeftMenu;

public class BasePage {

  protected final String IMAGES_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
          + File.separator + "resources" + File.separator + "images"; // /Users/tuyenluu/training-workspace/VTI_AT_202601/UiFramework/src/main/resources/images
  protected WebUI webUI;

//  public LeftMenu leftMenu;

  public BasePage(WebUI webUI) {
    this.webUI = webUI;
//    leftMenu = new LeftMenu(webUI);
  }

  public LeftMenu leftMenu() {
    return new LeftMenu(webUI);
  }

}
