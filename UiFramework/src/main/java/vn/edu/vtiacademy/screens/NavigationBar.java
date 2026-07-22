package vn.edu.vtiacademy.screens;

import io.qameta.allure.Step;
import vn.edu.vtiacademy.common.keywords.MobileUI;

public class NavigationBar extends BaseScreen{

  public NavigationBar(MobileUI mobileUI) {
    super(mobileUI);
    setRepoName(NavigationBar.class.getSimpleName());
  }

  public void clickWebViewTab() {
    mobileUI.tapOn(findTestObject("TAB_WEBVIEW"));
    mobileUI.takeScreenshot();
    mobileUI.takeScreenshot(findTestObject("TAB_WEBVIEW"));
    mobileUI.takeScreenshotAndMarkElement(findTestObject("TAB_WEBVIEW"));
  }

  @Step("Tap on Login tab")
  public LoginScreen clickLoginTab() {
    mobileUI.tapOn(findTestObject("TAB_LOGIN"));
    mobileUI.takeScreenshot();
    mobileUI.takeScreenshot(findTestObject("TAB_LOGIN"));
    mobileUI.takeScreenshotAndMarkElement(findTestObject("TAB_LOGIN"));
    return new LoginScreen(mobileUI);
  }

  public void clickFormsTab() {
    mobileUI.tapOn(findTestObject("TAB_FORMS"));
    mobileUI.takeScreenshot();
    mobileUI.takeScreenshot(findTestObject("TAB_FORMS"));
    mobileUI.takeScreenshotAndMarkElement(findTestObject("TAB_FORMS"));
  }

  public void clickSwipeTab() {
    mobileUI.tapOn(findTestObject("TAB_SWIPE"));
    mobileUI.takeScreenshot();
  }

  public void clickDragTab() {
    mobileUI.tapOn(findTestObject("TAB_DRAG"));
    mobileUI.takeScreenshot();
  }
}
