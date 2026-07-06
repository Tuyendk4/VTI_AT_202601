package vn.edu.vitacademy.steps;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import vn.edu.vitacademy.common.keywords.WebUI;

public class Hooks extends BaseSteps {

  @Before
  public void beforeScenario(Scenario scenario) {
    LOGGER.info("=========================Before scenario {}=========================", scenario.getName());
    webUI = new WebUI();
    webUI.openBrowser("Chrome");
    webUI.maximizeWindow();
    webUI.navigateToUrl(EMPLOYEE_URL);
  }

  @After
  public void afterScenario(Scenario scenario) {
    webUI.closeBrowser();
    LOGGER.info("=========================After scenario {}=========================", scenario.getName());
  }

  @BeforeAll
  public static void beforeAll() {
    LOGGER.info("================================Before all===============================");
  }

  @AfterAll
  public static void afterAll() {
    LOGGER.info("================================After all===============================");
  }
}






