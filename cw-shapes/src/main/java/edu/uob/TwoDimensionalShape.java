package edu.uob;

public abstract class TwoDimensionalShape {
  private Colour colour;

  public TwoDimensionalShape() {}

  public abstract double calculateArea();

  public abstract int calculatePerimeterLength();

  public void setColour(Colour newColour) {
    this.colour = newColour;
  }

  public Colour getColour()
  {
    return colour;
  }
}
