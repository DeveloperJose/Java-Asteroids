/*
CLASS: Game
DESCRIPTION: A painted canvas in its own window, updated every tenth second.
USAGE: Extended by Asteroids.
NOTE: You don't need to understand the details here, no fiddling necessary.
*/
public class Game {
    protected boolean on = true;
    protected int width, height;

    public Game(int inWidth, int inHeight) {
        width = inWidth;
        height = inHeight;

        StdDraw.setCanvasSize(width,height);
        // The following lines set the scales to
        // match Java's built-in coordinate system.
        //
        // The game coordinates will look like so:
        //
        // (0,0)                     (800,0)
        //   +-------------------------+
        //   |                         |
        //   |                         |
        //   +-------------------------+
        // (0,600)                   (800,600)
        StdDraw.setXscale(0,inWidth);
        StdDraw.setYscale(inHeight,0);
    }
}
