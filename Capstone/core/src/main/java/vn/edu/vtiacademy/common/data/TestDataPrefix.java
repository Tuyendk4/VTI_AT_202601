package vn.edu.vtiacademy.common.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Sinh chuoi danh dau duy nhat cho du lieu do test tao ra.
 *
 * <p><b>Tai sao can:</b> {@code dash-demo.workdo.io} la demo cong cong, du lieu dung chung
 * voi ca internet. Test khong duoc phep gia dinh bat cu ban ghi nao co san, va phai tim
 * lai duoc dung ban ghi CUA MINH giua hang tram ban ghi cua nguoi khac.
 *
 * <p><b>Cach dung tren WorkDo:</b> so hoa don (invoice number) la auto-generated - form
 * khong co o de nhap, nen KHONG nhet marker vao do duoc. Thay vao do:
 * <ol>
 *   <li>Nhet marker nay vao field <b>Notes</b> luc tao invoice</li>
 *   <li>Bat lay invoice number he thong sinh ra sau khi Create</li>
 *   <li>Dung invoice number do de search / verify / xoa (deep-link
 *       {@code /sales-invoices?search=<invoice_number>})</li>
 * </ol>
 *
 * <p>Marker trong Notes la luoi an toan: neu test chet giua chung va khong kip xoa,
 * ban van tim lai duoc rac cua minh de don tay.
 *
 * <p>Dinh dang: {@code AT_20260817143025_a1b2}
 * ({@value #PREFIX} + thoi diem tao + 4 ky tu ngau nhien).
 * Phan ngau nhien la de hai test chay song song trong cung mot giay khong trung nhau.
 */
public final class TestDataPrefix {

  /** Tien to co dinh - de loc rac do automation tao ra khoi du lieu that cua demo. */
  public static final String PREFIX = "AT_";

  private static final DateTimeFormatter TIMESTAMP_FORMAT =
      DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  private static final String RANDOM_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
  private static final int RANDOM_LENGTH = 4;

  private TestDataPrefix() {
    // Utility class - khong cho khoi tao.
  }

  /**
   * @return marker moi, vi du {@code AT_20260817143025_a1b2}
   */
  public static String newMarker() {
    return PREFIX + LocalDateTime.now().format(TIMESTAMP_FORMAT) + "_" + randomSuffix();
  }

  /**
   * Marker kem mo ta ngan de nguoi doc hieu ban ghi nay sinh ra tu dau.
   *
   * @param label mo ta ngan, thuong la ma test case (vi du {@code "INV001"})
   * @return vi du {@code AT_20260817143025_a1b2_INV001}
   */
  public static String newMarker(String label) {
    return newMarker() + "_" + label;
  }

  /**
   * Kiem tra mot chuoi co phai do automation sinh ra khong.
   * Dung khi don rac ton dong tren demo.
   */
  public static boolean isTestData(String value) {
    return value != null && value.startsWith(PREFIX);
  }

  private static String randomSuffix() {
    StringBuilder suffix = new StringBuilder(RANDOM_LENGTH);
    for (int i = 0; i < RANDOM_LENGTH; i++) {
      suffix.append(RANDOM_CHARS.charAt(ThreadLocalRandom.current().nextInt(RANDOM_CHARS.length())));
    }
    return suffix.toString();
  }
}
