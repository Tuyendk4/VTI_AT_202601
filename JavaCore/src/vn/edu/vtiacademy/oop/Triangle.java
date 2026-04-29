package vn.edu.vtiacademy.oop;

//is-A: Triangle is a TwoDShape
public class Triangle extends TwoDShape {

  private String style;

  public Triangle() {
    super(0, 0);
    style = "Empty";
  }

  public Triangle(int weight, int height) {
    super(weight, height);
    style = "Filled";
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public void showInfo() {
    System.out.println("Weight: " + getWeight() + " " + "Height: " + getHeight());
    System.out.println("Style: " + style);
  }
  public double area() {
    return (double) (getWeight() * getHeight() / 2);
  }

}
