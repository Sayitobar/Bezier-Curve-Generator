package com.sayitobar.input;

import com.sayitobar.Display;
import com.sayitobar.GUI.Menu;
import com.sayitobar.builder.FuncBuilder;
import com.sayitobar.entity.Function;
import com.sayitobar.entity.FunctionManager;
import com.sayitobar.point.MyPoint;
import com.sayitobar.world.Camera;

import java.awt.event.*;
import java.util.ArrayList;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    public int mouseX = -1, mouseY = -1;
    private int mouseButton = -1;

    private int scroll = 0;
    public static double scale = 1;  // Zoom in (between 0 and 300)
    public static double zoomFactor = 1.2;
    public static double camFactor = 1.15;

    private final double sensitivity = 2f;
    private int initialX, initialY;

    private Camera camera;
    private Display display;
    private FunctionManager fm;

    public Mouse(Camera camera, Display display) {
        this.camera = camera;
        this.display = display;
        this.fm = display.getEntityManager();
    }

    public void update(FunctionManager functionManager) {
        // Check if allowed to move bezier, if so move
        if (mouseButton == 1)  checkBezierEdit();
    }


    public void mouseClicked(MouseEvent e) {  // Tek klik
        /*
        // Create a square shaped small function on click location
        System.out.println(camera.getX() + " | " + camera.getY());
        ArrayList<MyLine> mL = new ArrayList<>();
        mL.add(new MyLine(
                // Kamera başlangıçta sabitken doğru, hareket edince yanlış işaretleniyor
                new MyPoint(mouseX-Display.WIDTH/2f, Display.HEIGHT/2f - mouseY),
                new MyPoint(mouseX-Display.WIDTH/2f, Display.HEIGHT/2f - mouseY),
                15));
        fm.functions.add(new Function(mL));
        */
    }

    // TODO pC'lerin olduğu bölgelere bir çember/yuvarlak konulabilir (UI/UX)

    public void mousePressed(MouseEvent e) {  // Basılı tutma (tekli)
        mouseButton = e.getButton();

        if (mouseButton != 1)  return;  // only left click

        // Check if clicked on menu icon
        if (mouseX > Display.WIDTH - 40  &&  mouseX < Display.WIDTH    &&    mouseY > 0  &&  mouseY < 40) {
            Menu.state = Menu.STATE.Menu;
            display.addMouseListener(display.getMenu());
        }

        // Check if clicked on a control point of an bezier curve
        for (Function bezier: fm.functions) {
            if (bezier.getControlPoints() == null) continue;  // If it's a bezier curve, go on the code below

            for (int i = 0; i < bezier.getControlPoints().size(); i++) {
                MyPoint pC = bezier.getControlPoints().get(i);

                if (mouseHoveredOn(mouseX, Display.HEIGHT - mouseY, (int) (pC.x + Display.WIDTH / 2f - Camera.x - 10), (int) (pC.y + Display.HEIGHT / 2f - Camera.y - 10), 20, 20)) {
                    moved_pC_and_Bezier[0] = i;
                    moved_pC_and_Bezier[1] = bezier.getUniqueCode();
                    System.out.println("Clicked (B): " + mouseX + "|" + mouseY + " - pC:" + pC.x + "|" + pC.y + " - c:" + camera.getX() + "|" + camera.getY());
                    return;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {  // Bırakma
        resetMButton();
        moved_pC_and_Bezier[0] = moved_pC_and_Bezier[1] = -1;
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {  // Tıklı halde sürükleme
        mouseX = e.getX();
        mouseY = e.getY();
        // System.out.println("DRAG: " + mouseX + " " + mouseY);
    }


    private int[] moved_pC_and_Bezier = {-1, -1};  // index, uniqueBezierCode: which bezier control point are you moving
    public void checkBezierEdit() {
        // Move bezier control points with cursor (by removing the original and creating a new one with an adjusted pC)
        for (Function bezier: fm.functions) {
            if (bezier.getControlPoints() == null) continue;  // If it's a bezier curve, go on the code below

            for (int i=0; i < bezier.getControlPoints().size(); i++) {

                if (moved_pC_and_Bezier[0] == i && moved_pC_and_Bezier[1] == bezier.getUniqueCode()) {  // If moving the same pC in that function
                    ArrayList<MyPoint> pControls = new ArrayList<>();
                    for (int j = 0; j < bezier.getControlPoints().size(); j++) {
                        if (j != i) pControls.add(bezier.getControlPoints().get(j));
                        else pControls.add(new MyPoint(mouseX - Display.WIDTH/2f + Camera.x, Display.HEIGHT-mouseY - Display.HEIGHT/2f + Camera.y));
                    }

                    System.out.println("Clicked a pControl: " + i);
                    Function bezier_new = FuncBuilder.createBezier(
                            bezier.getLines()[0].p1,
                            bezier.getLines()[bezier.getLines().length-1].p2,
                            pControls,
                            bezier.getResolution(),
                            bezier.thickness,
                            bezier.getLines()[bezier.getLines().length-1].color,
                            bezier.showHilfsLinie);

                    fm.functions.add(bezier_new);
                    moved_pC_and_Bezier[1] = bezier_new.getUniqueCode();

                    fm.removeFuncCode(bezier.getUniqueCode());

                    return;
                }
            }
        }
    }

    public void mouseMoved(MouseEvent e) {  // Genel sürükleme
        mouseX = e.getX();
        mouseY = e.getY();
        // System.out.println("MOVE: " + mouseX + " " + mouseY);
    }




    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = -e.getWheelRotation();

        if (scroll > 0) {
            scale *= zoomFactor;
            camera.camSpeed      /= camFactor;
        } else if (scroll < 0) {
            scale /= zoomFactor;
            camera.camSpeed      *= camFactor;
        }

        // Set zoom scale by zoomWheel
        scale  = Display.clamp(scale, 0, 300);
        System.out.println("ZoomScale: " + scale + " | c: " + camera.camSpeed + " (" + Camera.x + " | " + Camera.y + ")");
    }

    public boolean mouseHoveredOn(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseX < (x + width)     &&     mouseY > y && mouseY < (y + height);
    }


    public int getMouseX() {return mouseX; }
    public void setMouseX(int mouseX) {this.mouseX = mouseX;}

    public int getMouseY() {return mouseY;}
    public void setMouseY(int mouseY) {this.mouseY = mouseY;}

    public int getMouseButton() {return mouseButton;}
    public void setMouseButton(int mouseButton) {this.mouseButton = mouseButton;}
    public void resetMButton() {mouseButton = -1;}
    public static void resetScale() {scale = 1;}

    public int getScroll() {return scroll;}
    public void setScroll(int scroll) {this.scroll = scroll;}
}
