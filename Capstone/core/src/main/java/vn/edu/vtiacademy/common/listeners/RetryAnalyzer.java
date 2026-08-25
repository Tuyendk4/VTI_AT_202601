package vn.edu.vtiacademy.common.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Chay lai mot test da fail toi da {@value #DEFAULT_MAX_RETRY} lan.
 *
 * <p>Ly do can retry: he thong duoi test ({@code dash-demo.workdo.io}) la demo cong cong,
 * dung chung voi ca internet - do tre mang va dong tranh du lieu gay fail ngau nhien.
 * Retry giup phan biet "fail that" (fail ca 2 lan) voi "flaky" (fail roi pass).
 *
 * <p>Retry KHONG phai cai de che loi that. Neu mot test can retry deu dan moi lan chay
 * thi phai sua wait/locator cua test do, hoac ha xuong manual-only trong Test Case Excel.
 *
 * <p>So lan retry chinh duoc qua system property, vi du {@code mvn test -Dretry.count=2}.
 * Dat {@code -Dretry.count=0} de tat han (dung khi debug de thay loi that ngay).
 *
 * <p>TestNG tao mot instance analyzer rieng cho moi test method, nen bien dem
 * {@link #attempts} la an toan ke ca khi chay song song.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

  private static final Logger LOGGER = LoggerFactory.getLogger(RetryAnalyzer.class);

  /** So lan chay lai mac dinh khi khong truyen -Dretry.count. */
  private static final int DEFAULT_MAX_RETRY = 1;

  private static final String RETRY_COUNT_PROPERTY = "retry.count";

  private final int maxRetry = readMaxRetryFromSystemProperty();

  private int attempts = 0;

  @Override
  public boolean retry(ITestResult result) {
    if (attempts >= maxRetry) {
      return false;
    }
    attempts++;
    LOGGER.warn("Test '{}' FAILED - chay lai lan {}/{}",
        result.getName(), attempts, maxRetry);
    return true;
  }

  /**
   * Doc {@code -Dretry.count}. Gia tri khong hop le (khong phai so, hoac am)
   * se bi bo qua va dung mac dinh - de mot lan go nham khong lam hong ca suite.
   */
  private static int readMaxRetryFromSystemProperty() {
    String rawValue = System.getProperty(RETRY_COUNT_PROPERTY);
    if (rawValue == null || rawValue.isBlank()) {
      return DEFAULT_MAX_RETRY;
    }
    try {
      int parsed = Integer.parseInt(rawValue.trim());
      return parsed >= 0 ? parsed : DEFAULT_MAX_RETRY;
    } catch (NumberFormatException e) {
      LOGGER.warn("Gia tri -D{}='{}' khong hop le, dung mac dinh {}",
          RETRY_COUNT_PROPERTY, rawValue, DEFAULT_MAX_RETRY);
      return DEFAULT_MAX_RETRY;
    }
  }
}
