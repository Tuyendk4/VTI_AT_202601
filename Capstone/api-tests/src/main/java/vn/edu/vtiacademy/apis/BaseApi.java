package vn.edu.vtiacademy.apis;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Cau hinh dung chung cho moi lop API.
 *
 * <p><b>Ba quyet dinh thiet ke o day, va ly do:</b>
 *
 * <ol>
 *   <li><b>Khong giu {@code static Response} lam bien thanh vien.</b> Kieu cu
 *       ({@code UiFramework/apis/BaseApi}) luu response cuoi cung vao mot bien static roi
 *       cac ham {@code shouldSee...()} doc lai bien do. Cach nay hong ngay khi chay song
 *       song: hai test ghi de response cua nhau va bao cao ket qua cua nguoi khac.
 *       O day moi ham API TRA VE {@code Response}, test tu giu lay.
 *   <li><b>Base URI doc duoc tu {@code -Dapi.base.uri}.</b> Jenkins hoac ban co the tro
 *       sang moi truong khac ma khong phai sua code, build lai.
 *   <li><b>{@link AllureRestAssured} gan san vao spec.</b> Moi request/response deu duoc
 *       ghi nguyen van vao Allure. Khi test do, ban doc duoc dung cai da gui va da nhan
 *       thay vi phai doan. Chinh nho filter nay ma tim ra duoc nguyen nhan cua
 *       {@code 418} mo ta ben duoi - neu khong thi con mo mam rat lau.
 * </ol>
 */
public class BaseApi {

  /** Restful Booker - API demo cong cong, co auth + CRUD day du, khong can dang ky. */
  private static final String DEFAULT_BASE_URI = "https://restful-booker.herokuapp.com";

  private static final String BASE_URI_PROPERTY = "api.base.uri";

  protected static final String BOOKING_ENDPOINT = "/booking";

  /**
   * Gia tri Accept BAT BUOC phai la mot chuoi duy nhat {@code application/json}.
   *
   * <p><b>Day la cai bay ton nhieu thoi gian nhat khi lam voi API nay - ghi lai de nguoi
   * sau khong dam lai.</b> Neu dung {@code RequestSpecBuilder.setAccept(ContentType.JSON)},
   * RestAssured bung header thanh bon gia tri:
   * <pre>Accept: application/json, application/javascript, text/javascript, text/json</pre>
   * Day la header hoan toan hop le theo RFC 9110, va {@code application/json} lai dung o
   * vi tri dau tien. Nhung Restful Booker khong parse Accept dung chuan - no so khop ca
   * chuoi, khong khop thi tra ve <b>{@code 418 I'm a Teapot}</b> kem body text/plain.
   *
   * <p>Trieu chung rat de danh lac huong: {@code POST /auth} van chay binh thuong (endpoint
   * do khong qua middleware kiem tra Accept), chi {@code /booking} moi do - nen de tuong
   * nham la loi xac thuc hoac bi chan rate limit.
   *
   * <p><b>Day la mot khiem khuyet cua API duoc test, khong phai cua framework</b> - da ghi
   * vao muc Defect trong {@code docs/capstone-test-report.md}. Test phai chay duoc tren he
   * thong thuc te dang co, nen o day ta thich ung theo no.
   */
  private static final String ACCEPT_JSON = "application/json";

  /**
   * Spec dung chung: base URI + Content-Type + Accept + filter ghi log vao Allure.
   */
  protected static RequestSpecification baseSpec() {
    return new RequestSpecBuilder()
        .setBaseUri(getBaseUri())
        .setContentType(ContentType.JSON)
        // Dat bang addHeader chu KHONG dung setAccept(ContentType.JSON) - xem ACCEPT_JSON.
        .addHeader("Accept", ACCEPT_JSON)
        .addFilter(new AllureRestAssured())
        .build();
  }

  /** @return base URI dang dung, uu tien {@code -Dapi.base.uri} neu co truyen. */
  public static String getBaseUri() {
    String override = System.getProperty(BASE_URI_PROPERTY);
    return override == null || override.isBlank() ? DEFAULT_BASE_URI : override.trim();
  }
}
