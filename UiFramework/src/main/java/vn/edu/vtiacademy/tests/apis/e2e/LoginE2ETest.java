package vn.edu.vtiacademy.tests.apis.e2e;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import vn.edu.vtiacademy.apis.LoginApi;
import vn.edu.vtiacademy.apis.LoginValidateApi;
import vn.edu.vtiacademy.apis.LogoutApi;

public class LoginE2ETest {

  @Test(description = "LGE2E-001: Login - Validate - Logout successfully")
  public void LGE2E_001_login_validate_logout_successfully() {
    LoginApi.send("admin", "password");
    assertTrue(LoginApi.shouldSeeHttpCodeAs(200));
    String token = LoginApi.getToken();
    LoginValidateApi.send(token);
    assertTrue(LoginValidateApi.shouldSeeHttpCodeAs(200));
    LogoutApi.send(token);
    assertTrue(LogoutApi.shouldSeeHttpCodeAs(200));
  }
}
