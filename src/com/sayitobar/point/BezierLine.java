package com.sayitobar.point;

import com.sayitobar.Display;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BezierLine extends MyLine {  // A line in a bezier curve

    private double pValue;  // Only for (complex) bezier curve -> location of p1 on the previous Grad in percentage

    private final int lNum = (int) (Math.random() * 9998 +1);  // Unique line code

    public BezierLine(MyPoint p1, MyPoint p2, int thickness, Color color, double pValue) {
        super(p1, p2, thickness, color);
        this.pValue = pValue;
    }
    public BezierLine(MyPoint p1, MyPoint p2, int thickness, Color color) {
        super(p1, p2, thickness, color);
        this.pValue = 0;
    }

    public static String ToString(HashMap<Integer, ArrayList<BezierLine>> allLines) {
        String output = "";

        for (Integer i: allLines.keySet()) {
            output = output.concat("\nGrad " + i + ":\t(" +  allLines.get(i).size() + " lines)\n");

            for (BezierLine l: allLines.get(i)) {
                output = output.concat("\tL" + Display.elongateInt(l.lNum, 4) + " = (" + l.p1.x + " | " + l.p1.y + ")  ->  (" + l.p2.x + " | " + l.p2.y + ")\n");
            }
        }
        // Grad 1: \n
        // \t L0021 = () -> ()
        return output;
    }

    public double getPValue()              {return pValue;}
    public void   setPValue(double pValue) {pValue = Display.clamp(pValue, 0, 1);}
}
