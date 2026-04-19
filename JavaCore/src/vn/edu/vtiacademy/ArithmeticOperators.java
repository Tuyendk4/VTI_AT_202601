package vn.edu.vtiacademy;

public class ArithmeticOperators {

  public static void main(String[] args) {
    int number1 = 10;
    int number2 = 20;

    int sum = number1 + number2;
    System.out.println("sum = " + number1 + " + " + number2 + " = " + sum);
    int subtraction = number1 - number2;
    System.out.println("subtraction = " + number1 + " - " + number2 + " = " + subtraction);

    int multiplication = number1 * number2;
    System.out.println("multiplication = " + number1 + " * " + number2 + " = " + multiplication);
    int division = number1 / number2;
    System.out.println("division = " + number1 + " / " + number2 + " = " + division);
    int remainder = number1 % number2;
    System.out.println("remainder = " + number1 + " % " + number2 + " = " + remainder);

    System.out.println("number1++ = " + number1++); //number1 = number1 + 1
    System.out.println("number1 = " + number1);
    System.out.println("number2-- = " + number2--); //number2 = number2 - 1
    System.out.println("number2 = " + number2);
    System.out.println("++number1 = " + ++number1);
    System.out.println("--number2 = " + --number2);
  }
}
