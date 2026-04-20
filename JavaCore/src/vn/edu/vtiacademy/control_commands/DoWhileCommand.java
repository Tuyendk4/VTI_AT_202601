package vn.edu.vtiacademy.control_commands;

public class DoWhileCommand {

  public static void main(String[] args) {
    int sum = 0;
    int i = 0;
    do {
      System.out.print("sum = " + sum + " + " + i + " = ");
      sum += i;
      System.out.print(sum);
      System.out.println();
      i++;
    } while(i <= 10);
  }
}
