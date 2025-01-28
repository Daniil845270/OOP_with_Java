package edu.uob;

public class Shapes {

  // TODO use this class as then entry point; play around with your shapes, etc
  public static void main(String[] args) {
//    Triangle EQUILATERALTriangle = new Triangle(8, -1, 4);
//    System.out.println(EQUILATERALTriangle.getVariant());
//
//    Triangle myTriangle = new Triangle(17,23,32);
//    Triangle myOtherTriangle = new Triangle(8,0,-1);
//    int longestSide1 = testTriangle.getLongestSide();
//    int longestSide2 = myTriangle.getLongestSide();
//    int longestSide3 = myOtherTriangle.getLongestSide();
//    System.out.println("The longest side of the triangle is " + longestSide1);
//    System.out.println("The longest side of the triangle is " + longestSide2);
//    System.out.println("The longest side of the triangle is " + longestSide3);
//    System.out.println(myOtherTriangle.toString());
//
//    TwoDimensionalShape thing = new Triangle(4, 5, 6 );
//    System.out.println(thing.toString());
//
//    myTriangle.setColour(Colour.RED);
//    System.out.println(myTriangle.getColour());

//    System.out.println(myTriangle.calculateArea());
//
//    Circle myCircle = new Circle(5);
//
//  if(myTriangle instanceof MultiVariantShape) {
//      System.out.println("This shape has multiple variants");
//    } else {
//      System.out.println("This shape has only one variant");
//  }
//
//
//  if(myCircle instanceof MultiVariantShape) {
//      System.out.println("This shape has multiple variants");
//    } else {
//      System.out.println("This shape has only one variant");
//    }

    int range = 3;
    int min = 1;
    int rand = (int)(Math.random() * range) + min;
    TwoDimensionalShape shape = new Triangle(3, 4, 5);
    switch(rand) {
      case 1:
        shape = new Triangle(3, 4, 5);
        break;
      case 2:
        shape = new Circle(3);
        break;
      case 3:
        shape = new Rectangle(5, 7);
        break;
    }

//    System.out.println(shape.toString());

    if (shape instanceof MultiVariantShape) {
      System.out.println("This shape has multiple variants");
    } else {
      System.out.println("This shape has only one variant");
    }

    if (shape instanceof Triangle) {
      System.out.println("This shape Triangle");
    } else {
      System.out.println("This shape not Triangle");
    }

  }

}
