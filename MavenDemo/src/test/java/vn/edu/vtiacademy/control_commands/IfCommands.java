package vn.edu.vtiacademy.control_commands;

import java.util.Scanner;

public class IfCommands {

  public static void main(String[] args) {
    // Nhập một số nguyên bất kỳ. Nếu số nguyên lớn hơn 0 thì hiển thị ra màn hình console: Đây là số nguyên dương
    Scanner scanner = new Scanner(System.in);
    System.out.println("Pls enter number: ");
    int number = scanner.nextInt();
    if (number > 0) {
      System.out.println("This is positive number");
    }
    System.out.println("Finished!");

    // Nhập một số nguyên bất kỳ. Nếu số nguyên lớn hơn 0 thì hiển thị ra màn hình console: Đây là số nguyên dương, nếu không thì hiển thị Đây là số nguyên âm
    number = scanner.nextInt();
    if (number > 0) {
      System.out.println("This is positive number");
    } else {
      System.out.println("This is negative number");
    }
    System.out.println("Finished!");

    // Nhập một số nguyên bất kỳ. Nếu số nguyên lớn hơn 0 thì hiển thị ra màn hình console: Đây là số nguyên dương, nếu số nguyên nhỏ hơn 0 thì hiển thị Đây là số nguyên âm
    // Nếu không thì in ra Đây là số 0
    number = scanner.nextInt();
    if (number > 0) {
      System.out.println("This is positive number");
    } else if (number < 0) {
      System.out.println("This is negative number");
    } else {
      System.out.println("This is zero");
    }
    System.out.println("Finished!");

    // Nhập một số nguyên bất kỳ. Nếu số nguyên lớn hơn 0 thì hiển thị ra màn hình console: Đây là số nguyên dương, Nếu số nguyên chia hết cho 2 thì in ra mành hình Đây là số chẵn
    number = scanner.nextInt();
    if (number > 0) {
      System.out.println("This is positive number");
      if(number % 2 == 0) {
        System.out.println("This is even number");
      } else {
        System.out.println("This is odd number");
      }
    } else if (number < 0) {
      System.out.println("This is negative number");
      if(number % 2 == 0) {
        System.out.println("This is even number");
      } else {
        System.out.println("This is odd number");
      }
    } else {
      System.out.println("This is zero");
    }
    scanner.close();
  }
}
