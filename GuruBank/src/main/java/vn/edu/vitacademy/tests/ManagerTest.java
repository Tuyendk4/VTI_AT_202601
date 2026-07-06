package vn.edu.vitacademy.tests;

import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.model.Employee;
import vn.edu.vitacademy.pages.ManagerPage;

public class ManagerTest extends BaseTest {

//  private ManagerPage managerPage;

  @Test(description = "MG001 - Login successfully")
  public void MG001_Login_successfully() {
    ManagerPage managerPage = loginPage.loginWith(registeredUser.getUserId(), registeredUser.getPassword());
//    assertTrue(managerPage.shouldShowWelcomeMessageAs("Welcome To Manager's Page of Guru99 Bank"));
    assertTrue(managerPage.shouldShowUserIdAs("Manger Id : " + registeredUser.getUserId()));
  }

}
