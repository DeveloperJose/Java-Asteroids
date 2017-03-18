/*
CLASS: Shape
DESCRIPTION: The ship is the player's
USAGE: You should create an array of points that define the
       shape of the ship outside this class (for example, in
       the Asteroids class).
       Call the move() method repeatedly in the game loop.
 */

import java.awt.event.*;

public class Ship extends Polygon {
	/**
	 * The amount of pixels it will continue to move after you release the up key
	 */
	private static final int GRAVITY = 150;
	/**
	 * This creates an instance of the Ship class.
	 *
	 * @param shipShape
	 *            An array containing points that define the Ship's shape.
	 * @param inPosition
	 *            The position offset.
	 * @param rotation
	 *            Rotation of the ship, in degrees.
	 */
	public Ship(Point[] shipShape, Point inPosition, double rotation) {
		super(shipShape, inPosition, rotation);
	}
	/**
	 * How many pixels to accelerate after releasing up
	 */
	private double acceleration = 0;
	
	/**
	 * Checks the keyboard and moves the ship
	 */
	public void move() {
		// Check the state of the keyboard
		boolean isUpPressed = StdDraw.isKeyPressed(KeyEvent.VK_UP);
		boolean isLeftPressed = StdDraw.isKeyPressed(KeyEvent.VK_LEFT);
		boolean isRightPressed = StdDraw.isKeyPressed(KeyEvent.VK_RIGHT);
		boolean isSpacePressed = StdDraw.isKeyPressed(KeyEvent.VK_SPACE);
		// Act upon the state of the keys

		// If the UP arrow is pressed ...
		if (isUpPressed) {
			acceleration = GRAVITY; // How many pixels it keeps moving towards
			int increase = 1; // Number of pixels to move the ship
			// Trigonometry to move the ship forward depending on
			// the degrees of rotation.
			position.x += increase * Math.cos(Math.toRadians(rotation));
			position.y += increase * Math.sin(Math.toRadians(rotation));
			// If, after repositioning the ship, we end up past a screen corner,
			// make the ship re-appear on the other edge of the screen.

			// Check x boundaries
			if (position.x > 800)
				position.x = 0;
			if (position.x < 0)
				position.x = 800;

			// Check y boundaries
			if (position.y > 600)
				position.y = 0;
			if (position.y < 0)
				position.y = 600;
		} else { // If we have NOT pressed up
			if (acceleration > 0) { // If we are accelerating still
				// Move one pixel on x and y
				position.x += 1 * Math.cos(Math.toRadians(rotation));
				position.y += 1 * Math.sin(Math.toRadians(rotation));
				// Decrease acceleration
				acceleration -= 1;
			}
		}

		// If the left key is pressed ...
		if (isLeftPressed) {
			int degreesChange = 1; // Number of degrees to rotate
			rotate(-degreesChange);
		}

		// If the right key is pressed ...
		if (isRightPressed) {
			int degreesChange = 1; // Number of degrees to rotate
			rotate(degreesChange);
		}

		if (isSpacePressed) {
			Asteroids.bullet = Bullet.generate(position, rotation);
		}
	}
}
