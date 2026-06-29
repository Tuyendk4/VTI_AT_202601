package vn.edu.vitacademy.tests;

import java.io.File;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import vn.edu.vitacademy.common.helper.ConfigReader;
import vn.edu.vitacademy.common.helper.FileHelper;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.guru.ManagerHomePage;
import vn.edu.vitacademy.pages.guru.ManagerLoginPage;

/**
 * Lifecycle base for Guru99 Banking data-driven tests.
 *
 * <p>Browser and URL are injected from the TestNG suite XML via {@code @Parameters}.
 * Login credentials are resolved by {@link ConfigReader} (env var, system property
 * or gitignored {@code config.properties}) so no secret lives in committed files;
 * the optional {@code username}/{@code password} suite parameters act only as a
 * fallback. The manager login runs once per {@code <test>} and the resulting
 * {@link ManagerHomePage} is shared with the concrete scenario test classes.
 */
public class GuruBankBaseTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuruBankBaseTest.class);

  // Static so the single manager login in @BeforeTest is shared across every
  // scenario test class. TestNG instantiates one object per <class> in a <test>,
  // so instance fields would only be populated on the first instance and leave
  // the other classes with a null homePage.
  protected static WebUI webUI;
  protected static ManagerHomePage homePage;

  private static final String ALLURE_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "target" + File.separator + "allure-results";

  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    LOGGER.info("==================Start Guru99 Banking suite");
    FileHelper.deleteFolder(ALLURE_FOLDER_PATH);
  }

  @Parameters({"browser", "url", "username", "password"})
  @BeforeTest(alwaysRun = true)
  public void beforeTest(@Optional("Chrome") String browser, String url,
      @Optional("") String username, @Optional("") String password) {
    LOGGER.info("------------------Start test - opening {} at {}", browser, url);
    // Credentials come from ConfigReader (env var / -D / config.properties); the
    // suite XML parameters are only the lowest-priority fallback.
    String resolvedUsername = ConfigReader.get("guru.username", "GURU_USERNAME", username);
    String resolvedPassword = ConfigReader.get("guru.password", "GURU_PASSWORD", password);
    webUI = new WebUI();
    webUI.openBrowser(browser, url);
    webUI.maximizeWindow();
    homePage = new ManagerLoginPage(webUI).login(resolvedUsername, resolvedPassword);
  }

  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method method) {
    LOGGER.info("Running test method: {}", method.getName());
  }

  @AfterTest(alwaysRun = true)
  public void afterTest() {
    if (webUI != null) {
      webUI.closeBrowser();
    }
    LOGGER.info("------------------Ended test");
  }

  @AfterSuite(alwaysRun = true)
  public void afterSuite() {
    LOGGER.info("==================Ended Guru99 Banking suite");
  }
}
