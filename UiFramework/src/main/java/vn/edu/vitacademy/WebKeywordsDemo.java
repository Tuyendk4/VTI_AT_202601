package vn.edu.vitacademy;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import vn.edu.vitacademy.common.keywords.WebUI;

public class WebKeywordsDemo {

  private static final String BROWSER_NAME = "Chrome";
  private static final String DEMO_URL = "https://demoqa.com/text-box";
  private static final String LBL_FULL_NAME = "//label[@id='userName-label']";
  private static final String TXT_FULL_NAME = "//input[@id='userName']";

  private static final String TXT_EMAIL = "//input[@id='userEmail']";
  private static final String BTN_SUBMIT = "//button[@id='submit']";

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
    webUI.getTagName(LBL_FULL_NAME);
    webUI.getCssValue(LBL_FULL_NAME, "color");
    webUI.getAttributeValue(LBL_FULL_NAME, "id");
    webUI.getElementHeigh(LBL_FULL_NAME);
    webUI.getElementWidth(LBL_FULL_NAME);
    webUI.getHorizontalPosition(LBL_FULL_NAME);
    webUI.getVerticalPosition(LBL_FULL_NAME);
    webUI.inputText(TXT_FULL_NAME, "Automation Tester");
    webUI.getText(TXT_FULL_NAME);
    webUI.getAttributeValue(TXT_FULL_NAME, "value");
    webUI.closeBrowser();
  }

}
