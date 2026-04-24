package vn.edu.vtiacademy.class_demo;

import java.util.Scanner;

public class Number {

  private int firstNumber;
  private int secondNumber;

  Number(int firstNumber, int secondNumber) {
    this.firstNumber = firstNumber;
    this.secondNumber = secondNumber;
  }

  public int inputNumber() {
    Scanner scanner = new Scanner(System.in);
    return scanner.nextInt();
  }

  public void printNumber(int number) {
    System.out.println("Number is " +number);
  }

  public boolean isPositive(int number) {
    return number > 0;
  }

  public boolean isNegative(int number) {
    return number < 0;
  }

  public boolean isOddNumber(int number) {
    return number % 2 != 0;
  }

  public boolean isEvenNumber(int number) {
    if(number == 0) {
      return false;
    }
    return number % 2 == 0;
  }

  public int sum() {
    return firstNumber + secondNumber;
  }

  public int subtraction() {
    return firstNumber - secondNumber;
  }

}
