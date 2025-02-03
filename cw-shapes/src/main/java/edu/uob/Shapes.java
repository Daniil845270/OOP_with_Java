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

    TwoDimensionalShape[] shapesArray = new TwoDimensionalShape[100];

    for (int i = 0; i < shapesArray.length; i++) {
      double randomValue = Math.random(); // returns [0, 1)

      if (randomValue < 0.33) {
        // create a Triangle with some parameters
        shapesArray[i] = new Triangle(3, 4, 5);
      } else if (randomValue < 0.66) {
        // create a Circle
        shapesArray[i] = new Circle(5);
      } else {
        // create a Rectangle
        shapesArray[i] = new Rectangle(5, 7);
      }
    }

//    TwoDimensionalShape firstShape = shapesArray[0];
//    // Down-cast the shape into a triangle
//    Triangle firstTriangle = (Triangle)firstShape;
//    TriangleVariant variant = firstTriangle.getVariant();

    int triangleCount = 0;  // to keep track of how many triangles we have

    for (int i = 0; i < shapesArray.length; i++) {
      TwoDimensionalShape shape = shapesArray[i];

      // Print out the shape's details (using toString)
      System.out.println(shape.toString());

      // Check if the shape is a triangle
      if (shape instanceof Triangle) {
        triangleCount++;
      }

      System.out.println("-----------"); // Just a separator
    }

    System.out.println("Number of Triangles: " + triangleCount);
    System.out.println("Class variable value: " + Triangle.populationTriangleSize);


//    int range = 3;
//    int min = 1;
//    int rand = (int)(Math.random() * range) + min;
//    TwoDimensionalShape shape = new Triangle(3, 4, 5);
//    switch(rand) {
//      case 1:
//        shape = new Triangle(3, 4, 5);
//        break;
//      case 2:
//        shape = new Circle(3);
//        break;
//      case 3:
//        shape = new Rectangle(5, 7);
//        break;
//    }
//
////    System.out.println(shape.toString());
//
//    if (shape instanceof MultiVariantShape) {
//      System.out.println("This shape has multiple variants");
//    } else {
//      System.out.println("This shape has only one variant");
//    }
//
//    if (shape instanceof Triangle) {
//      System.out.println("This shape Triangle");
//    } else {
//      System.out.println("This shape not Triangle");
//    }

  }

}


//Then (in your main method) create an array of size 100 that can hold TwoDimensionalShapes.
//Fill this array with randomly chosen shapes (Circles, Rectangles, Triangles).
//In order to randomly select a shape from those available, you may like to make use of the Java Math.random() method.
//This will return a randomly selected double precision floating point number (somewhere between 0.0 and 1.0).
//You will need to write an IF statement and use some cunning maths to look at this number and decide which shape to create.