package vn.edu.vtiacademy.junit_demo;

import io.qameta.allure.Step;

public class MathProvider {

  @Step("first number + second number = {0} + {1}")
  public int sum(int a, int b) {
    return a + b;
  }

  @Step("first number - second number = {0} - {1}")
  public int sub(int a, int b) {
    return a - b;
  }

  @Step("first number * second number = {0} * {1}")
  public int mul(int a, int b) {
    return a * b;
  }

  @Step("first number / second number = {0} / {1}")
  public double div(int a, int b) {
    return (double) a / b;
  }

}
