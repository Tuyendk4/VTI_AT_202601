package vn.edu.vtiacademy.control_commands;

public class ContinueCommand {

  public static void main(String[] args) {
    for (int i = 1; i <= 10; i++) {
      if (i == 5) {
//        System.out.println("Continue");
        continue;
      }
      System.out.println("i = " + i);
    }
    System.out.println("Finished!");
  }

}
