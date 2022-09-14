package com.sayitobar.input;

import com.sayitobar.Display;
import com.sayitobar.GUI.Menu;
import com.sayitobar.entity.Function;
import com.sayitobar.entity.FunctionManager;
import com.sayitobar.world.Camera;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private final boolean[] keys = new boolean[10000];
    private boolean left, right, forward, backward, up, down;

    private final Display display;
    private final Camera camera;

    public Keyboard(Display display, Camera camera) {
        this.display = display;
        this.camera = camera;
    }

    public void update(FunctionManager functionManager) {
        if (left)      camera.translate(-camera.camSpeed, 0);
        if (right)     camera.translate(camera.camSpeed, 0);
        if (forward)   camera.translate(0, camera.camSpeed);
        if (backward)  camera.translate(0, -camera.camSpeed);

        updateKeys();
    }

    public void updateKeys() {
        this.left     = this.keys[KeyEvent.VK_LEFT]  || this.keys[KeyEvent.VK_A];
        this.right    = this.keys[KeyEvent.VK_RIGHT] || this.keys[KeyEvent.VK_D];
        this.forward  = this.keys[KeyEvent.VK_UP]    || this.keys[KeyEvent.VK_W];
        this.backward = this.keys[KeyEvent.VK_DOWN]  || this.keys[KeyEvent.VK_S];

        this.up       = this.keys[KeyEvent.VK_SPACE];
        this.down     = this.keys[KeyEvent.VK_SHIFT];
    }


    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;


        if (e.getKeyCode() == KeyEvent.VK_M) {
            if (Menu.state == Menu.STATE.FreeRoam) {
                Menu.state = Menu.STATE.Menu;
                display.addMouseListener(display.getMenu());
            }
            else {
                Menu.state = Menu.STATE.FreeRoam;
                display.removeMouseListener(display.getMenu());
            }
        }
    }

    public boolean isStringInt(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }
}
