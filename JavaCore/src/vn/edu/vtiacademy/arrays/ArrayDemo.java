package vn.edu.vtiacademy.arrays;

public class ArrayDemo {

  public static void main(String[] args) {
    int[] numbers = new int[10];
    for (int i = 0; i < numbers.length; i++) {
      numbers[i] = i + 1;
    }
    for (int i = 0; i < numbers.length; i++) {
      System.out.println("number[" + i + "] = " + numbers[i]);
    }

    for(int number: numbers) {
      System.out.println("number = " + number);
    }
  }
}
