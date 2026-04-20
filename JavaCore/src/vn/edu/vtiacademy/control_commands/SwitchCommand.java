package vn.edu.vtiacademy.control_commands;

import java.util.Scanner;

public class SwitchCommand {

  public static void main(String[] args) {
    //Nhập số nguyên nằm trong khoảng từ 1 - 7. In ra thứ trong tuần tương ứng
    Scanner scanner = new Scanner(System.in);
    System.out.println("Pls enter number: ");
    int number = scanner.nextInt();
    switch (number) {
      case 1:
        System.out.println("Monday");
        break;
      case 2:
        System.out.println("Tuesday");
        break;
      case 3:
        System.out.println("Wednesday");
        break;
      case 4:
        System.out.println("Thursday");
        break;
      case 5:
        System.out.println("Friday");
        break;
      case 6:
        System.out.println("Saturday");
        break;
      case 7:
        System.out.println("Sunday");
        break;
      default:
        System.out.println("Invalid number!");
    }

    if (number == 1) {
      System.out.println("Monday");
    } else if (number == 2) {
      System.out.println("Tuesday");
    } else if (number == 3) {
      System.out.println("Wednesday");
    } else if (number == 4) {
      System.out.println("Thursday");
    } else if (number == 5) {
      System.out.println("Friday");
    } else if (number == 6) {
      System.out.println("Saturday");
    } else if (number == 7) {
      System.out.println("Sunday");
    } else {
      System.out.println("Invalid number!");
    }

    switch (number) {
      case 1:
      case 3:
      case 5:
      case 7:
        System.out.println("This is odd number");
        break;
      case 2:
      case 4:
      case 6:
        System.out.println("This is even number");
        break;
      default:
        System.out.println("Invalid number!");
    }
  }
}
