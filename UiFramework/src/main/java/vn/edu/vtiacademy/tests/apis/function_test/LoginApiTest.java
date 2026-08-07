package vn.edu.vtiacademy.tests.apis.function_test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import org.testng.annotations.Test;
import vn.edu.vtiacademy.apis.LoginApi;
import vn.edu.vtiacademy.model.apis.LoginErrorResponseBody;
import vn.edu.vtiacademy.model.apis.UserRequestBody;
import vn.edu.vtiacademy.tests.apis.LoginApiBaseTest;

public class LoginApiTest extends LoginApiBaseTest {

  @Test(description = "LG001 - Login successfully")
  public void LG001_login_successfully() {
    LoginApi.send("admin", "password");
    assertTrue(LoginApi.shouldSeeHttpCodeAs(200));
  }

  @Test(description = "LG001 - Login successfully")
  public void LG001_POJO_login_successfully() {
    LoginApi.send(new UserRequestBody("admin", "password"));
    assertTrue(LoginApi.shouldSeeHttpCodeAs(200));
  }

  @Test(description = "LG002 - Login with username as null")
  public void LG002_login_with_username_as_null() {
    LoginApi.sendUserNameAsNull("password");
    assertTrue(LoginApi.shouldSeeHttpCodeAs(403));
  }

  @Test(description = "LG003 - Login with username as object")
  public void LG003_login_with_username_as_object() {
    LoginApi.sendUserNameAsObject("admin", "password");
    assertTrue(LoginApi.shouldSeeHttpCodeAs(400));
    assertTrue(LoginApi.shouldShowErrorStatusCode(400));
    assertTrue(LoginApi.shouldShowMessageError("Bad Request"));
    assertTrue(LoginApi.shouldShowPath("/auth/login"));
  }

  @Test(description = "LG003 - Login with username as object")
  public void LG003_POJO_login_with_username_as_object() {
    LoginErrorResponseBody response = LoginApi.sendUserNameAsObject_POJO("admin", "password");
    assertTrue(response.getStatus() == 400);
    assertTrue(response.getTimestamp() instanceof String);
    assertTrue(response.getError().equals("Bad Request"));
    assertTrue(response.getPath().equals("/auth/login"));
  }

  @Test(description = "LG004 - Login with username as array")
  public void LG004_login_with_user_name_as_array() {
    ArrayList<String> usernames = new ArrayList<>();
    usernames.add("admin");
    usernames.add("user");
    LoginApi.send(usernames, "password");
    assertTrue(LoginApi.shouldSeeHttpCodeAs(400));
    assertTrue(LoginApi.shouldShowErrorStatusCode(400));
    assertTrue(LoginApi.shouldShowMessageError("Bad Request"));
    assertTrue(LoginApi.shouldShowPath("/auth/login"));
  }
}
