package vn.edu.vtiacademy.enum_demo;

public enum WeekDays {
  MONDAY("Monday"),
  TUESDAY("Tuesday"),
  WEDNESDAY("Wednesday"),
  THURSDAY("Thursday"),
  FRIDAY("Friday"),
  SATURDAY("Saturday"),
  SUNDAY("Sunday");

  private final String weedDays;

  WeekDays(String weedDays){
    this.weedDays = weedDays;
  }

  public String getNameOfIts(){
    return this.weedDays;
  }
}
