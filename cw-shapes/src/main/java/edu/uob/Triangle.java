package edu.uob;

public class Triangle extends TwoDimensionalShape implements MultiVariantShape {
  private long a, b, c;
  private TriangleVariant triangleShape;
  static int populationTriangleSize = 0;

  // TODO implement me!
  public Triangle(int e, int f, int g) {
    Triangle.populationTriangleSize++;

    this.a = e;
    this.b = f;
    this.c = g;

    if (a == b || b == c || c == a) {
      triangleShape = TriangleVariant.ISOSCELES;
    } if (a == b && b == c) {
      triangleShape = TriangleVariant.EQUILATERAL;
    } if (a != b && b != c && c != a) {
      triangleShape = TriangleVariant.SCALENE;
    } if ((a*a == c*c - b*b) ||
          (c*c == a*a - b*b) ||
          (a*a == b*b - c*c)){
      triangleShape = TriangleVariant.RIGHT;
    } if ((a + b == c) || (b + c == a) || (c + a == b)){
      triangleShape = TriangleVariant.FLAT;
    } if ((a + b < c) || (b + c < a) || (c + a < b)){
      triangleShape = TriangleVariant.IMPOSSIBLE;
    } if ((a <= 0) || (b <= 0) || (c <= 0)){
      triangleShape = TriangleVariant.ILLEGAL;
    }
  }

  public int getPopulation(){
    return populationTriangleSize;
  }

  public TriangleVariant getVariant(){
    return triangleShape;
  }

  public long getLongestSide() {
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
    //Area = √[s(s-a)(s-b)(s-c)]
    //s = p/2
    //int s = calculatePerimeterLength()/2;
    double s = (a + b + c)/2.0;
    double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
    return area;
  }

  // TODO implement me!
  public int calculatePerimeterLength() {
    int p = (int) (a + b + c);
    return p;
  }
}
