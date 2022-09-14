package com.sayitobar.GUI;

import com.sayitobar.Display;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class GUI extends MouseAdapter {
    public final int w = Display.WIDTH, h = Display.HEIGHT;

    public final Font font1     = new Font("CenturyGothic", Font.PLAIN, 50);  // Header
    public final Font font2     = new Font("CenturyGothic", Font.BOLD, 25);   // For Buttons
    public final Font font3     = new Font("CenturyGothic", Font.PLAIN, 20);  // For Descriptions
    public final Font font4     = new Font("CenturyGothic", Font.PLAIN, 17);  // For Settings/Menu
    public final Font font5     = new Font("CenturyGothic", Font.PLAIN, 23);  // For Sub-Settings/Create
    public final Font fontLight = new Font("Calibri", Font.PLAIN, 20);        // For Numbers & Others

    public final Color yellow     = new Color(213, 153, 11);
    public final Color lilac      = new Color(114, 115, 163);
    public final Color lightLilac = new Color(143, 138, 179);
    public final Color lightGray  = new Color(180, 180, 180);
    public final Color darkPurple = new Color(30, 0, 45);
    public final Color transpBlack= new Color(0, 0, 0, 160);
    public final Color onBlue     = new Color(10, 156, 156);


    public abstract void checkClicked(int mouseX, int mouseY);
    public abstract void render(Graphics g);

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public boolean mouseHoveredOn(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseX < (x + width)     &&     mouseY > y && mouseY < (y + height);
    }

}

