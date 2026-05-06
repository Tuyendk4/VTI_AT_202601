package vn.edu.vtiacademy;

public class LogiicOperators {

  public static void main(String[] args) {
    boolean isTrue = true;
    boolean isFalse = false;

    System.out.println("Logical operators result:");
    System.out.println(isTrue + " && " + isFalse + " = " + (isTrue && isFalse));
    System.out.println(isTrue + " || " + isFalse + " = " + (isTrue || isFalse));
    System.out.println("!" + isTrue + " = " + (!isTrue));
  }
}
