package vn.edu.vtiacademy.exceptions;

public class ExceptionDemo {

  public static void main(String[] args) {
    int[] numbers = {1, 2, 3};
    try {
      System.out.println(numbers[5]);
    } catch (Exception e) {
      System.out.println("Error: Index is out of bounds!");
      System.out.println(e.getMessage());
    } finally {
      System.out.println("Execution completed.");
    }

    try {
      int result = divide(10, 0);
      System.out.println("Result: " + result);
//      String message = null;
//      System.out.println(message.length());
    } catch (ArithmeticException e) {
      System.out.println("Error: Cannot divide by zero!");
    }
  }

  public static int divide(int a, int b) {
//    if (b == 0) return 0;
    return a / b;
  }


}
