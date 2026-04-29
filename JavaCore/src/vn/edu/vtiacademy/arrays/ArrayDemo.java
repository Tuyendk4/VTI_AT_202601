package vn.edu.vtiacademy.arrays;

public class ArrayDemo {

  public static void main(String[] args) {
    int[] numbers = new int[10];
    System.out.println("Size of numbers: " + numbers.length);
    for (int i = 0; i < numbers.length; i++) {
      numbers[i] = i + 1;
    }
    for (int i = 0; i < numbers.length; i++) {
      System.out.println("number[" + i + "] = " + numbers[i]);
    }

    for(int number: numbers) {
      System.out.println("number = " + number);
    }

    int[] secondNumbers = {1, 2, 3, 4, 5};
    System.out.println("Size of secondNumbers: " + secondNumbers.length);
    for(int number: secondNumbers) {
      System.out.println("number = " + number);
    }
  }
}
