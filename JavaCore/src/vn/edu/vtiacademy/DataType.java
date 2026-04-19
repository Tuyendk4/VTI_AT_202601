package vn.edu.vtiacademy;

public class DataType {

  public static void main(String[] args) {
    byte byteNumber = 10;
    short shortNumber = 10;
    int integerNumber = 10;
    long longNumber = 10;
    float floatNumber = 10;
    double doubleNumber = 10;

    System.out.println("byte: " + byteNumber);
    System.out.println("short: " + shortNumber);
    System.out.println("int: " + integerNumber);
    System.out.println("long: " + longNumber);
    System.out.println("float: " + floatNumber);
    System.out.println("double: " + doubleNumber);

    boolean isTrue = true;
    System.out.println("isTrue: " + isTrue);
    isTrue = false;
    System.out.println("isTrue: " + isTrue);

    char character = 'A';
    System.out.println("character: " + character);

    character = 234;
    System.out.println("character: " + character);

    String firstName = "John";
    String lastName = "Doe";
    String fullName = firstName + " " + lastName;
    System.out.println("fullName: " + fullName);
  }
}
