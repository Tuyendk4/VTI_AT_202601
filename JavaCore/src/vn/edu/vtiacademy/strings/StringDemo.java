package vn.edu.vtiacademy.strings;

public class StringDemo {

  public static void main(String[] args) {
//    String name = "John";
    String greeting = "Welcome to Java Programming!";
    String empty = "";
    String nullString = null;
    String space = " "; //blank
    System.out.println(greeting);
    System.out.println("Length of greeting: " + getLengthOf(greeting));
    System.out.println(greeting.concat("Hello world")); //Welcome to Java Programming!Hello world
    System.out.println(greeting); //Welcome to Java Programming!Hello world
    String appendedGreeting = append(greeting, " and have a great day!");
    System.out.println(appendedGreeting);
    char foundChar = findCharacterAtIndex(greeting, 5);
    System.out.println("Character at index 5: " + foundChar);
  }

  public static int getLengthOf(String str) { // get length of string
    return str.length();
  }

  public static String append(String str, String appendedString) {
    return str.concat(appendedString);
  }

  public static char findCharacterAtIndex(String sourceString, int index) {
    if (sourceString == null || index < 0 || index >= sourceString.length()) {
      throw new IllegalArgumentException("Invalid input");
    }
    return sourceString.charAt(index);
  }
}
