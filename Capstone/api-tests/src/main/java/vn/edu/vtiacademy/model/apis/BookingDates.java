package vn.edu.vtiacademy.model.apis;

/**
 * Khoang ngay nhan/tra phong cua mot booking.
 *
 * <p>Restful Booker nhan va tra ngay duoi dang chuoi {@code yyyy-MM-dd}. Giu nguyen kieu
 * {@code String} thay vi {@code LocalDate} la co y: test API phai kiem tra dung cai
 * server tra ve, khong duoc de thu vien parse rui im lang "sua" gia tri sai thanh dung.
 */
public class BookingDates {

  private String checkin;
  private String checkout;

  /** Jackson can constructor rong de deserialize. */
  public BookingDates() {
  }

  public BookingDates(String checkin, String checkout) {
    this.checkin = checkin;
    this.checkout = checkout;
  }

  public String getCheckin() {
    return checkin;
  }

  public void setCheckin(String checkin) {
    this.checkin = checkin;
  }

  public String getCheckout() {
    return checkout;
  }

  public void setCheckout(String checkout) {
    this.checkout = checkout;
  }

  @Override
  public String toString() {
    return "BookingDates{checkin='" + checkin + "', checkout='" + checkout + "'}";
  }
}
