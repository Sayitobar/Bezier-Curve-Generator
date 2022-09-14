package com.sayitobar.entity;

import com.sayitobar.GUI.Settings;
import com.sayitobar.point.MyLine;
import com.sayitobar.point.MyPoint;

import java.awt.*;
import java.util.ArrayList;

public class Function {

    private MyPoint[] points;
    private MyLine[]  lines;
    public Color color;
    public int thickness;
    private double resolution;
    public boolean showHilfsLinie;

    private final int uniqueCode = (int) (Math.random() * 999999998) +1;
    private ArrayList<MyPoint> bezierControlPoints;

    public Function(ArrayList<MyPoint> points, int thickness) {
        this.points    = points.toArray(new MyPoint[0]);
        this.thickness = thickness;
        this.color     = Color.white;
    }
    public Function(ArrayList<MyPoint> points, int thickness, Color color) {
        this.points    = points.toArray(new MyPoint[0]);
        this.lines     = null;  //getLines(points, thickness);
        this.thickness = thickness;
        this.color     = color;
    }
    public Function(ArrayList<MyLine> lines) {
        this.lines     = lines.toArray(new MyLine[0]);
        this.thickness = getAvgThickness(lines);
        this.color     = null;
    }
    public Function(ArrayList<MyLine> lines, ArrayList<MyPoint> bezierControlPoints) {
        this.lines     = lines.toArray(new MyLine[0]);
        this.thickness = getAvgThickness(lines);
        this.color     = null;
        this.bezierControlPoints = bezierControlPoints;
    }  // Only if bezier curve
    public Function(ArrayList<MyLine> lines, Color color) {
        this.points    = null;  //getPoints(lines);
        this.lines     = lines.toArray(new MyLine[0]);
        this.thickness = getAvgThickness(lines);
        this.color     = color;
    }

    public void render(Graphics g) {
        if (Settings.AA) ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ((Graphics2D) g).setStroke(new BasicStroke(thickness));
        if (color != null) g.setColor(color);

        if (points == null) {                            // If just lines were given
            for (MyLine line: lines) {
                // if (color == null)
                g.setColor(line.color);
                line.render(g);
            }
        }
        else if (lines == null) {                        // If just points were given  (ll'WORK FAULTY - REMASTER THIS!!!)
            if (points.length <= 1) {
                g.fillOval((int) points[0].x, (int) points[0].y, thickness, thickness); // didn't add xOffset, it'll work faulty while camera movement
                return;
            }

            for (int i = 0; i < points.length - 1; i++)
                g.drawLine((int) points[i].x, (int) points[i].y, (int) points[i + 1].x, (int) points[i + 1].y);
        }

        if (Settings.AA) ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

/*
    private MyLine[] getLines(ArrayList<MyPoint> points, int thickness) {
        MyLine[] l = new MyLine[ points.size()-1 ];

        if (points.size() <= 1) {
            l[0] = new MyLine(points.get(0), points.get(0), thickness);
            return l;
        }

        for (int i=0; i < points.size()-1; i++) {  // Son noktayı line'lara ekleme, çünkü -> lineNum = points.size - 1
            l[i] = new MyLine(points.get(i), points.get(i + 1), thickness);
        }

        return l;
    }

    private MyPoint[] getPoints(ArrayList<MyLine> lines) {
        ArrayList<MyPoint> ps = new ArrayList<>();

        for (int i=0; i < lines.size(); i++) {
            if (i >= lines.size() - 1) {  // Son line (nokta)
                ps.add(lines.get(i).p1);
                ps.add(lines.get(i).p2);
            }
            else
                ps.add(lines.get(i).p1);
        }
        return ps.toArray(new MyPoint[0]);
    }
*/
    private int getAvgThickness(ArrayList<MyLine> lines) {
        int num = 0;
        for (MyLine l: lines)
            num += l.thickness;
        return num/lines.size();
    }
    public ArrayList<MyPoint> getControlPoints() {
        return bezierControlPoints;
    }
    public int getUniqueCode() {return uniqueCode;}

    public MyPoint[] getPoints() {return points;}
    public MyLine[] getLines() {return lines;}

    public void setResolution(double resolution) {this.resolution = resolution;}
    public double getResolution() {return resolution;}

    private int getAvgThickness(MyLine[] lines) {
        int num = 0;
        for (MyLine l: lines)
            num += l.thickness;
        return num/lines.length;
    }
}
