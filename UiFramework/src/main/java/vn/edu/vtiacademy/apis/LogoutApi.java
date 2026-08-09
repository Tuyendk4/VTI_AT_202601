package vn.edu.vtiacademy.apis;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class LogoutApi extends BaseApi {
  private static final String LOGOUT_END_POINT = "/auth/logout";

  @Step("Send logout api with token '{0}")
  public static Response send(String token) {
    String body = "{\"token\":\"" + token + "\"}`";
    response = RestAssured.given().baseUri(authenUrl)
        .header("Content-Type", "application/json")
        .body(body)
        .when().post(LOGOUT_END_POINT)
        .then().log().all().extract().response();
    return response;
  }

}
