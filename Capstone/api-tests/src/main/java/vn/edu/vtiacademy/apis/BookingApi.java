package vn.edu.vtiacademy.apis;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import vn.edu.vtiacademy.model.apis.Booking;

/**
 * CRUD tren {@code /booking} cua Restful Booker.
 *
 * <p>Moi ham chi lam MOT viec: gui request va tra ve {@code Response} nguyen ban.
 * <b>Khong assert o day.</b> Ly do: mot ham vua goi API vua assert thi khong tai su dung
 * duoc cho test negative - test muon kiem tra {@code 403} se bi chinh ham do nem loi
 * truoc khi kip assert. Tach ra thi 1 ham dung duoc cho ca happy path lan error path.
 *
 * <p>Token duoc truyen vao tung ham thay vi luu static: xem ly do o {@link BaseApi}.
 */
public class BookingApi extends BaseApi {

  private static final String TOKEN_COOKIE = "token";

  @Step("POST /booking - tao booking moi cho '{0}'")
  public static Response create(Booking booking) {
    return RestAssured.given()
        .spec(baseSpec())
        .body(booking)
        .when()
        .post(BOOKING_ENDPOINT)
        .then()
        .extract()
        .response();
  }

  @Step("GET /booking/{0} - lay chi tiet booking")
  public static Response getById(int bookingId) {
    return RestAssured.given()
        .spec(baseSpec())
        .when()
        .get(BOOKING_ENDPOINT + "/" + bookingId)
        .then()
        .extract()
        .response();
  }

  @Step("PUT /booking/{0} - cap nhat booking (co token)")
  public static Response update(int bookingId, Booking booking, String token) {
    return RestAssured.given()
        .spec(baseSpec())
        .cookie(TOKEN_COOKIE, token)
        .body(booking)
        .when()
        .put(BOOKING_ENDPOINT + "/" + bookingId)
        .then()
        .extract()
        .response();
  }

  /**
   * PUT KHONG kem token - dung cho test negative kiem tra API co chan nguoi la khong.
   *
   * <p>Day la case quan trong nhat trong bo API test: mot API cho phep sua du lieu ma
   * khong can xac thuc la lo hong nghiem trong. Test nay phai luon co trong suite.
   */
  @Step("PUT /booking/{0} - cap nhat booking KHONG co token (negative)")
  public static Response updateWithoutToken(int bookingId, Booking booking) {
    return RestAssured.given()
        .spec(baseSpec())
        .body(booking)
        .when()
        .put(BOOKING_ENDPOINT + "/" + bookingId)
        .then()
        .extract()
        .response();
  }

  /**
   * Xoa booking.
   *
   * <p>Luu y: Restful Booker tra ve chuoi {@code "Created"} dang {@code text/plain} cho
   * DELETE thanh cong, khong phai JSON. Vi vay o day chi lay {@code Response} tho va test
   * chi assert theo status code - goi {@code jsonPath()} tren response nay se nem loi parse.
   */
  @Step("DELETE /booking/{0} - xoa booking")
  public static Response delete(int bookingId, String token) {
    return RestAssured.given()
        .spec(baseSpec())
        .cookie(TOKEN_COOKIE, token)
        .when()
        .delete(BOOKING_ENDPOINT + "/" + bookingId)
        .then()
        .extract()
        .response();
  }
}
