package vn.edu.vtiacademy.tests.web;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
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
import vn.edu.vtiacademy.common.helper.FileHelper;
import vn.edu.vtiacademy.common.keywords.WebUI;
import vn.edu.vtiacademy.pages.EmployeesPage;

public class BaseTest_Excel02 {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesTest_POM.class);
  private static final String ALLURE_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "target" + File.separator
          + "allure-results";
  private final String DATA_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
          + File.separator + "resources" + File.separator + "data" + File.separator + "Data.xlsx";
  protected EmployeesPage employeesPage;
  private WebUI webUI;
  private String sheetName;


  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    LOGGER.info("==================Start suite");
    FileHelper.deleteFolder(ALLURE_FOLDER_PATH);
  }

  @Parameters({"browser", "url"})
  @BeforeTest(alwaysRun = true)
  public void beforeTest(@Optional("Chrome") String browser, String url) {
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

  private String getSheetName() {
    return sheetName;
  }

  protected void setSheetName(String sheetName) {
    this.sheetName = sheetName;
  }

  protected List<HashMap<String, String>> getTestData(String testCaseId) {
    LOGGER.info("Finding test case id '{}' in sheet name '{}' of file '{}'", testCaseId,
        getSheetName(), DATA_FOLDER_PATH);
    List<HashMap<String, String>> cellValues = FileHelper.getExcelData(DATA_FOLDER_PATH,
        getSheetName(), testCaseId);
    LOGGER.info("Found test data of test case id '{}' in sheet name '{}' of file '{}'",
        testCaseId, getSheetName(), DATA_FOLDER_PATH);
    return cellValues;
  }

}
