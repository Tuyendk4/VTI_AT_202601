package vn.edu.vtiacademy.enum_demo;

public class EnumDemo {

  public static void main(String[] args) {
    for (WeekDays weekDay : WeekDays.values()) {
      System.out.println(weekDay + " = " + weekDay.getNameOfIts());
      //System.out.println(weekDay.getNameOf());
    }
  }

}
