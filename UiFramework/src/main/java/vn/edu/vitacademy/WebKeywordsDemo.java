package vn.edu.vitacademy;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import vn.edu.vitacademy.common.keywords.WebUI;

public class WebKeywordsDemo {

  private static final String BROWSER_NAME = "Chrome";
  private static final String DEMO_URL = "https://demoqa.com/text-box";

  private static final String DEMO_DROPDOWN_URL = "https://demoqa.com/select-menu";
  private static final String LBL_FULL_NAME = "//label[@id='userName-label']";
  private static final String TXT_FULL_NAME = "//input[@id='userName']";

  private static final String TXT_EMAIL = "//input[@id='userEmail']";
  private static final String BTN_SUBMIT = "css:button[id='submit']";

  private static final String DDL_COLORS = "//select[@id='oldSelectMenu']";

  private static final String WINDOW_DEMO_URL = "https://demoqa.com/browser-windows";
  private static final String BTN_NEW_TAB = "//button[@id='tabButton']";
  private static final String BTN_NEW_WINDOW = "//button[@id='windowButton']";
  private static final String BTN_NEW_MESSAGE = "//button[@id='messageWindowButton']";



  @Test
  public void Test01_web_browser_and_navigation_keywords() {
    WebUI webUI = new WebUI();
    webUI.openBrowser("Chrome");
    webUI.navigateToUrl("https://dantri.com.vn");
    webUI.delayInSeconds(5);
    webUI.getTitle();
    webUI.getUrl();
    webUI.getPageSource();
    webUI.navigateToUrl("https://vnexpress.net/");
    webUI.delayInSeconds(5);
    webUI.back();
    webUI.delayInSeconds(5);
    webUI.forward();
    webUI.delayInSeconds(5);
    webUI.refresh();
    webUI.delayInSeconds(5);
    webUI.closeBrowser();
  }

  @Test
  public void Test02_click_sendKeys_submit_keywords() {
    WebUI webUI = new WebUI();
    webUI.openBrowser(BROWSER_NAME, DEMO_URL);
    webUI.maximizeWindow();
    webUI.delayInSeconds(5);
    webUI.inputText(TXT_FULL_NAME, "Automation Tester");
    webUI.delayInSeconds(5);
    webUI.selectAllText(TXT_FULL_NAME);
    webUI.delayInSeconds(5);
    webUI.copy(TXT_FULL_NAME);
    webUI.clearText(TXT_FULL_NAME);
    webUI.delayInSeconds(5);
    webUI.paste(TXT_FULL_NAME);
    webUI.delayInSeconds(5);
    webUI.clickOn(BTN_SUBMIT);
    webUI.delayInSeconds(5);
    webUI.inputText(TXT_EMAIL, "test@mailinator.com");
    webUI.delayInSeconds(5);
    webUI.submit(BTN_SUBMIT);
    webUI.delayInSeconds(5);
    webUI.closeBrowser();
  }

  @Test
  public void Test03_other_web_keywords() {
    WebUI webUI = new WebUI();
    webUI.openBrowser(BROWSER_NAME, DEMO_URL);
    webUI.maximizeWindow();
    webUI.delayInSeconds(5);
    webUI.getText(LBL_FULL_NAME);
    assertTrue(webUI.verifyElementContainsText(LBL_FULL_NAME, "Full"));
    webUI.getTagName("id:userName-label");
    webUI.getCssValue("css:label[id='userName-label']", "color");
    webUI.getAttributeValue("xpath://label[@id='userName-label']", "id");
    webUI.getElementHeigh(LBL_FULL_NAME);
    webUI.getElementWidth(LBL_FULL_NAME);
    webUI.getHorizontalPosition(LBL_FULL_NAME);
    webUI.getVerticalPosition(LBL_FULL_NAME);
    webUI.inputText(TXT_FULL_NAME, "Automation Tester");
    webUI.getText(TXT_FULL_NAME);
    webUI.getAttributeValue(TXT_FULL_NAME, "value");
    webUI.closeBrowser();
  }

  @Test
  public void Test04_click_sendKeys_submit_keywords() {
    WebUI webUI = new WebUI();
    webUI.openBrowser(BROWSER_NAME, DEMO_URL);
    webUI.maximizeWindow();
    webUI.inputText(TXT_FULL_NAME, "Automation Tester");
    webUI.submit(BTN_SUBMIT);
    webUI.closeBrowser();
  }

  @Test
  public void Test05_select_option_in_dropdown() {
    WebUI webUI = new WebUI();
    webUI.openBrowser(BROWSER_NAME, DEMO_DROPDOWN_URL);
    webUI.maximizeWindow();
    webUI.selectOptionByIndex(DDL_COLORS, 3); // Yellow
    webUI.delayInSeconds(5);
    webUI.selectOptionByText(DDL_COLORS, "Black");  // Black
    webUI.delayInSeconds(5);
    webUI.selectOptionByValue(DDL_COLORS, "8"); // Indigo
    webUI.delayInSeconds(5);
    webUI.closeBrowser();
  }

  @Test
  public void Test06_waits_in_selenium() {
    WebUI webUI = new WebUI();
    webUI.openBrowser(BROWSER_NAME, DEMO_URL);
    webUI.maximizeWindow();
    webUI.inputText("//input[@id='userName01']", "Automation Tester");
//    webUI.submit(BTN_SUBMIT);
    webUI.closeBrowser();
  }

  @Test
  public void Test07_handle_windows_in_selenium() {
    WebUI webUI = new WebUI();
    webUI.openBrowser(BROWSER_NAME, WINDOW_DEMO_URL);
    webUI.maximizeWindow();
//    webUI.delayInSeconds(5);
    webUI.clickOn(BTN_NEW_TAB);
    webUI.getText("//h1[@id='sampleHeading']");
    webUI.switchToWindowByIndex(1);
    webUI.getText("//h1[@id='sampleHeading']");
    webUI.closeWindowByIndex(1);
    webUI.delayInSeconds(5);
    webUI.switchToWindowByIndex(0);
    webUI.clickOn(BTN_NEW_WINDOW);
    webUI.switchToWindowByURL("https://demoqa.com/sample");
    webUI.getText("//h1[@id='sampleHeading']");
    webUI.switchToWindowByURL(WINDOW_DEMO_URL);
    webUI.delayInSeconds(5);
    webUI.clickOn(BTN_NEW_MESSAGE);
    webUI.switchToWindowByIndex(2);
    webUI.getUrl();
    webUI.getTitle();
    webUI.getText("//body");
    webUI.closeBrowser();
  }
}
