package vn.edu.vtiacademy.common.listeners;

/**
 * Hop dong giua test class va {@link TestListener}.
 *
 * <p>{@code TestListener} chi nhan duoc {@code ITestResult}, no khong biet test dang
 * dung {@code WebUI} (web) hay {@code MobileUI} (mobile) - hai class nay lai co ten
 * ham chup man hinh khac nhau ({@code attachmentScreenshot()} vs {@code takeScreenshot()}).
 *
 * <p>BaseTest cua tung module implement interface nay va tu goi dung ham cua minh.
 * Nho vay listener dung chung duoc cho ca web lan mobile ma khong can biet gi ve driver.
 */
public interface ScreenshotCapable {

  /**
   * @return anh PNG duoi dang byte[], hoac {@code null} neu chua co driver
   *     (vi du test fail ngay trong {@code @BeforeMethod} truoc khi mo browser).
   */
  byte[] captureScreenshot();
}
