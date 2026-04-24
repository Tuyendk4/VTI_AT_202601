package vn.edu.vtiacademy.vararg;

public class VarargDemo {

  public static void main(String[] args) {
    showInfo();
    System.out.println("--------------------------");
    showInfo("Hello");
    System.out.println("--------------------------");
    showInfo("Hello", "World", "!");
  }

  public static void showInfo(String... messages) { //arrays
    for (String message : messages) {
      System.out.println(message);
    }
  }

}
