package vn.edu.vitacademy.tests;

import com.jayway.jsonpath.JsonPath;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import vn.edu.vitacademy.common.helper.FileHelper;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.EmployeesPage;

public class BaseTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesTest_POM.class);
  private WebUI webUI;
  protected EmployeesPage employeesPage;

  private static final String ALLURE_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "target" + File.separator + "allure-results";
  private final String DATA_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
          + File.separator + "resources" + File.separator + "data";

  private String dataSource;


  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    LOGGER.info("==================Start suite");
    FileHelper.deleteFolder(ALLURE_FOLDER_PATH);
  }

  @Parameters({"browser", "url"})
  @BeforeTest(alwaysRun = true)
  public void beforeTest(@Optional("Chrome")String browser, String url) {
    LOGGER.info("------------------Start test");
    webUI = new WebUI();
    webUI.openBrowser(browser, url);
    webUI.maximizeWindow();
    employeesPage = new EmployeesPage(webUI);
  }


  @BeforeClass(alwaysRun = true)
  public void beforeClass() {
    LOGGER.info("Running test class: {}", this.getClass().getSimpleName());
  }

  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method method) {
    LOGGER.info("Running test method: {}", method.getName());
  }

  @AfterMethod(alwaysRun = true)
  public void afterMethod(Method method) {
    LOGGER.info("Ended test method: {}", method.getName());
  }

  @AfterClass(alwaysRun = true)
  public void afterClass() {
    webUI.closeBrowser();
    LOGGER.info("Ended test class: {}", this.getClass().getSimpleName());
  }

  @AfterTest(alwaysRun = true)
  public void afterTest() {
    webUI.closeBrowser();
    LOGGER.info("------------------Ended test");
  }

  @AfterSuite(alwaysRun = true)
  public void afterSuite() {
    LOGGER.info("==================Ended suite");
  }

  private String getDataSource() {
    return DATA_FOLDER_PATH + File.separator + dataSource + ".json"; // /Users/tuyenluu/training-workspace/VTI_AT_202601/UiFramework/src/main/resources/object_repository/EmployeesPage.json
  }

  protected void setDataSource(String dataSource) {
    this.dataSource = dataSource;
  }

  protected String findTestData(String dataName) {
    LOGGER.info("Finding test object '{}' in '{}' file", dataName, getDataSource());
    File repoFile = new File(getDataSource());
    try {
      return JsonPath.parse(repoFile).read(dataName);
    } catch (IOException e) {
      LOGGER.error("Failed to find test object '{}' in json file. Root cause: {}", dataName, e.getMessage());
    }
    return null;
  }

}
