package edu.uob;

public class Triangle extends TwoDimensionalShape {
  private int a, b, c;
  private TriangleVariant triangleShape;

  // TODO implement me!
  public Triangle(int e, int f, int g) {
    this.a = e;
    this.b = f;
    this.c = g;


    if (a == b || b == c || c == a) {
      triangleShape = TriangleVariant.ISOSCELES;
    } if (a == b && b == c) {
      triangleShape = TriangleVariant.EQUILATERAL;
    } if (a != b && b != c && c != a) {
      triangleShape = TriangleVariant.SCALENE;
    } if ((Math.pow(a, 2) + Math.pow(b, 2) == Math.pow(c, 2)) ||
          (Math.pow(c, 2) + Math.pow(b, 2) == Math.pow(a, 2)) ||
          (Math.pow(a, 2) + Math.pow(c, 2) == Math.pow(b, 2))){
      triangleShape = TriangleVariant.RIGHT;
    } if ((a + b == c) || (b + c == a) || (c + a == b)){
      triangleShape = TriangleVariant.FLAT;
    } if ((a + b < c) || (b + c < a) || (c + a < b)){
      triangleShape = TriangleVariant.IMPOSSIBLE;
    } if ((a < 0) || (b < 0) || (c < 0)){
      triangleShape = TriangleVariant.ILLEGAL;
    }
  }

  public TriangleVariant getVariant(){
    return triangleShape;
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
