package vn.edu.vtiacademy.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vtiacademy.junit_demo.MathProvider;

public class MathProviderTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(MathProviderTest.class);

  @Test
  public void MP001_testSum() {
    LOGGER.info("Starting to execute test method: {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    MathProvider provider = new MathProvider();
    int result = provider.sum(10, 20);
    assertEquals(20, result); // best practice

    // bad practice
//    if(result == 20) {
//      LOGGER.info("Ended to execute test method: {}", Thread.currentThread().getStackTrace()[1].getMethodName());
//    } else {
//      LOGGER.error("Ended to execute test method: {}", Thread.currentThread().getStackTrace()[1].getMethodName());
//    }
    LOGGER.error("Ended to execute test method: {}", Thread.currentThread().getStackTrace()[1].getMethodName());
  }

  @Test
  public void MP002_testSub() {
    MathProvider provider = new MathProvider();
    int result = provider.sub(20, 10);
    assertEquals(10, result);
  }

  @Test
  public void MP003_testMul() {
    MathProvider provider = new MathProvider();
    int result = provider.mul(5, 6);
    assertEquals(30, result);
  }

  @Test
  public void MP004_testDiv() {
    vn.edu.vtiacademy.junit_demo.MathProvider provider = new vn.edu.vtiacademy.junit_demo.MathProvider();
    double result = provider.div(10, 2);
    assertEquals(5.0, result);
  }

}
