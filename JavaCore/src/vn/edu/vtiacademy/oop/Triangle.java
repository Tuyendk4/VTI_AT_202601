package vn.edu.vtiacademy.oop;

//is-A: Triangle is a TwoDShape
public class Triangle extends TwoDShape {

  private String style;

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

  public double area() {
    return (double) (getWeight() * getHeight() / 2);
  }
}
