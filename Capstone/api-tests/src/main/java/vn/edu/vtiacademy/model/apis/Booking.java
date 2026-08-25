package vn.edu.vtiacademy.model.apis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

/**
 * Mot booking cua Restful Booker.
 *
 * <p><b>Tai sao dung POJO ma khong noi chuoi JSON bang tay:</b> khi test tao body bang
 * {@code "{\"firstname\":\"" + name + "\"}"} thi mot dau ngoac thieu chi lo ra luc chay,
 * va khong ai kiem duoc kieu du lieu. POJO cho compiler bat loi ngay, va cho phep so sanh
 * ca doi tuong bang {@link #equals(Object)} thay vi assert tung field mot.
 *
 * <p>{@code @JsonIgnoreProperties(ignoreUnknown = true)} de server them field moi cung
 * khong lam do test - test API khong nen vo vi mot field ma no khong quan tam.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking {

  private String firstname;
  private String lastname;
  private Integer totalprice;
  private Boolean depositpaid;
  private BookingDates bookingdates;
  private String additionalneeds;

  /** Jackson can constructor rong de deserialize. */
  public Booking() {
  }

  public Booking(String firstname, String lastname, Integer totalprice, Boolean depositpaid,
      BookingDates bookingdates, String additionalneeds) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.totalprice = totalprice;
    this.depositpaid = depositpaid;
    this.bookingdates = bookingdates;
    this.additionalneeds = additionalneeds;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public Integer getTotalprice() {
    return totalprice;
  }

  public void setTotalprice(Integer totalprice) {
    this.totalprice = totalprice;
  }

  public Boolean getDepositpaid() {
    return depositpaid;
  }

  public void setDepositpaid(Boolean depositpaid) {
    this.depositpaid = depositpaid;
  }

  public BookingDates getBookingdates() {
    return bookingdates;
  }

  public void setBookingdates(BookingDates bookingdates) {
    this.bookingdates = bookingdates;
  }

  public String getAdditionalneeds() {
    return additionalneeds;
  }

  public void setAdditionalneeds(String additionalneeds) {
    this.additionalneeds = additionalneeds;
  }

  /**
   * So sanh theo TOAN BO field nghiep vu.
   *
   * <p>Dung trong assert "cai server luu lai co dung cai minh gui len khong". So sanh ca
   * doi tuong bat duoc loi ma assert tung field de bo sot - vi du server nuot mat
   * {@code additionalneeds} thi chi co phep so sanh day du moi phat hien.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Booking other)) {
      return false;
    }
    return Objects.equals(firstname, other.firstname)
        && Objects.equals(lastname, other.lastname)
        && Objects.equals(totalprice, other.totalprice)
        && Objects.equals(depositpaid, other.depositpaid)
        && Objects.equals(additionalneeds, other.additionalneeds)
        && Objects.equals(datesOf(this), datesOf(other));
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, lastname, totalprice, depositpaid, additionalneeds,
        datesOf(this));
  }

  /** Gop bookingdates thanh chuoi de so sanh - {@link BookingDates} khong override equals. */
  private static String datesOf(Booking booking) {
    return booking.bookingdates == null ? null : booking.bookingdates.toString();
  }

  @Override
  public String toString() {
    return "Booking{firstname='" + firstname + "', lastname='" + lastname
        + "', totalprice=" + totalprice + ", depositpaid=" + depositpaid
        + ", bookingdates=" + bookingdates + ", additionalneeds='" + additionalneeds + "'}";
  }
}
