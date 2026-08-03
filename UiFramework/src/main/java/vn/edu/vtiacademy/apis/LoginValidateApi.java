package vn.edu.vtiacademy.apis;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class LoginValidateApi extends BaseApi {

  private static final String LOGIN_VALIDATE_END_POINT = "/auth/validate";

  @Step("Send login validate api with token '{0}")
  public static Response send(String token) {
    String body = "{\"token\":\"" + token + "\"}";
    response = RestAssured.given().baseUri(AUTHEN_URL)
        .header("Content-Type", "application/json")
        .body(body)
        .when().post(LOGIN_VALIDATE_END_POINT)
        .then().log().all().extract().response();
    return response;
  }

}
