package vn.edu.vtiacademy.tests.apis;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import vn.edu.vtiacademy.apis.AuthApi;
import vn.edu.vtiacademy.apis.BookingApi;
import vn.edu.vtiacademy.model.apis.Booking;
import vn.edu.vtiacademy.model.apis.BookingFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Bo test API cho {@code /booking} cua Restful Booker.
 *
 * <p><b>Pham vi:</b> vong doi day du cua mot booking (tao - doc - sua - xoa) cong voi hai
 * case bao mat: dang nhap sai, va sua du lieu khi khong co token.
 *
 * <p><b>Nguyen tac viet o day - moi test tu tao du lieu cua rieng no.</b> Khong test nao
 * phu thuoc vao test truoc chay xong ({@code dependsOnMethods}). Doi lai la vai loi goi
 * API thua, nhung duoc: chay rieng le mot test bat ky van xanh, va mot test do khong keo
 * theo ca chuoi bao SKIP che mat van de that.
 *
 * <p>Chay: {@code mvn -pl api-tests test}
 */
@Epic("Capstone - API Layer")
@Feature("Restful Booker - Booking API")
public class BookingApiTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(BookingApiTest.class);

  private static final int HTTP_OK = 200;
  private static final int HTTP_CREATED = 201;
  private static final int HTTP_FORBIDDEN = 403;
  private static final int HTTP_NOT_FOUND = 404;

  private String token;

  /** Cac booking da tao trong luc chay, de don sach o {@link #cleanUpCreatedBookings()}. */
  private final List<Integer> createdBookingIds = new ArrayList<>();

  /**
   * Lay token MOI truoc moi test.
   *
   * <p><b>Vi sao khong lay mot lan trong {@code @BeforeClass}:</b> Restful Booker la he thong
   * demo cong cong va tu dat lai du lieu theo dinh ky - token dang cam co the het hieu luc giua
   * chung. Khi do cac thao tac ghi tra ve {@code 403} va bo test do voi thong bao trong het nhu
   * mot lo hong phan quyen, trong khi that ra chi la token cu. Da gap dung tinh huong nay: bo
   * test dang xanh bong do hai case PUT/DELETE ma khong sua gi.
   *
   * <p>Gia phai tra la them mot loi goi {@code POST /auth} (khoang mot giay) cho moi test -
   * re hon nhieu so voi mot bo test thinh thoang do vi ly do khong that.
   */
  @BeforeMethod(alwaysRun = true)
  public void obtainFreshToken() {
    token = AuthApi.getValidToken();
    assertNotNull(token, "Khong lay duoc token - moi test can quyen ghi deu se do. "
        + "Kiem tra mang hoac trang thai cua " + AuthApi.getBaseUri());
  }

  // ---------------------------------------------------------------------------------
  // AUTH
  // ---------------------------------------------------------------------------------

  @Test(description = "API_AUTH_01 - POST /auth voi tai khoan dung tra ve token")
  @Story("Xac thuc")
  @Severity(SeverityLevel.BLOCKER)
  @Description("Token la cua ngo cua moi thao tac ghi. Test nay do truoc thi cac test "
      + "PUT/DELETE phia sau khong con y nghia.")
  public void createToken_withValidCredentials_returnsToken() {
    Response response = AuthApi.createToken(AuthApi.VALID_USERNAME, AuthApi.VALID_PASSWORD);

    SoftAssert softly = new SoftAssert();
    softly.assertEquals(response.getStatusCode(), HTTP_OK, "HTTP status code");
    String actualToken = response.jsonPath().getString("token");
    softly.assertNotNull(actualToken, "Truong 'token' trong response body");
    // Restful Booker sinh token 15 ky tu. Kiem do dai de bat truong hop server tra ve
    // chuoi rong hoac thong bao loi ma van de status 200.
    softly.assertTrue(actualToken != null && !actualToken.isBlank(),
        "Token phai khac rong, thuc te: '" + actualToken + "'");
    softly.assertAll();
  }

  @Test(description = "API_AUTH_02 - POST /auth voi mat khau sai KHONG tra ve token")
  @Story("Xac thuc")
  @Severity(SeverityLevel.CRITICAL)
  @Description("Case bao mat. Luu y hanh vi la cua Restful Booker: no tra ve 200 kem "
      + "truong 'reason' chu khong phai 401 - day la mot diem thiet ke dang ngo cua API, "
      + "test ghi lai dung hanh vi THUC TE chu khong phai hanh vi mong muon.")
  public void createToken_withWrongPassword_returnsReasonInsteadOfToken() {
    Response response = AuthApi.createToken(AuthApi.VALID_USERNAME, "wrong_password_" + System.nanoTime());

    SoftAssert softly = new SoftAssert();
    softly.assertNull(response.jsonPath().getString("token"),
        "Sai mat khau ma van cap token - day la lo hong nghiem trong");
    softly.assertEquals(response.jsonPath().getString("reason"), "Bad credentials",
        "Truong 'reason' trong response body");
    softly.assertAll();
  }

  // ---------------------------------------------------------------------------------
  // CRUD
  // ---------------------------------------------------------------------------------

  @Test(description = "API_BOOK_01 - POST /booking tao thanh cong va tra ve dung du lieu da gui")
  @Story("Tao booking")
  @Severity(SeverityLevel.BLOCKER)
  public void createBooking_returnsCreatedBookingIdenticalToRequest() {
    Booking request = BookingFactory.validBooking();

    Response response = BookingApi.create(request);

    assertEquals(response.getStatusCode(), HTTP_OK, "HTTP status code khi tao booking");
    int bookingId = response.jsonPath().getInt("bookingid");
    rememberForCleanUp(bookingId);

    SoftAssert softly = new SoftAssert();
    softly.assertTrue(bookingId > 0, "bookingid phai la so duong, thuc te: " + bookingId);
    Booking created = response.jsonPath().getObject("booking", Booking.class);
    // So sanh CA doi tuong: bat duoc ca truong hop server nuot mat mot field.
    softly.assertEquals(created, request,
        "Booking server tra ve phai giong het booking da gui len");
    softly.assertAll();
  }

  @Test(description = "API_BOOK_02 - GET /booking/{id} tra ve dung ban ghi vua tao")
  @Story("Doc booking")
  @Severity(SeverityLevel.CRITICAL)
  public void getBookingById_returnsBookingThatWasCreated() {
    Booking request = BookingFactory.validBooking();
    int bookingId = createBookingAndGetId(request);

    Response response = BookingApi.getById(bookingId);

    assertEquals(response.getStatusCode(), HTTP_OK, "HTTP status code khi doc booking");
    Booking fetched = response.as(Booking.class);
    assertEquals(fetched, request,
        "Du lieu doc len phai giong du lieu da tao. Neu lech, server da bien doi du lieu luc luu");
  }

  @Test(description = "API_BOOK_03 - PUT /booking/{id} co token cap nhat duoc du lieu")
  @Story("Sua booking")
  @Severity(SeverityLevel.CRITICAL)
  public void updateBooking_withValidToken_persistsNewData() {
    int bookingId = createBookingAndGetId(BookingFactory.validBooking());
    Booking updated = BookingFactory.updatedBooking();

    Response response = BookingApi.update(bookingId, updated, token);

    assertEquals(response.getStatusCode(), HTTP_OK, "HTTP status code khi cap nhat");
    assertEquals(response.as(Booking.class), updated,
        "Response cua PUT phai phan anh du lieu moi");

    // Doc lai bang GET: response cua PUT co the "dung" ma du lieu chua thuc su duoc luu.
    // Chi GET moi chung minh duoc no da vao kho.
    assertEquals(BookingApi.getById(bookingId).as(Booking.class), updated,
        "Doc lai sau khi PUT phai ra du lieu moi - neu ra du lieu cu thi PUT khong luu that");
  }

  @Test(description = "API_BOOK_04 - PUT /booking/{id} KHONG co token bi tu choi 403")
  @Story("Sua booking")
  @Severity(SeverityLevel.BLOCKER)
  @Description("Case bao mat quan trong nhat cua bo API test: API cho phep sua du lieu ma "
      + "khong can xac thuc la lo hong nghiem trong. Test con kiem tra du lieu KHONG bi doi.")
  public void updateBooking_withoutToken_isRejectedAndDataUnchanged() {
    Booking original = BookingFactory.validBooking();
    int bookingId = createBookingAndGetId(original);

    Response response = BookingApi.updateWithoutToken(bookingId, BookingFactory.updatedBooking());

    SoftAssert softly = new SoftAssert();
    softly.assertEquals(response.getStatusCode(), HTTP_FORBIDDEN,
        "Sua khong token phai bi tu choi bang 403");
    // Chan bang status code thoi chua du - phai chung minh du lieu that su khong doi.
    softly.assertEquals(BookingApi.getById(bookingId).as(Booking.class), original,
        "Du lieu phai giu nguyen sau mot request khong duoc phep");
    softly.assertAll();
  }

  @Test(description = "API_BOOK_05 - DELETE /booking/{id} xoa han ban ghi")
  @Story("Xoa booking")
  @Severity(SeverityLevel.CRITICAL)
  public void deleteBooking_removesItPermanently() {
    int bookingId = createBookingAndGetId(BookingFactory.validBooking());

    Response deleteResponse = BookingApi.delete(bookingId, token);

    SoftAssert softly = new SoftAssert();
    // Restful Booker tra ve 201 cho DELETE thanh cong (khong phai 200/204 nhu thong le).
    softly.assertEquals(deleteResponse.getStatusCode(), HTTP_CREATED,
        "HTTP status code khi xoa booking");
    softly.assertEquals(BookingApi.getById(bookingId).getStatusCode(), HTTP_NOT_FOUND,
        "Doc lai ban ghi da xoa phai ra 404 - neu van 200 thi xoa chua that su xay ra");
    softly.assertAll();

    // Da xoa roi thi khong can don nua.
    createdBookingIds.remove(Integer.valueOf(bookingId));
  }

  // ---------------------------------------------------------------------------------
  // Helper + don du lieu
  // ---------------------------------------------------------------------------------

  /**
   * Tao mot booking va tra ve id, dong thoi ghi lai de don sau.
   *
   * <p>Dung {@code assertTrue} thay vi de test chay tiep: neu buoc chuan bi du lieu that
   * bai thi phan assert chinh cua test se do voi thong bao vo nghia (vi du "bookingid = 0
   * khong ton tai"), rat mat cong truy nguyen nhan.
   */
  private int createBookingAndGetId(Booking booking) {
    Response response = BookingApi.create(booking);
    assertEquals(response.getStatusCode(), HTTP_OK,
        "Buoc chuan bi du lieu that bai - khong tao duoc booking de test");
    int bookingId = response.jsonPath().getInt("bookingid");
    assertTrue(bookingId > 0, "Buoc chuan bi du lieu that bai - bookingid khong hop le");
    rememberForCleanUp(bookingId);
    return bookingId;
  }

  private void rememberForCleanUp(int bookingId) {
    createdBookingIds.add(bookingId);
  }

  /**
   * Xoa moi booking do bo test nay tao ra.
   *
   * <p>Restful Booker la he thong dung chung. Test khong don rac cua minh se lam ban du
   * lieu cua nguoi khac va cua chinh lan chay sau. Moi loi khi xoa deu bi nuot: don rac
   * that bai khong duoc phep lam do mot bo test da chay xong.
   */
  @AfterClass(alwaysRun = true)
  public void cleanUpCreatedBookings() {
    // Lay token moi cho rieng buoc don dep: token dung trong luc chay test co the da het han.
    String cleanUpToken = AuthApi.getValidToken();
    if (cleanUpToken == null) {
      LOGGER.warn("Khong lay duoc token - bo qua buoc don du lieu, {} booking se con lai tren server",
          createdBookingIds.size());
      return;
    }
    for (Integer bookingId : createdBookingIds) {
      try {
        BookingApi.delete(bookingId, cleanUpToken);
      } catch (Exception e) {
        LOGGER.warn("Khong xoa duoc booking {}. Nguyen nhan: {}", bookingId, e.getMessage());
      }
    }
    LOGGER.info("Da don {} booking do test tao ra", createdBookingIds.size());
    createdBookingIds.clear();
  }
}
