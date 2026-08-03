package vn.edu.vtiacademy.apis;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class BaseApi {

  protected static final String AUTHEN_URL = "http://localhost:3004";
  protected static final String BOOKING_URL = "http://localhost:3000";

  protected static Response response;

  @Step("Should see http code as {0}")
  public static boolean shouldSeeHttpCodeAs(int expectedCode) {
    return response.getStatusCode() == expectedCode;
  }

}
