package com.sayitobar.point;

public class MyPoint {  // A 2D Point

    public final static MyPoint origin = new MyPoint(0, 0);

    public double x, y;

    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double dist(MyPoint p1, MyPoint p2) {  // Give the distance between 2 points (actually length of vector)
        double x2 = Math.pow(p2.x - p1.x, 2);
        double y2 = Math.pow(p2.y - p1.y, 2);
        return Math.sqrt(x2 + y2);
    }
}
