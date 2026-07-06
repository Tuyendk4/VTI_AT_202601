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
import vn.edu.vitacademy.common.helper.DateTimeHelper;
import vn.edu.vitacademy.common.helper.FileHelper;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.RegisteredUser;
import vn.edu.vitacademy.pages.AccessDetailAccountPage;
import vn.edu.vitacademy.pages.LoginPage;
import vn.edu.vitacademy.pages.RegisterEmailPage;

public class BaseTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
  private static final String ALLURE_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "target" + File.separator
          + "allure-results";
  private final String DATA_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
          + File.separator + "resources" + File.separator + "data";

  private final String REGISTERED_USER_FILE_NAME = "RegisteredUser.json";
  protected LoginPage loginPage;
  private WebUI webUI;
  private String dataSource;

  protected RegisteredUser registeredUser;


  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    LOGGER.info("==================Start suite");
    FileHelper.deleteFolder(ALLURE_FOLDER_PATH);
  }

  @Parameters({"browser", "url", "email"})
  @BeforeTest(alwaysRun = true)
  public void beforeTest(@Optional("Chrome") String browser, String url, String email) {
    LOGGER.info("------------------Start test");
    webUI = new WebUI();
    webUI.openBrowser(browser, url);
    webUI.maximizeWindow();
    loginPage = new LoginPage(webUI);
    String registeredUserAbsoluteFilePath =
        DATA_FOLDER_PATH + File.separator + REGISTERED_USER_FILE_NAME;
    registeredUser = RegisteredUser.Builder.aUser()
        .withUserId(FileHelper.getValueOfKeyFromJsonFile(registeredUserAbsoluteFilePath, "userId"))
        .withPassword(
            FileHelper.getValueOfKeyFromJsonFile(registeredUserAbsoluteFilePath, "password"))
        .withCreatedDate(
            FileHelper.getValueOfKeyFromJsonFile(registeredUserAbsoluteFilePath, "createdDate"))
        .build();
    if (registeredUser.getUserId().isEmpty() || registeredUser.getPassword().isEmpty() ||
        DateTimeHelper.calculateDays(registeredUser.getCreatedDate(),
            DateTimeHelper.formatCurrentDateAs("yyyyMMdd")) > 20 ||
        DateTimeHelper.calculateDays(registeredUser.getCreatedDate(),
            DateTimeHelper.formatCurrentDateAs("yyyyMMdd")) == -1) {
      RegisterEmailPage registerEmailPage = loginPage.clickHereLink();
      AccessDetailAccountPage accessDetailAccountPage = registerEmailPage.registerEmail(email);
      registeredUser = accessDetailAccountPage.saveRegisteredUser();
      FileHelper.saveToJsonFile(registeredUserAbsoluteFilePath, "userId",
          registeredUser.getUserId());
      FileHelper.saveToJsonFile(registeredUserAbsoluteFilePath, "password",
          registeredUser.getPassword());
      FileHelper.saveToJsonFile(registeredUserAbsoluteFilePath, "createdDate",
          registeredUser.getCreatedDate());
      loginPage = navigateToLoginPage(url);
    }
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
    return DATA_FOLDER_PATH + File.separator + dataSource + ".json";
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
      LOGGER.error("Failed to find test object '{}' in json file. Root cause: {}", dataName,
          e.getMessage());
    }
    return null;
  }

  public LoginPage navigateToLoginPage(String loginPageUrl) {
    webUI.navigateToUrl(loginPageUrl);
    return new LoginPage(webUI);
  }
}
