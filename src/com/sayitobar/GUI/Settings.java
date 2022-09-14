package com.sayitobar.GUI;

import com.sayitobar.Display;
import com.sayitobar.builder.FuncBuilder;

import java.awt.*;
import java.util.Set;

public class Settings extends GUI {
    public static boolean dX = false,  dY = false;                 // Is Selected to edit display dimensions?

    public static boolean AA = true;                               // AntiAliasing (Smooth lines)
    public static boolean AA_Text = true;                          // AntiAliasing (Smooth texts)

    private Display display;
    public Settings(Display display) {this.display = display;}

    public void checkClicked(int mouseX, int mouseY) {

        // Clicked on display dimensions X
        if (mouseHoveredOn(mouseX, mouseY, w-160, 301, 60, 24)) {
            dX = !dX;
        }
        // Clicked on display dimensions Y
        else if (mouseHoveredOn(mouseX, mouseY, w-90, 301, 60, 24)) {
            dY = !dY;
        }

        // Clicked "Set Background Color", assign bgColor a random color
        else if (mouseHoveredOn(mouseX, mouseY, w - 400, 340, 400, 40)) {
            Display.bgColor = FuncBuilder.getRandomColor();
        }

        // Clicked "AntiAliasing Lines", toggle switch
        else if (mouseHoveredOn(mouseX, mouseY, w - 400, 480, 400, 40)) {
            Settings.AA = !Settings.AA;
        }
        // Clicked "AntiAliasing Texts", toggle switch
        else if (mouseHoveredOn(mouseX, mouseY, w - 400, 520, 400, 40)) {
            Settings.AA_Text = !Settings.AA_Text;
        }

        // Clicked "Back", turn to main menu
        else if (mouseHoveredOn(mouseX, mouseY, w - 60, h - 40, 60, 40)) {
            Menu.state = Menu.STATE.Menu;
        }
    }

    public void update() {    }

    public void render(Graphics g) {
        ((Graphics2D) g).setStroke(new BasicStroke(1));
        g.setColor(transpBlack);
        g.fillRect(w - 400, 0, w, h);

        g.setColor(Color.white);
        g.setFont(font2);
        g.drawString("Settings", w-375, 30);
        g.drawRect(w-400, w, 0, 35);

        // == Settings buttons == \\

        // Lighting Settings
        g.setFont(font5);
        g.drawString("Lighting", w-200 - g.getFontMetrics().stringWidth("Lighting")/2, 80);
        g.drawLine(w-320, 90, w-80, 90);

        g.setFont(font3);
        g.drawString("Enable Lighting", w-375, 130);
        g.drawString("Light Vector", w-375, 170);
        g.drawString("Ambient Light", w-375, 210);



        // Display Settings
        g.setFont(font5);
        g.drawString("Display", w-200 - g.getFontMetrics().stringWidth("Display")/2, 270);
        g.drawLine(w-320, 280, w-80, 280);

        g.setFont(font3);
        g.drawString("Set Dimensions", w-375, 320);
        g.drawString("Set Random Background Color", w-375, 360);
        g.drawString("Scroll Sensitivity", w-375, 400);
        g.drawString("Camera Speed", w-375, 440);
        g.drawString("AntiAliasing Lines", w-375, 500);
        g.drawString(Settings.AA ? "ON" : "OFF", w-100, 500);
        g.drawString("AntiAliasing Texts", w-375, 540);
        g.drawString(Settings.AA_Text ? "ON" : "OFF", w-100, 540);
        g.drawString("Reset Scroll Scale", w-375, 600);

        // Set Dimensions
        g.setColor(onBlue);
        g.fillRoundRect(w-160, 301, 60, 24, 23, 23);
        g.fillRoundRect(w-90,  301, 60, 24, 23, 23);
        g.setColor(Color.WHITE);
        if (dX) g.drawRoundRect(w-160, 301, 59, 23, 23, 23);
        if (dY) g.drawRoundRect(w-90,  301, 59, 23, 23, 23);
        g.drawString(String.valueOf(Display.WIDTH ), w-130 - g.getFontMetrics().stringWidth(String.valueOf(Display.WIDTH ))/2, 320);
        g.drawString(String.valueOf(Display.HEIGHT), w-60  - g.getFontMetrics().stringWidth(String.valueOf(Display.HEIGHT))/2, 320);
        g.setFont(font4);
        g.drawString("x", w-91  - g.getFontMetrics().stringWidth("x"), 318);
        g.setFont(font3);

        g.setFont(font4);
        g.drawString("Back", w - 55, h - 20);
    }
}
