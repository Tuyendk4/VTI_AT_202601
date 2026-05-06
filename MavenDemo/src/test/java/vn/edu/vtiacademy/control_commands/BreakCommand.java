package vn.edu.vtiacademy.control_commands;

public class BreakCommand {

  public static void main(String[] args) {
    for (int i = 1; i <= 10; i++) {
      if (i == 5) {
        System.out.println("Break at i = " + i);
        break;
      }
      System.out.println("i = " + i);
    }
    System.out.println("Finished!");
  }

}
