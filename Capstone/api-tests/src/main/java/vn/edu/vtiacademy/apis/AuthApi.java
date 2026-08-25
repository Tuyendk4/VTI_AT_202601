package vn.edu.vtiacademy.apis;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Map;

/**
 * {@code POST /auth} - lay token de goi duoc cac thao tac ghi (PUT / PATCH / DELETE).
 *
 * <p>Restful Booker khong dung {@code Authorization: Bearer}. No doi token nam trong
 * <b>cookie</b> ten {@code token}. Goi sai cach se nhan {@code 403 Forbidden} chu khong
 * phai {@code 401}, va thong bao khong noi ro nguyen nhan - day la cho de mat thoi gian
 * nhat khi lam quen voi API nay.
 */
public class AuthApi extends BaseApi {

  private static final String AUTH_ENDPOINT = "/auth";

  /** Tai khoan demo cong khai, ghi thang trong tai lieu cua Restful Booker. */
  public static final String VALID_USERNAME = "admin";
  public static final String VALID_PASSWORD = "password123";

  @Step("POST /auth - dang nhap voi username '{0}'")
  public static Response createToken(String username, String password) {
    return RestAssured.given()
        .spec(baseSpec())
        .body(Map.of("username", username, "password", password))
        .when()
        .post(AUTH_ENDPOINT)
        .then()
        .extract()
        .response();
  }

  /**
   * Lay token bang tai khoan hop le. Dung trong {@code @BeforeClass} cua cac test can quyen ghi.
   *
   * @return chuoi token, hoac {@code null} neu server tra ve loi (test se do o buoc assert
   *     tiep theo voi thong bao ro rang hon la nem exception o day)
   */
  @Step("Lay token bang tai khoan hop le")
  public static String getValidToken() {
    return createToken(VALID_USERNAME, VALID_PASSWORD).jsonPath().getString("token");
  }
}
