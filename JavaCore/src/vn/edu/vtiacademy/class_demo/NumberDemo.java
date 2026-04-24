package vn.edu.vtiacademy.class_demo;

public class NumberDemo {

  public static void main(String[] args) {
    Number number = new Number(10, 20);
    System.out.println("Sum: " + number.sum());
    System.out.println("Subtraction: " + number.subtraction());

    System.out.println("Pls enter first number: ");
    int firstNumber = number.inputNumber();
    System.out.println("Pls enter second number: ");
    int secondNumber = number.inputNumber();
    number = new Number(firstNumber, secondNumber);
    System.out.println("Sum: " + number.sum());
    System.out.println("Subtraction: " + number.subtraction());
  }
}
