package vn.edu.vtiacademy.tests.mobile;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import vn.edu.vtiacademy.screens.LoginScreen;

public class LoginTest extends BaseMobileTest {

  private LoginScreen loginScreen;

  @Test(description = "LG001: Should show email error message after inputted invalid email")
  public void LG001_should_show_email_error_message_after_inputted_invalid_email() {
    loginScreen = homeScreen.navigationBar().clickLoginTab();
    loginScreen.inputEmail("abdgmail.com");
    loginScreen.clickLoginButton();

    assertTrue(loginScreen.shouldShowEmailErrorMessage("Please enter a valid email address"));
  }

  @Test(description = "LG002: Should show password error message after inputted invalid password")
  public void LG002_should_show_password_error_message_after_inputted_invalid_password() {
    loginScreen.inputPassword("123456");
    loginScreen.clickLoginButton();
    assertTrue(loginScreen.shouldShowPasswordErrorMessage("Please enter at least 8 characters"));
  }
}
