package vn.edu.vtiacademy.apis;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoginApi extends BaseApi {

  private static final String LOGIN_END_POINT = "/auth/login";


  @Step("Send login api with username '{0}' and password '{1}'")
  public static Response send(String username, String password) {
    String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
    response = RestAssured.given().baseUri(AUTHEN_URL)
        .header("Content-Type", "application/json")
        .body(body)
        .when().post(LOGIN_END_POINT)
        .then().log().all().extract().response();
    return response;
  }

  @Step("Send login api with username '{0}' and password '{1}'")
  public static void send(int username, String password) {
    String body = "{\"username\":" + username + ",\"password\":\"" + password + "\"}";
    response = RestAssured.given().baseUri(AUTHEN_URL)
        .header("Content-Type", "application/json")
        .body(body)
        .when().post(LOGIN_END_POINT)
        .then().log().all().extract().response();
  }

  @Step("Send login api with username '{0}' and password '{1}'")
  public static void sendUserNameAsNull(String password) {
    String body = "{\"username\":" + null + ",\"password\":\"" + password + "\"}";
    response = RestAssured.given().baseUri(AUTHEN_URL)
        .header("Content-Type", "application/json")
        .body(body)
        .when().post(LOGIN_END_POINT)
        .then().log().all().extract().response();
  }

  @Step("Send login api with username '{0}' and password '{1}'")
  public static void send(ArrayList<String> username, String password) {
    String body = "{\"username\":" + username.toArray() + ",\"password\":\"" + password + "\"}";
    response = RestAssured.given().baseUri(AUTHEN_URL)
        .header("Content-Type", "application/json")
        .body(body)
        .when().post(LOGIN_END_POINT)
        .then().log().all().extract().response();
  }

  @Step("Send login api with username '{0}' and password '{1}'")
  public static void sendUserNameAsObject(String username, String password) {
    String body = "{\"username\":{\"username\":\"" + username + "\"}," + ",\"password\":\"" + password + "\"}";
    response = RestAssured.given().baseUri(AUTHEN_URL)
        .header("Content-Type", "application/json")
        .body(body)
        .when().post(LOGIN_END_POINT)
        .then().log().all().extract().response();
  }

  @Step("Send login api with username '{0}' and password '{1}'")
  public static void sendWithLackUsername(String password) {
    String body = "{\"password\":\"" + password + "\"}";
    response = RestAssured.given().baseUri(AUTHEN_URL)
        .header("Content-Type", "application/json")
        .body(body)
        .when().post(LOGIN_END_POINT)
        .then().log().all().extract().response();
  }


  @Step("Get token from cookies of Login api")
  public static String getToken() {
    return response.cookies().get("token");
  }

  @Step("Should show error status code '{0}'")
  public static boolean shouldShowErrorStatusCode(int number) {
    return Integer.parseInt(response.jsonPath().get("status").toString()) == number;
  }

  @Step("Should show message error '{0}'")
  public static boolean shouldShowMessageError(String errorMessage) {
    return response.jsonPath().get("error").equals(errorMessage);
  }

  @Step("Should show path '{0}'")
  public static boolean shouldShowPath(String path) {
    return response.jsonPath().get("path").equals(path);
  }
}
