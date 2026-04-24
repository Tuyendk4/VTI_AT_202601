package vn.edu.vtiacademy.enum_demo;

import vn.edu.vtiacademy.oop.Triangle;
import vn.edu.vtiacademy.oop.TwoDShape;

public class Demo {

  public static void main(String[] args) {
    TwoDShape shape = new TwoDShape(5, 10);
    System.out.println("Weight: " + shape.getWeight());
    System.out.println("Height: " + shape.getHeight());
    shape.setWeight(20);
    shape.setHeight(15);
    System.out.println("Weight: " + shape.getWeight());
    System.out.println("Height: " + shape.getHeight());

    Triangle triangle = new Triangle(10, 20);
    triangle.showDim();
    System.out.println("Style: " + triangle.getStyle());
    triangle.setWeight(30);
    triangle.setHeight(40);
    triangle.setStyle("Outline");
    triangle.showDim();
    System.out.println("Style: " + triangle.getStyle());
  }

}
