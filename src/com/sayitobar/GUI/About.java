package com.sayitobar.GUI;

import com.sayitobar.Display;
import com.sayitobar.builder.FuncBuilder;
import com.sayitobar.entity.FunctionManager;
import com.sayitobar.point.MyPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class About extends GUI {

    private Display display;

    public About(Display display) {
        this.display = display;
    }

    public void checkClicked(int mouseX, int mouseY) {
        // Clicked "Back", go back
        if (mouseHoveredOn(mouseX, mouseY, w - 60, h - 40, 60, 40)) {
            Menu.state = Menu.STATE.Menu;
        }
    }

    public void update() {

    }

    public void render(Graphics g) {
        g.setColor(transpBlack);
        g.fillRect(w - 400, 0, w, h);

        g.setColor(Color.white);
        g.setFont(font2);

        g.drawString("About", w-375, 30);
        g.drawRect(w-400, w, 0, 35);

        g.setFont(font3);
        g.drawString("Bezier Curve Generator® created by", w-375, 80);
        g.drawString("Sayitobar Software Inc.", w-315, 130);
        try {
            g.drawImage(ImageIO.read(new File(System.getProperty("user.dir") + "/res/sayitobar_Logo.png")), w-400, h/2-100, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.setFont(font4);
        g.drawString("Back", w - 55, h - 20);
    }
}
