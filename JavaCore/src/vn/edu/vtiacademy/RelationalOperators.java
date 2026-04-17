package vn.edu.vtiacademy;

import java.util.Scanner;

public class RelationalOperators {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Pls enter first number: ");
    int number1 = scanner.nextInt();
    System.out.println("Pls enter second number: ");
    int number2 = scanner.nextInt();
    System.out.println("Relational operators result:");
    System.out.println(number1 + " == " + number2 + " ==> " + (number1 == number2));
    System.out.println(number1 + " != " + number2 + " ==> " + (number1 != number2));
    System.out.println(number1 + " > " + number2 + " ==> " + (number1 > number2));
    System.out.println(number1 + " < " + number2 + " ==> " + (number1 < number2));
    System.out.println(number1 + " >= " + number2 + " ==> " + (number1 >= number2));
    System.out.println(number1 + " <= " + number2 + " ==> " + (number1 <= number2));
  }
}
