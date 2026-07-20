package vn.edu.vtiacademy.tests.mobile;

import io.qameta.allure.Step;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import vn.edu.vtiacademy.common.helper.Device;
import vn.edu.vtiacademy.common.keywords.MobileUI;
import vn.edu.vtiacademy.screens.HomeScreen;

public class BaseMobileTest {

  private final Logger LOGGER = LoggerFactory.getLogger(BaseMobileTest.class.getName());
  protected MobileUI mobileUI;
  protected HomeScreen homeScreen;

  @BeforeTest(alwaysRun = true)
  @Parameters(value = {"deviceName"})
  public void beforeTest(String deviceName) {
    new Device(deviceName);
    mobileUI = new MobileUI();
    mobileUI.startApplication();
    homeScreen = moveToHomeScreen();
  }

  @BeforeClass
  public void beforeClass() {
    LOGGER.info("--------------------------Start {}------------------------",
        this.getClass().getName());
  }

  @BeforeMethod
  public void setUp(Method method) {
    LOGGER.info("===========================Start {}==========================", method.getName());
  }

  @AfterMethod
  public void tearDown(Method method) {
    LOGGER.info("===========================End {}==========================", method.getName());
  }

  @AfterClass
  public void afterClass() {
    LOGGER.info("--------------------------End {}------------------------",
        this.getClass().getName());
  }

  @AfterTest(alwaysRun = true)
  public void afterTest() {
    mobileUI.stopApplication();
  }

  @Step("Move to Home Screen")
  public HomeScreen moveToHomeScreen() {
    return new HomeScreen(mobileUI);
  }
}
