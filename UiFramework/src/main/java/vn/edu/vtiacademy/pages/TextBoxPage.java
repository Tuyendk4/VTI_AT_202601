package vn.edu.vtiacademy.pages;

import vn.edu.vtiacademy.common.keywords.WebUI;

public class TextBoxPage extends BasePage {

  public TextBoxPage(WebUI webUI) {
    super(webUI);
  }

  public void inputFullName(String fullName) {
    webUI.inputText("FullName", fullName);
  }
}
