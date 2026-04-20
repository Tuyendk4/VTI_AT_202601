package vn.edu.vtiacademy.control_commands;

public class WhileCommand {

  public static void main(String[] args) {
    int sum = 0;
    int i = 0; // khởi tạo bước lặp
    while (i <= 10) { // điều kiện kết thúc vòng lặp
      System.out.print("sum = " + sum + " + " + i + " = ");
      sum += i;
      System.out.print(sum);
      System.out.println();
      i++; // tăng giá trị bước lặp
    }
  }
}
