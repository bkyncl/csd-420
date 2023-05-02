/*GeometricObject.java
* Module 6 Assignment 
* Name: Brittany Kyncl
* Date: 4.7.23
* Course: CSD420
* Abstract class to represent geometric object defining basic properties common to geometric objects
* implementing the comparable interface to allow objects of this
* type to be compared based on their area
*/
public abstract class GeometricObject implements Comparable<GeometricObject> {
    // static counter for total number of geometric object instances
    private static int objCount = 0;
    // instance counter that stores unique instance count for current object
    private int instanceCount;
    
    // constructor that increments objcount and assigns current count to instancecount
    public GeometricObject() {
        objCount++;
        instanceCount = objCount;
    }

    // returns instance count
    public int getInstanceCount(){
        return instanceCount;
    }

    // returns total instance count
    public int getObjCount(){
        return objCount;
    }

    // abstract method to return area of geometric object
    public abstract double getArea();
    // abstract method to return perimeter of geometric object
    public abstract double getPerimeter();
    
    //Implementation of the compareTo method from the Comparable interface
    //Compares two GeometricObject instances based on their area
    public int compareTo(GeometricObject other) {
        double thisArea = getArea();
        double otherArea = other.getArea();
        if (thisArea < otherArea) {
            return -1;
        } else if (thisArea > otherArea) {
            return 1;
        } else {
            return 0;
        }
    }

    // returns string value to print geometric obj instance properties 
    // and instance number out of total geometric object instances
    @Override
    public String toString() {
        return "Obj: " + getInstanceCount() + "/" + getObjCount();
    }
}
// Class defines Rectangle properties, extends GeometricObject
class Rectangle extends GeometricObject {
    // Instance variables that represent the width and height of the rectangle
    private double width;
    private double height;
    
    // constructor to initialize height and width of rectangle
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    // getters and setters for rectangle properties
    public double getWidth() {
        return width;
    }
    
    public void setWidth(double width) {
        this.width = width;
    }
    
    public double getHeight() {
        return height;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }

    // Calculates and returns area of rectangle
    @Override
    public double getArea() {
        return width * height;
    }

    // Calculates and returns perimeter of rectangle
    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    // Returns string value representing rectangle instance
    @Override
    public String toString() {
        return "Rectangle [width=" + width + ", height=" + height + ", perimeter= " + getPerimeter() +  ", area= " + getArea() +"] " + super.toString();
    }
}
// Class defines Circle properties, extends GeometricObject
class Circle extends GeometricObject {
    // Instance variable that represents the radius of the circle
    private double radius;
    
    // constructor to initialize radius of circle
    public Circle(double radius) {
        this.radius = radius;
    }

    // getters and setters for circle properties
    public double getRadius() {
        return radius;
    }
    
    public void setRadius(double radius) {
        this.radius = radius;
    }

    // calculates and returns radius of cirlce
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    // calculates and returns perimeter of circle
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
    
    // Returns string value representing circle instance
    @Override
    public String toString() {
        return "Circle [radius=" + radius + ", perimeter= "+ getPerimeter() + ", area= " + getArea() +"] "  + super.toString();
    }
}
