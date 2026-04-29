package vn.edu.vtiacademy.oop;

public class Square extends TwoDShape {

  private final String STYLE = "Square Style";
  private static String color = "Blue";

  public Square(int weight) {
    super(weight, weight);
    this.color = "Red";
  }

  public static void setColor(String newColor) {
    color = newColor;
  }

  @Override
  public double area() {
    return getWeight() * getHeight();
  }

  public void showInfo() {
    System.out.println("Weight: " + getWeight() + " " + "Height: " + getHeight() + " " + "Color: " + color);

  }
}
