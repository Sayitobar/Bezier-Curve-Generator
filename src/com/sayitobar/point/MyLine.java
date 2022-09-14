package com.sayitobar.point;

import com.sayitobar.Display;
import com.sayitobar.input.Mouse;
import com.sayitobar.world.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MyLine {  // A line with thickness

    public MyPoint p1, p2;
    public int thickness;
    public Color color;

    private final int lNum = (int) (Math.random() * 99998 +1);  // Unique line code

    public MyLine(MyPoint p1, MyPoint p2, int thickness) {
        this.p1 = p1;
        this.p2 = p2;
        this.thickness = thickness;
        this.color = Color.white;
    }
    public MyLine(MyPoint p1, MyPoint p2, int thickness, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.thickness = thickness;
        this.color = color;
    }

    public void render(Graphics g) {
        Mouse.resetScale();  // TEMPORARY, JUST NOT TO DRIVE ME CRAZY, DELETE AFTERWARDS
        g.drawLine(
                // bir fonksiyonu origin'e bağlı olarak x ve y değerleri üzerinden renderlamak !!!
                (int) ((p1.x - Camera.x + Camera.camOffsetX) * Mouse.scale),
                (int) ((Display.HEIGHT - p1.y + Camera.y - Camera.camOffsetY) * Mouse.scale),
                // Normalde g2 sol üstten bakar, ama onu sol alttan bakmasını sağlıyoruz
                (int) ((p2.x - Camera.x + Camera.camOffsetX) * Mouse.scale),
                (int) ((Display.HEIGHT - p2.y + Camera.y - Camera.camOffsetY) * Mouse.scale)
        );
        // TODO Zoom'luyken kamera hızını precise ayarla + mouse neredeyse oraya zoomla
    }

    public MyPoint getPointOf(double location) {
        location = Display.clamp(location, 0, 1);

        // return: Distance from point one (p1) in percentage
        return new MyPoint(p1.x + (p2.x - p1.x) * location, p1.y + (p2.y - p1.y) * location);
    }

    public double getLength() {
        return MyPoint.dist(p1, p2);
    }





    public static String toString(HashMap<Integer, ArrayList<MyLine>> allLines) {
        String output = "";

        for (Integer i: allLines.keySet()) {
            output = output.concat("\nGrad " + i + ":\t(" +  allLines.get(i).size() + " lines)\n");

            for (MyLine l: allLines.get(i)) {
                output = output.concat("\tL" + Display.elongateInt(l.lNum, 4) + " = (" + l.p1.x + " | " + l.p1.y + ")  ->  (" + l.p2.x + " | " + l.p2.y + ")\n");
            }
        }
        // Grad 1: \n
        // \t L0021 = () -> ()
        return output;
    }

    public String toString() {
        return "L" + Display.elongateInt(lNum, 4) + " = (" + p1.x + " | " + p1.y + ")  ->  (" + p2.x + " | " + p2.y + ")";
    }
}
