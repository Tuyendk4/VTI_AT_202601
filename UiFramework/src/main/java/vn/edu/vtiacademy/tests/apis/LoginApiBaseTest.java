package vn.edu.vtiacademy.tests.apis;

import java.io.File;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import vn.edu.vtiacademy.apis.BaseApi;
import vn.edu.vtiacademy.common.helper.FileHelper;

public class LoginApiBaseTest {

  private static final String ALLURE_FOLDER_PATH =
      System.getProperty("user.dir") + File.separator + "target" + File.separator
          + "allure-results";

  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    FileHelper.deleteFolder(ALLURE_FOLDER_PATH);
  }

  @BeforeTest(alwaysRun = true)
  @Parameters(value = {"loginUrl"})
  public void setup(String loginUrl) {
    BaseApi.setAuthenUrl(loginUrl);
  }
}
