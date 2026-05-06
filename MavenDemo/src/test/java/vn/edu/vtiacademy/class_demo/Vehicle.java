package vn.edu.vtiacademy.class_demo;

public class Vehicle {

  private String vehicleName;
  private int passengers;
  private int fuelCap;
  private int mpg;

  Vehicle() {
    vehicleName = "Default";
    passengers = 5;
    fuelCap = 16;
    mpg = 21;
  }

  Vehicle(String vehicleName, int passengers, int fuelCap, int mpg) {
    this.vehicleName = vehicleName;
    this.passengers = passengers;
    this.fuelCap = fuelCap;
    this.mpg = mpg;
  }

  int range() {
//    int range = fuelcap * mpg;  //CTRL + /
//    System.out.println("The vehicle range is " + range + " miles");
    return fuelCap * mpg;
  }

  double fuelNeed(int miles) {
    return (double) miles / mpg;
  }

  void showInfo() {
    System.out.println(vehicleName +" carries " + passengers + " passengers, and fuel capacity is " + fuelCap + " gallons and mpg is " + mpg + " miles per gallon");
  }

  void showRange(int range) {
    System.out.println(vehicleName + " range is " + range + " miles");
  }
}
