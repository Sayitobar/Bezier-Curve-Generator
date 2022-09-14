package com.sayitobar;

import com.sayitobar.entity.FunctionManager;
import com.sayitobar.input.UserInput;
import com.sayitobar.world.Camera;
import com.sayitobar.GUI.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends Canvas implements Runnable {

    private Thread thread;
    private final JFrame frame;
    private static final String title = "Bézier Renderer";
    public  static int WIDTH = 1280, HEIGHT = WIDTH * 9/16;
    private static boolean running = false;

    private final UserInput userInput;
    private final Camera camera;
    private final Menu menu;

    private final FunctionManager functionManager;

    public Display() {
        this.frame = new JFrame();

        this.setPreferredSize( new Dimension(WIDTH, HEIGHT) );

        functionManager = new FunctionManager();

        camera = new Camera(0, 0);
        userInput = new UserInput(camera, this);
        menu = new Menu(this);

        this.addMouseListener(userInput.mouse);
        this.addMouseMotionListener(userInput.mouse);
        this.addMouseWheelListener(userInput.mouse);
        this.addKeyListener(userInput.keyboard);


        functionManager.init();
    }

    public static void main(String[] args) {
        Display display = new Display();

        display.frame.setTitle(title);
        display.frame.add(display);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setResizable(false);
        display.frame.setVisible(true);

        display.start();
    }

    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "Display");
        this.thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double amountOfTicks = 60.0;  // amount of tics/second
    public int FPS = 0;
    public void run() {
        long lastTime = System.nanoTime();
        double ns = 1000000000 / amountOfTicks; // amount of nanoseconds/tick

        double delta = 0;  // Time Difference
        long timer = System.currentTimeMillis();
        int frame = 0;
        this.requestFocus();  // Ekrana tıklamana gerek kalmayacak

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {  // One tick happens
                tick();
                delta--;
            }

            if (running) {
                render();  // don't Fix it to tick time (60fps)
                frame++;
            }

            if (System.currentTimeMillis() - timer > 1000) {  // Writes out the FPS once per second (1000ms = 1s)
                timer += 1000;
                System.out.println("FPS: " + frame);
                this.frame.setTitle("3D Renderer - FPS: " + frame);

                FPS = frame;
                frame = 0;
            }
        }
        stop();  // Stops the game
    }

    private Graphics g;
    public static int axisOffsetX = 0, axisOffsetY = 0;
    public static Color bgColor = new Color(117, 166, 173);
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();

        // Background
        g.setColor(bgColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);

        // TODO scroll adjust
        g.drawLine(0, (int) (HEIGHT/2 + camera.getY()), WIDTH, (int) (HEIGHT/2 + camera.getY()));  // xAxis
        g.drawLine((int) (WIDTH/2 - camera.getX()), 0, (int) (WIDTH/2 - camera.getX()), HEIGHT);   // yAxis

        // == Playground == \\
        functionManager.render(g);  // Render all functions
        menu.render(g);

        // ================ \\

        g.dispose();
        bs.show();
    }



    public void tick() {  // Update
        functionManager.update(userInput);
        menu.update();
    }


    public Graphics getGraphics() {return g;}
    public Menu getMenu() {return menu;}
    public FunctionManager getEntityManager() {return functionManager;}
    public UserInput getUserInput() {return userInput;}


    public static int clamp(int var, int min, int max) {
        if (var >= max) { return max; } else return Math.max(var, min);
    }

    public static double clamp(double var, double min, double max) {
        if (var >= max) { return max; } else return Math.max(var, min);
    }

    public static String elongateInt(int integer, int elongation) {
        // e.g given int is 7 and elongation is 4, output is 0007

        int len = Integer.toString(integer).length();
        if (elongation == len) return Integer.toString(integer);

        String output = "";
        for (int i = 0; i < elongation - len; i++)
            output = "0" + output;

        return output;
    }
}
