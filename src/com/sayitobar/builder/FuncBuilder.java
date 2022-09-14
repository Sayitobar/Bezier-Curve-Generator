package com.sayitobar.builder;

import com.sayitobar.Display;
import com.sayitobar.entity.Function;
import com.sayitobar.point.BezierLine;
import com.sayitobar.point.MyLine;
import com.sayitobar.point.MyPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FuncBuilder {

    // Just a static class, which builds 2D functions \\

    // Basic Bézier Curve with only 1 control point
    public static Function createQuadraticBezier(MyPoint p1, MyPoint p2, MyPoint pControl, double resolution, int thickness, Color color, Boolean showHilfslinie) {
        ArrayList<MyLine> lines = new ArrayList<>();
        resolution = Display.clamp(resolution, 0.01, 10);

        MyLine l1 = new MyLine(p1, new MyPoint(pControl.x, pControl.y), 2, Color.DARK_GRAY);
        MyLine l2 = new MyLine(new MyPoint(pControl.x, pControl.y), p2, 2, Color.DARK_GRAY);

        if (showHilfslinie) {
            for (double l = 0; l <= 1; l += 0.1/resolution)
                lines.add(new MyLine(l1.getPointOf(l), l2.getPointOf(l), 1, Color.WHITE));

            lines.add( l1 );
            lines.add( l2 );
        }

        for (double l = 0; l <= 1; l += 0.1/resolution) {
            lines.add(new MyLine(
                    new MyLine(l1.getPointOf(l), l2.getPointOf(l), thickness).getPointOf(l),
                    new MyLine(l1.getPointOf(l + 0.1/resolution), l2.getPointOf(l + 0.1/resolution), thickness).getPointOf(l + 0.1/resolution),
                    thickness,
                    color
            ));
        }
        ArrayList<MyPoint> pC = new ArrayList<>();
        pC.add(pControl);

        Function f = new Function(lines, pC);
        f.setResolution(resolution);
        f.showHilfsLinie = showHilfslinie;
        return f;
    }

    // Complex Bézier Curve with more than 1 control points
    public static Function createBezier(MyPoint p1, MyPoint p2, ArrayList<MyPoint> pControls, double resolution, int thickness, Color color, Boolean showHilfslinie) {
        ArrayList<MyLine> lines = new ArrayList<>();
        resolution = Display.clamp(resolution, 0.01, 10);
        int Grad = pControls.size()+1;
        double ppl = 1/(0.1/resolution);  //  points per line

        ArrayList<BezierLine> structuralLines = new ArrayList<>();  // pControls'ları birbirine bağlayan ana line'lar

        // Adding structural lines (ilk nokta -- ilk pControl + pControl'lar araları + son pControl -- son nokta)
        structuralLines.add(new BezierLine(new MyPoint(p1.x, p1.y), new MyPoint(pControls.get(0).x, pControls.get(0).y), 2, getGradColor(1)));
        for (int i = 0; i < pControls.size()-1; i++)
            structuralLines.add(
                    new BezierLine(
                            new MyPoint(pControls.get(i).x, pControls.get(i).y),
                            new MyPoint(pControls.get(i+1).x, pControls.get(i+1).y),
                            2,
                            getGradColor(1)
                    ));
        structuralLines.add(new BezierLine(new MyPoint(pControls.get(pControls.size()-1).x, pControls.get(pControls.size()-1).y), new MyPoint(p2.x, p2.y), 2, getGradColor(1)));




        HashMap<Integer, ArrayList<BezierLine>> allLines = new HashMap<>();

        // Grade n of bezier curve (number of pControls +1) = n line arrays (including structural lines)
        for (int i=1; i <= Grad; i++)    allLines.put(i, new ArrayList<>());

        allLines.put(1, structuralLines);

        for (Integer i: allLines.keySet()) {
            if (i==1) continue;  // Structural lines zaten yerleştirdik

            ArrayList<BezierLine> subLines = new ArrayList<>();

            // Kendinden önceki line array'inden Bezier Spur'u çıkar (get the next grad from previous grad)

            // İlk önce structuralLines arraylist'inden çıkar (çünkü l onlarda yok ve bir line'da ör. üç yerden subLine çıkarıyor: resolution -> ppl)
            if (i==2) {
                for (double l = 0; l <= 1; l += 0.1/resolution) {
                    for (int preLine=0; preLine < allLines.get(i-1).size()-1; preLine++) {
                        subLines.add(new BezierLine(
                                structuralLines.get(preLine).getPointOf(l),
                                structuralLines.get(preLine+1).getPointOf(l),
                                thickness,
                                getGradColor(i), //color
                                l
                        ));
                    }
                }
            } else {  // Sonra 3. grad ve ötesini çıkar (çünkü l var ve bir çizgide sadece bir substring var)
                for (int preLine=0; preLine < allLines.get(i-1).size()-1; preLine++) {
                    // Eğer oluşturacağı BezierLine'ın iki noktasının l'si (0.3) farklıysa, aynı Grad ama farklı basamak demektir -> atla
                    if (allLines.get(i-1).get(preLine).getPValue() != allLines.get(i-1).get(preLine+1).getPValue()) continue;

                    // Sonraki Grad'lar için önceki Grad'ın l'sini yeni oluşturduğuna aktar
                    double l = allLines.get(i-1).get(preLine).getPValue();  // ör. 0.3

                    subLines.add(new BezierLine(
                            allLines.get(i-1).get(preLine  ).getPointOf( l ),
                            allLines.get(i-1).get(preLine+1).getPointOf( l ),
                            thickness,
                            getGradColor(i), //color
                            l
                    ));
                }
            }
            allLines.put(i, subLines);
        }

        if (showHilfslinie) {
            // Tersten göster ki structrLines önde çıksın
            for (int i = allLines.keySet().size(); i > 0; i--) {
                lines.addAll(allLines.get(i));
            }
        }

        // Get needed bezier curve from the last subLine array in HashMap (by connecting gotten points)
        for (int i=0; i < allLines.get(Grad).size()-1; i++) {
            lines.add(new MyLine(
                    allLines.get(Grad).get(i).getPointOf( allLines.get(Grad).get(i).getPValue() ),
                    allLines.get(Grad).get(i+1).getPointOf( allLines.get(Grad).get(i+1).getPValue() ),
                    thickness,
                    color
            ));
        }
        // Sondaki (bazen) tamamlanmamış noktayı p2 ile birleştir
        lines.add(new MyLine(
                allLines.get(Grad).get(allLines.get(Grad).size()-1).getPointOf( allLines.get(Grad).get(allLines.get(Grad).size()-1).getPValue() ),
                p2, thickness, color));

        // System.out.println(BezierLine.ToString(allLines));  // VERY USEFUL !!!

        Function f = new Function(lines, pControls);
        f.setResolution(resolution);
        f.showHilfsLinie = showHilfslinie;
        return f;
    }

    public static Function createParabola(double x, double y, double Steigung, double resolution, int thickness, Color color) {  // x, y = Scheitelpunkt
        ArrayList<MyLine> lines = new ArrayList<>();
        resolution = Display.clamp(resolution, 0.01, 10);

        // Formula: f(x0) = Steigung * (x0-x)^2 + y
        for (double x0 = -100.1; x0 <= 100.1; x0 += 1 / resolution) {
            lines.add(new MyLine(
                    new MyPoint(
                            x0 + x,
                            y + Steigung * Math.pow(x0, 2)
                    ),
                    new MyPoint(
                            (x0 + 1 / resolution) + x,      // Bir sonraki nokta (line oluşturabilmek için)
                            y + Steigung * Math.pow(x0 + 1 / resolution, 2)
                    ),                                         // Burada yeni nokta oluşturmayınca ( new MyPoint() ) kamera gezinirken 3D oluyor (saçma ama evet)
                    thickness,
                    color
            ));
        }

        Function f = new Function(lines);
        f.setResolution(resolution);
        return f;
    }

    public static Function createAxis(double x, double y, int thickness, Color color) {  // x, y = Origin
        ArrayList<MyLine> lines = new ArrayList<>();

        lines.add(new MyLine(new MyPoint(-1000000, y), new MyPoint(1000000, y), thickness));  // x-Axis
        lines.add(new MyLine(new MyPoint(x, -1000000), new MyPoint(x, 1000000), thickness));  // y-Axis

        return new Function(lines, color);
    }



    public static Color getRandomColor() {
        return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }

    public static Color getGradColor(int Grad) {
        /*return switch (Grad) {  // Doesn't work at JDK 1.8, only for JDK 15
            case 1 -> Color.DARK_GRAY;
            case 2 -> new Color(255, 255, 255, 149);
            case 4 -> new Color(129, 130, 0, 168);
            case 3 -> new Color(79, 128, 4, 133);
            default -> Color.GRAY;
        };*/
        if (Grad==1) {
            return Color.DARK_GRAY;
        }
        else if (Grad==2) {
            return new Color(255, 255, 255, 120);
        }
        else if (Grad==3) {
            return new Color(1, 91, 101, 120);
        }
        else if (Grad==4) {
            return new Color(92, 99, 65, 120);
        }
        else {
            return new Color(69, 69, 68, 120);
        }
    }

    public static Color getRisingColor(int Grad) {
        // Grad = order

        return new Color(
                Display.clamp(50  + Grad*20, 0, 255),
                Display.clamp(100 + Grad*20, 0, 255),
                Display.clamp(100 + Grad*20, 0, 255)
        );
    }
}
