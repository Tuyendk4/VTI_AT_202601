package vn.edu.vitacademy.pages;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.components.LeftMenu;

public class BasePage {

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
