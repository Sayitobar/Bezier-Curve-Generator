package com.sayitobar.point;

public class MyVector {

    public double x, y;

    public MyVector() {
        this.x = this.y = 0;
    }

    public MyVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public MyVector(MyPoint p1, MyPoint p2) {
        this.x = p2.x - p1.x;
        this.y = p2.y - p1.y;
    }


    public static double dot(MyVector v1, MyVector v2) {         // Skalarprodukt zweier Vektoren
        return v1.x * v2.x   +   v1.y * v2.y;                    // Wenn 0 => normal aufeinander (dik)
    }

    /*public  static MyVector cross(MyVector v1, MyVector v2) {    // Kreuzprodukt (Vektorprodukt) => Gibt einen neuen Vektor, der zu v1 und v2 normal ist.
        return new MyVector(
                v1.y * v2.z  -  v1.z * v2.y,
                v1.z * v2.x  -  v1.x * v2.z,
                v1.x * v2.y  -  v1.y * v2.x
        );
    }*/

    public static MyVector normalize(MyVector v) {
        double length = Math.sqrt(v.x*v.x + v.y+v.y);         // Die Länge (Betrag) von Vektor
        return new MyVector(v.x/length, v.y/length);    // Returns the "Einheitsvektor" with the length of 1
    }
}
