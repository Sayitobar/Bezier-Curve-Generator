package com.sayitobar.GUI;

import com.sayitobar.Display;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Menu extends GUI {
    public enum STATE {FreeRoam, Menu, Settings, Create, About}
    public static STATE state = STATE.FreeRoam;

    private Create create;
    private Settings settings;
    private About about;

    private Display display;
    public Menu(Display display) {
        this.display = display;

        create   = new Create(display);
        settings = new Settings(display);
        about    = new About(display);
    }


    public void checkClicked(int mouseX, int mouseY) {
        // Clicked "Create"
        if (mouseHoveredOn(mouseX, mouseY, w-200, 60, 200, 40)) {
            state = STATE.Create;
        }
        // Clicked "Settings"
        else if (mouseHoveredOn(mouseX, mouseY, w-200, 100, 200, 40)) {
            state = STATE.Settings;
        }
        // Clicked "About" section
        else if (mouseHoveredOn(mouseX, mouseY, w-200, 140, 200, 40)) {
            state = STATE.About;
        }
        // Clicked "Back", remove keyboardListener
        else if (mouseHoveredOn(mouseX, mouseY, w - 60, h - 40, 60, 40)) {
            state = STATE.FreeRoam;
            display.removeMouseListener(this);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() != 1) return;

        int mouseX = e.getX();
        int mouseY = e.getY();

        if (state == STATE.Menu) {
            this.checkClicked(mouseX, mouseY);
        }
        else if (state == STATE.Create) {
            create.checkClicked(mouseX, mouseY);
        }
        else if (state == STATE.Settings) {
            settings.checkClicked(mouseX, mouseY);
        }
        else if (state == STATE.About) {
            about.checkClicked(mouseX, mouseY);
        }
    }

    public void update() {
        settings.update();
        create.update();
        about.update();
    }

    public void render(Graphics g) {
        if (Settings.AA_Text) ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Show only menu icon in free roam
        if (state == STATE.FreeRoam) {
            g.setColor(Color.white);
            g.drawRect(w - 40, 0, 39, 39);
            g.setColor(transpBlack);
            g.fillRect(w - 39, 1, 38, 38);

            g.setColor(Color.white);
            g.fillRoundRect(w-35, 8, 30, 2, 5, 5);
            g.fillRoundRect(w-35, 18, 30, 2, 5, 5);
            g.fillRoundRect(w-35, 28, 30, 2, 5, 5);
        }

        // Menu screen
        else if (state == STATE.Menu) {
            g.setColor(transpBlack);
            g.fillRect(w - 200, 0, w, h);

            g.setColor(Color.white);
            g.setFont(font2);

            g.drawString("Menu", w-175, 30);
            g.drawRect(w-200, w, 0, 35);

            g.setFont(font3);
            g.drawString("Create", w-175, 80);

            g.drawString("Settings", w-175, 120);
            g.drawString("About", w-175, 160);

            g.setFont(font4);
            g.drawString("Back", w - 55, h - 20);
        }

        // Create screen
        else if (state == STATE.Create) {
            create.render(g);
        }

        // Settings screen
        else if (state == STATE.Settings) {
            settings.render(g);
        }

        // About screen
        else if (state == STATE.About) {
            about.render(g);
        }

        if (Settings.AA_Text) ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
}
