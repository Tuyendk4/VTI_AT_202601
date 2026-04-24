package vn.edu.vtiacademy.oop;

public class TwoDShape {

  private int weight;
  private int height;

  public TwoDShape(int weight, int height) {
    this.weight = weight;
    this.height = height;
  }

  public int getWeight() { //get, find,...
    return weight;
  }

  public void setWeight(int weight) { //set, update, input, ...
    this.weight = weight;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void showDim() {
    System.out.println("Weight: " + weight + " " + "Height: " + height);
  }
}
