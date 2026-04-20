package vn.edu.vtiacademy.control_commands;

public class ForCommand {

  public static void main(String[] args) {
    //Tính tổng từ 1 - 10;
    // sum = 1 + 2 + 3 +...+ 10
    int sum = 0;
    for (int i = 0; i <= 10; i++) {
      System.out.print("sum = " + sum + " + " + i + " = ");
      sum += i;
      System.out.print(sum);
      System.out.println();
    }
//    System.out.println("Sum from 1 to 10 is: " + sum);
//    int i = 0;
//    sum = 0;
//    for (; i <= 10; i++) {
//      System.out.print("sum = " + sum + " + " + i + " = ");
//      sum += i;
//      System.out.print(sum);
//      System.out.println();
//    }

//    for(;;) {
//      System.out.println("Hello");
//    }
  }
}
