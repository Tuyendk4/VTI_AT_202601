package vn.edu.vtiacademy;

public class SpecialCharacters {

  public static void main(String[] args) {
    String str = "Hello \"World\"";
    String str1 = "Hello 'World'";
    System.out.println(str);
    System.out.println("Special characters:\tHello \"World\"");
    System.out.println("Special characters:\nHello \"World\"");
    System.out.println("Special characters:\bHello \"World\"");
    System.out.println("Special characters:\rHello \"World\"");
  }
}
