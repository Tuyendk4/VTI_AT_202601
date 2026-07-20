package vn.edu.vtiacademy.pages_factory;

import java.io.File;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import vn.edu.vtiacademy.common.keywords.WebUI;
import vn.edu.vtiacademy.pages.components.LeftMenu;

public class BasePage {

  protected final String IMAGES_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
          + File.separator + "resources" + File.separator + "images"; // /Users/tuyenluu/training-workspace/VTI_AT_202601/UiFramework/src/main/resources/images
  protected WebUI webUI;

  public BasePage(WebUI webUI) {
    this.webUI = webUI;
    PageFactory.initElements(new AjaxElementLocatorFactory(webUI.getWebDriver(), 30), this);
  }

  public LeftMenu leftMenu() {
    return new LeftMenu(webUI);
  }

}
