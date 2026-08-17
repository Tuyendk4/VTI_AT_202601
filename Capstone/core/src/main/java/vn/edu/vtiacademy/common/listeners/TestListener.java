package vn.edu.vtiacademy.common.listeners;

import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Chup man hinh khi test FAIL va dinh vao Allure report.
 *
 * <p>Day la thu bien mot report "test X do" thanh mot report noi duoc "test X do vi
 * man hinh luc do trong nhu the nay". Khi demo cho mentor, anh chup tai thoi diem fail
 * la bang chung manh nhat.
 *
 * <p>Duoc TestNG nap tu dong qua ServiceLoader
 * ({@code META-INF/services/org.testng.ITestNGListener}).
 *
 * <p>Luu y ve retry: {@link RetryAnalyzer} co the chay lai test. Moi lan fail deu
 * duoc chup, nen trong Allure se thay nhieu anh - dung y muon, vi no cho thay
 * test that su khong on dinh hay chi tach mot lan.
 */
public class TestListener implements ITestListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);

  @Override
  public void onTestFailure(ITestResult result) {
    String testName = result.getName();
    LOGGER.error("Test FAILED: {} - {}", testName,
        result.getThrowable() != null ? result.getThrowable().getMessage() : "khong ro nguyen nhan");
    attachScreenshot(result, "FAILED - " + testName);
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    LOGGER.warn("Test SKIPPED: {}", result.getName());
  }

  /**
   * Lay anh tu test instance neu no implement {@link ScreenshotCapable}.
   *
   * <p>Moi loi phat sinh khi chup deu bi nuot: neu browser da chet thi viec chup cung
   * se nem exception, va mot exception trong listener se che mat loi that cua test.
   * Bao cao sai nguyen nhan con te hon la thieu anh.
   */
  private void attachScreenshot(ITestResult result, String attachmentName) {
    Object testInstance = result.getInstance();
    if (!(testInstance instanceof ScreenshotCapable screenshotCapable)) {
      LOGGER.debug("{} khong implement ScreenshotCapable - bo qua viec chup man hinh",
          testInstance != null ? testInstance.getClass().getSimpleName() : "null");
      return;
    }
    try {
      byte[] screenshot = screenshotCapable.captureScreenshot();
      if (screenshot == null || screenshot.length == 0) {
        LOGGER.debug("Chua co driver de chup man hinh cho test '{}'", result.getName());
        return;
      }
      Allure.addAttachment(attachmentName, "image/png",
          new ByteArrayInputStream(screenshot), ".png");
    } catch (Exception e) {
      LOGGER.warn("Khong chup duoc man hinh cho test '{}'. Nguyen nhan: {}",
          result.getName(), e.getMessage());
    }
  }
}
