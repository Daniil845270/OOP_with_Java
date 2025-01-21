package edu.uob;

public class Triangle extends TwoDimensionalShape {
  private int a, b, c;

  // TODO implement me!
  public Triangle(int e, int f, int g) {
    this.a = e;
    this.b = f;
    this.c = g;
  }

  public int getLongestSide() {
    if (a > b && a > c){
      return a;
    }
    else if (b > a && b > c){
      return b;
    }
    else if (c > a && c > b){
      return c;
    }
    return -1;
  }

  public String toString(){
    return ("The sides of the triangle are " + a + " " + b + " " + c);
  }

  // TODO implement me!
  public double calculateArea() {
    return 0;
  }

  // TODO implement me!
  public int calculatePerimeterLength() {
    return 0;
  }
}
