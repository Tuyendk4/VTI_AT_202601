package vn.edu.vtiacademy.collections.array_list;

import java.util.ArrayList;

public class ArrayListDemo {

  public static void main(String[] args) {
    ArrayList<Integer> list = new ArrayList<>();
    list.add(50);
    list.add(Integer.valueOf("70"));
    addIntegerItem(list, 10);
    addIntegerItem(list, "20");
    addIntegerItem(list, "30");
    addIntegerItem(list, "dsfsfdsf");
    addIntegerItem(list, null);
    showItems(list);
    insertIntegerItem(list, 1, 100);
    insertIntegerItem(list, 50, 200);

    showItems(list);

    ArrayList<String> stringList = new ArrayList<>();
    addStringItem(stringList, "10");
    addStringItem(stringList, "20");
    addStringItem(stringList, null);
    showStringItems(stringList);
  }

  public static void addIntegerItem(ArrayList<Integer> list, int item) {
    list.add(item);
  }

  public static void addIntegerItem(ArrayList<Integer> list, String item) {
    if(item == null) {
      System.err.println("Invalid input. The item is null");
      return;
    }
    if(!item.matches("\\d+")) {
      System.err.println("Invalid input. The item " + item + " is not a number");
      return;
    }
    list.add(Integer.parseInt(item));
  }

  public static void insertIntegerItem(ArrayList<Integer> list, int index, int item) {
//    if(index < 0 || index > list.size()) {
//      System.err.println("Invalid input. The index is out of range");
//      return;
//    }
//    list.add(index, item);

    try {
      list.add(index, item);
    } catch (IndexOutOfBoundsException e) {
      System.err.println("Invalid input. The index is out of range");
    }
  }

  public static void addStringItem(ArrayList<String> list, String item) {
    try {
      if(!item.matches("\\d+")) {
        System.err.println("Invalid input. The item " + item + " is not a number");
        return;
      }
      list.add(item);
    } catch (NullPointerException e) {
      System.err.println("Invalid input. The item is null");
    }
  }

  public static void showItems(ArrayList<Integer> list) {
    System.out.println("List items:");
    for(Integer item: list) {
      System.out.println(item);
    }
  }

  public static void showStringItems(ArrayList<String> list) {
    System.out.println("List items:");
    for(String item: list) {
      System.out.println(item);
    }
  }

}
