package vn.edu.vtiacademy.test;

import java.lang.reflect.Method;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LifeCycleDemo {

  private static final Logger LOGGER = LoggerFactory.getLogger(LifeCycleDemo.class);

//  @BeforeAll
//  public static void setup() {
//    LOGGER.info("===Starting Test suite");
//  }

  @BeforeEach
  public void setupTestScript() {
    LOGGER.info("=========Starting Test case {}", Thread.currentThread().getStackTrace()[1].getMethodName());
  }

  @Test
  public void test01() {
    LOGGER.info("==============Execute test01");
  }

  @Test
  public void test02() {
    LOGGER.info("==============Execute test02");
  }

  @Test
  public void test03() {
    LOGGER.info("==============Execute test03");
  }

  @AfterEach
  public void teardownTestScript() {
    LOGGER.info("=========Ending Test case {}", Thread.currentThread().getStackTrace()[1].getMethodName());
  }

//  @AfterAll
//  public static void teardown() {
//    LOGGER.info("===Ending Test suite");
//  }

}
