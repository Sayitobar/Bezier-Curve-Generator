package com.sayitobar.world;

import com.sayitobar.Display;

public class Camera {

    public static double x, y;
    public static int camOffsetX = Display.WIDTH/2, camOffsetY = Display.HEIGHT/2;

    public double camSpeed = 6;

    public Camera(double x, double y) {
        Camera.x = x;
        Camera.y = y;
    }

    public void translate(double x, double y) {
        Camera.x += x;
        Camera.y += y;
    }

    public double getX()     {return x;}
    public double getY()     {return y;}

    public double getCamSpeed() {return camSpeed;}
}
