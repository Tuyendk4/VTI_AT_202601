package vn.edu.vtiacademy.class_demo;

public class Main {

  public static void main(String[] args) {
    Vehicle minivan = new Vehicle("Minivan", 7, 16, 21);
    Vehicle sportsCar = new Vehicle("SportsCar", 2, 14, 12);

    minivan.showInfo();

    sportsCar.showInfo();

    System.out.println("----------------------------------------");


//    minivan.vehicleName = "Minivan";
//    minivan.passengers = 7;
//    minivan.fuelCap = 16;
//    minivan.mpg = 21;
//
//    sportsCar.vehicleName = "SportsCar";
//    sportsCar.passengers = 2;
//    sportsCar.fuelCap = 14;
//    sportsCar.mpg = 12;

//    System.out.println("Minivan can carry " + minivan.passengers + " passengers with range of " + minivan.range() + " miles");
    minivan.showInfo();
    minivan.showRange(minivan.range());
    System.out.println("Minivan needs " + minivan.fuelNeed(100) + " gallons of fuel to travel 100 miles");
//    minivan.range();

//    System.out.println("SportsCar can carry " + sportsCar.passengers + " passengers with range of " + sportsCar.range() + " miles");
    sportsCar.showInfo();
    sportsCar.showRange(sportsCar.range());
    System.out.println("SportsCar needs " + sportsCar.fuelNeed(100) + " gallons of fuel to travel 100 miles");
//    sportsCar.range();

    System.out.println("----------------------------------------");
    Vehicle sedan = new Vehicle("Sedan", 5, 12, 30);
    sedan.showInfo();

    Vehicle defaultVehicle = new Vehicle();
    defaultVehicle.showInfo();
  }

}
