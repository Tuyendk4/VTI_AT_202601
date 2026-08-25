package vn.edu.vtiacademy.model.apis;

import vn.edu.vtiacademy.common.data.TestDataPrefix;

/**
 * Sinh du lieu booking cho test.
 *
 * <p><b>Tai sao khong hardcode "Jim Brown":</b> Restful Booker la API demo cong cong,
 * hang tram nguoi dang goi vao cung luc va du lieu bi reset dinh ky. Neu test dat ten co
 * dinh thi khong the phan biet ban ghi cua minh voi cua nguoi khac, va test se doc nham
 * du lieu khi verify. Moi ban ghi vi vay deu mang marker {@code AT_<timestamp>_<random>}
 * lay tu {@link TestDataPrefix} cua module {@code core} - dung dung cai marker ma web
 * layer dang dung, khong viet lai (DRY).
 */
public final class BookingFactory {

  private BookingFactory() {
    // Utility class.
  }

  /** Booking hop le, day du field, ten mang marker duy nhat. */
  public static Booking validBooking() {
    return new Booking(
        TestDataPrefix.newMarker("FIRST"),
        TestDataPrefix.newMarker("LAST"),
        150,
        true,
        new BookingDates("2026-09-01", "2026-09-05"),
        "Breakfast");
  }

  /**
   * Bien the cua mot booking de dung cho PUT - doi ten, gia va nhu cau them.
   *
   * <p>Doi NHIEU field cung luc la co y: neu API chi cap nhat mot phan (vi du nuot mat
   * {@code additionalneeds}), phep so sanh ca doi tuong se bat duoc ngay.
   */
  public static Booking updatedBooking() {
    return new Booking(
        TestDataPrefix.newMarker("UPDFIRST"),
        TestDataPrefix.newMarker("UPDLAST"),
        999,
        false,
        new BookingDates("2026-10-10", "2026-10-20"),
        "Late checkout");
  }
}
