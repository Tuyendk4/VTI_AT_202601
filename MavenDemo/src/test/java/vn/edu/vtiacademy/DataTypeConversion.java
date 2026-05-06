package vn.edu.vtiacademy;

import java.util.Scanner;

public class DataTypeConversion {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Pls enter first number: ");
    int firstNumber = scanner.nextInt();
    System.out.println("Pls enter second number: ");
    int secondNumber = scanner.nextInt();
    double result = (double) firstNumber / secondNumber;
    System.out.println("result = " + result);
    int intResult = (int) result;
    System.out.println("intResult = " + intResult);
    scanner.close();
  }
}
