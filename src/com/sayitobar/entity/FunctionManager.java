package com.sayitobar.entity;

import com.sayitobar.builder.FuncBuilder;
import com.sayitobar.input.UserInput;
import com.sayitobar.point.MyPoint;

import java.awt.*;
import java.util.ArrayList;

public class FunctionManager {

    public ArrayList<Function> functions;


    public FunctionManager() {
        this.functions = new ArrayList<>();
    }

    public void init() {
        functions.add(FuncBuilder.createParabola(0, 0, 0.1, 3.0, 1, Color.BLACK));

        functions.add(FuncBuilder.createQuadraticBezier(new MyPoint(-400, 100), new MyPoint(+400, 100), new MyPoint(400, -300), 1.0, 3, Color.ORANGE, true));

        ArrayList<MyPoint> pControls = new ArrayList<>();
        pControls.add(new MyPoint(800, -100));
        pControls.add(new MyPoint(900, 300));
        pControls.add(new MyPoint(1000, 100));
        pControls.add(new MyPoint(1100, 500));
        pControls.add(new MyPoint(1300, 200));
        
        functions.add(FuncBuilder.createBezier(new MyPoint(700, 200), new MyPoint(1100, 200), pControls, 2.0, 3, Color.RED, true));

        ArrayList<MyPoint> pControls2 = new ArrayList<>();
        pControls2.add(new MyPoint(-600, 200));
        pControls2.add(new MyPoint(-900, 300));

        functions.add(FuncBuilder.createBezier(new MyPoint(-1000, 200), new MyPoint(-1500, 200), pControls2, 2.0, 3, Color.CYAN, true));


        // TODO: fix Zoom  |  Add pC when right click on bezier curve
    }

    public void render(Graphics g) {
        for (int i=0; i < functions.size(); i++) {
            functions.get(i).render(g);
        }
    }

    public void update(UserInput userInput) {
        userInput.mouse.update(this);
        userInput.keyboard.update(this);
    }

    public void removeAllFuncs() {
        functions = new ArrayList<>();
    }
    public void removeFuncCode(int code) {
        for (int i=0; i < functions.size(); i++)
            if (functions.get(i).getUniqueCode() == code) {
                functions.remove(i);
                return;
            }
    }
}
