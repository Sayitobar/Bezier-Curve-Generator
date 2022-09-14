package com.sayitobar.input;

import com.sayitobar.Display;
import com.sayitobar.world.Camera;

public class UserInput {

    public Mouse mouse;
    public Keyboard keyboard;

    public Camera camera;

    public UserInput(Camera camera, Display display) {
        this.mouse = new Mouse(camera, display);
        this.keyboard = new Keyboard(display, camera);

        this.camera = camera;
    }

    public UserInput(Mouse mouse, Keyboard keyboard, Camera camera) {
        this.mouse = mouse;
        this.keyboard = keyboard;

        this.camera = camera;
    }
}
