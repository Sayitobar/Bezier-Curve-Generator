# Bezier-Curve-Generator
Beziér curve generator with UI on a 2D graphics engine


## Usage
- You can click & drag the control points of curves.
- You can use WASD (and arrow) keys to move on graph.
- You can zoom in/out.
- You can add new Bezier Curves in `entity` folder, `FunctionManager()` class in `init()` function. Just run the code and curves will be generated.
- The app is simple and clear, you can access and execute every task by clicking top right button.

## Cons
- You can neither generate new control points nor delete one.
- You can't drag the curves themselves
- You can't turn on/off the visibility of assisting lines (de: Hilfslinie)
- Zooming is janky and turned off by default. You need to re-enable it by (un)commenting some stuff (check `com.sayitobar.point.MyLine.render()`).
