package vn.edu.vitacademy.pages_factory.components;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.TextBoxPage;

public class LeftMenu {

  private static final String LNK_TEXT_BOX = "//a/span[.='Text Box']";
  private WebUI webUI;

  public LeftMenu(WebUI webUI) {
    this.webUI = webUI;
  }

  public TextBoxPage moveToTextBox() {
    webUI.clickOn(LNK_TEXT_BOX);
    webUI.delayInSeconds(3);
    return new TextBoxPage(webUI);
  }
}
