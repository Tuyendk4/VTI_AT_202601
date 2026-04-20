package vn.edu.vtiacademy.control_commands;

import java.util.Scanner;

public class FunctionMehtod {

  public static void main(String[] args) {
    System.out.println("Enter a number: ");
    int number = inputNumber();
    printNumber(number);
    if (isPositive(number)) { // if number is positive
      System.out.println("This is positive number");
    } else if (isNegative(number)) { // else if number is negative
      System.out.println("This is negative number");
    } else {
      System.out.println("This is zero");
    }
    if(isEvenNumber(number)) {// ìf number is even number
      System.out.println("This is even number");
    } else {
      System.out.println("This is odd number");
    }
  }

  public static int inputNumber() {
    Scanner scanner = new Scanner(System.in);
    return scanner.nextInt();
  }

  public static void printNumber(int number) {
    System.out.println("Number is " +number);
  }

  public static boolean isPositive(int number) {
    return number > 0;
  }

  public static boolean isNegative(int number) {
    return number < 0;
  }

  public static boolean isOddNumber(int number) {
    return number % 2 != 0;
  }

  public static boolean isEvenNumber(int number) {
    if(number == 0) {
      return false;
    }
    return number % 2 == 0;
  }
}
