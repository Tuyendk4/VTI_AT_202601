package vn.edu.vtiacademy;

public class AssignmentOperators {

  public static void main(String[] args) {
    int number = 10;
    int temp = number;
    number += 10;
    System.out.println("number += 10 => " + temp + " += 10 = " + number);
    number -= 5;
    System.out.println("number -= 5 = " + number);
    number *= 2;
    System.out.println("number *= 2 = " + number);
    number /= 2;
    System.out.println("number /= 2 = " + number);
    number %= 2;
    System.out.println("number %= 2 = " + number);

    // Windows: CTRL + ALt + Shift + L: format code
    // MacOS: COMMAND + Alt + Shift + L: format code
  }
}
