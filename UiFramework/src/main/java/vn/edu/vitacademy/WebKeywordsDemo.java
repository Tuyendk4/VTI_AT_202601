package vn.edu.vitacademy;

import vn.edu.vitacademy.common.keywords.WebUI;

public class WebKeywordsDemo {

//  public static void main() {
//    WebUI webUI = new WebUI();
//    webUI.openBrowser("Chrome");
//    webUI.navigateToUrl("https://dantri.com.vn");
//    webUI.delayInSeconds(5);
//    webUI.getTitle();
//    webUI.getUrl();
//    webUI.getPageSource();
//    webUI.navigateToUrl("https://vnexpress.net/");
//    webUI.delayInSeconds(5);
//    webUI.back();
//    webUI.delayInSeconds(5);
//    webUI.forward();
//    webUI.delayInSeconds(5);
//    webUI.refresh();
//    webUI.delayInSeconds(5);
//    webUI.closeBrowser();
//  }

  public static void main() {
    WebUI webUI = new WebUI();
    webUI.openBrowser("Chrome", "https://demoqa.com/text-box");
    webUI.delayInSeconds(5);
    webUI.inputText("//input[@id='userName']", "Automation Tester");
    webUI.delayInSeconds(5);
    webUI.clearText("//input[@id='userName']");
    webUI.delayInSeconds(5);
    webUI.closeBrowser();
  }

}
