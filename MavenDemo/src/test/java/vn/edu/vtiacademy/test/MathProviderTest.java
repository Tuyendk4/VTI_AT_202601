package vn.edu.vtiacademy.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vtiacademy.junit_demo.MathProvider;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MathProviderTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(MathProviderTest.class);
  private static MathProvider provider;

  @BeforeAll
  public static void setup() {
    LOGGER.info("===Starting Test suite");
    provider = new MathProvider(); // local variable
  }

  private static Stream<Integer> dataMP001() {
    return Stream.of(10, 20, 30);
  }

  @ParameterizedTest
  @MethodSource("dataMP001")
  @DisplayName("MP001: Test sum method")
  @Order(1)
  public void MP001_testSum(int params) {
    LOGGER.info("Starting to execute test method: {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    int result = provider.sum(params, params);
    assertEquals(params * 2, result); // best practice

    // bad practice
//    if(result == 20) {
//      LOGGER.info("Ended to execute test method: {}", Thread.currentThread().getStackTrace()[1].getMethodName());
//    } else {
//      LOGGER.error("Ended to execute test method: {}", Thread.currentThread().getStackTrace()[1].getMethodName());
//    }
    LOGGER.error("Ended to execute test method: {}", Thread.currentThread().getStackTrace()[1].getMethodName());
  }

  @ParameterizedTest
  @ValueSource(ints = {10, 20, 30})
  @DisplayName("MP002: Test sub method")
  @Order(3)
  @Disabled
  public void MP002_testSub(int params) {
//    MathProvider provider = new MathProvider();
    int result = provider.sub(params, params);
    assertEquals(0, result);
  }

  @ParameterizedTest
  @CsvSource({"10,20,200", "34,56,1904"})
  @Order(2)
  public void MP003_testMul(int firstNumber, int secondNumber, int expectedResult) {
//    MathProvider provider = new MathProvider();
    int result = provider.mul(firstNumber, secondNumber);
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
  public void MP004_testDiv(int firstNumber, int secondNumber, double expectedResult) {
//    MathProvider provider = new MathProvider();
    double result = provider.div(firstNumber, secondNumber);
    assertEquals(expectedResult, result);
  }

}
