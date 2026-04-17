package vn.edu.vtiacademy;

public class Variable {

  public static void main(String[] args) {
    int number;
    number = 10;
    System.out.println("number = " + number);

    int number2 = 20;
    System.out.println("number2 = " + number2);

    number = number2;
    System.out.println("number = " + number);

    String firstName = "John";
    String lastName = "Doe";
    String fullName = firstName + " " + lastName;
    System.out.println("fullName = " + fullName);
  }
}
