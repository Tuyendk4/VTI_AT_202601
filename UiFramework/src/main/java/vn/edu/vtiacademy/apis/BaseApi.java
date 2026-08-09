package vn.edu.vtiacademy.apis;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class BaseApi {

  protected static String authenUrl;
  protected static final String BOOKING_URL = "http://localhost:3000";

  protected static Response response;

  @Step("Should see http code as {0}")
  public static boolean shouldSeeHttpCodeAs(int expectedCode) {
    return response.getStatusCode() == expectedCode;
  }

  public static String getAuthenUrl() {
    return authenUrl;
  }

  public static void setAuthenUrl(String authenUrl) {
    BaseApi.authenUrl = authenUrl;
  }
}
