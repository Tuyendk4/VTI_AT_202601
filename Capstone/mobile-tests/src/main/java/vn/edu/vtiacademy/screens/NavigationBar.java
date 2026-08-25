package vn.edu.vtiacademy.screens;

import io.qameta.allure.Step;
import vn.edu.vtiacademy.common.keywords.MobileUI;

/**
 * Thanh dieu huong duoi cung cua WDIO Demo App.
 *
 * <p>Ban goc trong {@code UiFramework} co ham cho ca sau tab (Home, Webview, Login, Forms,
 * Swipe, Drag). O day chi giu tab Login: pham vi kiem thu cua module nay la man hinh Login,
 * va ham khong ai goi chi lam nguoi doc mat cong tu hoi "cai nay dung o dau". Can them tab
 * nao thi them locator vao {@code NavigationBar.json} va mot ham o day - moi thu deu ngan.
 */
public class NavigationBar extends BaseScreen {

  public NavigationBar(MobileUI mobileUI) {
    super(mobileUI);
    setRepoName(NavigationBar.class.getSimpleName());
  }

  @Step("Mo tab Login")
  public LoginScreen clickLoginTab() {
    mobileUI.tapOn(findTestObject("TAB_LOGIN"));
    mobileUI.delayInSeconds(1);
    mobileUI.takeScreenshot();
    return new LoginScreen(mobileUI);
  }
}
