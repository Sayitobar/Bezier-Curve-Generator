package com.sayitobar.GUI;

import com.sayitobar.Display;
import com.sayitobar.builder.FuncBuilder;
import com.sayitobar.entity.FunctionManager;
import com.sayitobar.point.MyPoint;

import java.awt.*;
import java.util.ArrayList;

public class Create extends GUI {

    private Display display;
    private FunctionManager functionManager;
    public Create(Display display) {
        this.display = display;
        this.functionManager = display.getEntityManager();
    }

    public void checkClicked(int mouseX, int mouseY) {
        // Create Bezier
        if (mouseHoveredOn(mouseX, mouseY, w-200, 60, 200, 40)) {
            functionManager.removeAllFuncs();

            ArrayList<MyPoint> pControls = new ArrayList<>();
            for (int i = (int) (Math.random() * 8) +1; i > 0 ; i--)
                pControls.add(new MyPoint(Math.random() * Display.WIDTH -Display.WIDTH/2f, Math.random() * Display.HEIGHT -Display.HEIGHT/2f));

            functionManager.functions.add(FuncBuilder.createBezier(new MyPoint(Math.random() * Display.WIDTH -Display.WIDTH/2f, Math.random() * Display.HEIGHT -Display.HEIGHT/2f), new MyPoint(Math.random() * Display.WIDTH -Display.WIDTH/2f, Math.random() * Display.HEIGHT -Display.HEIGHT/2f), pControls, 2.0, (int) ((mouseX-w+200)/200f * 10), FuncBuilder.getRandomColor(), true));
        }
        // Clicked "Back", remove keyboardListener
        else if (mouseHoveredOn(mouseX, mouseY, w - 60, h - 40, 60, 40)) {
            Menu.state = Menu.STATE.Menu;
        }
    }

    public void update() {

    }

    public void render(Graphics g) {
        g.setColor(transpBlack);
        g.fillRect(w - 200, 0, w, h);

        g.setColor(Color.white);
        g.setFont(font2);

        g.drawString("Create", w-175, 30);
        g.drawRect(w-200, w, 0, 35);

        g.setFont(font3);
        g.drawString("Create Bezier", w-175, 80);

        g.setFont(font4);
        g.drawString("Back", w - 55, h - 20);
    }
}
