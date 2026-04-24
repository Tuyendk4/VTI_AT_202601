package vn.edu.vtiacademy.arrays;

public class TwoWayArray {

  public static void main(String[] args) {
    int[][] table = new int[3][4];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        table[i][j] = (i*4) + j + 1;
        System.out.print(table[i][j] + " ");
      }
      System.out.println();
    }

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        System.out.println("table[" + i + "][" + j + "] = " + table[i][j]);
        // table[0][0] = [1 2 3 4][0] = 1
        // table[0][1] = [1 2 3 4][1] = 2
        // table[0][2] = [1 2 3 4][2] = 3
      }
    }
  }

}
