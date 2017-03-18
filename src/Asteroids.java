import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

/*
 CLASS: Asteroids
 DESCRIPTION: Extending Game, Asteroids is all in the paint method.
 NOTE: This class is the metaphorical "main method" of your program,
 it is your control center.
 Modified to use Princeton's stdlib by Rafa Escalante
 */
public class Asteroids extends Game {
	/**
	 * Length of invincibility in milliseconds [Max]
	 */
	private static final int INVINCIBILITY_INTERVAL = 3000;
	/**
	 * How much time has actually passed of invincibilty [Current]
	 */
	private static int currentInvFrames = 0;
	/**
	 * Color scheme for invincibility mode
	 */
	private static final Color[] invColors = new Color[] { StdDraw.WHITE,
			StdDraw.LIGHT_GRAY, StdDraw.WHITE, StdDraw.GRAY, StdDraw.WHITE,
			StdDraw.DARK_GRAY, StdDraw.WHITE, StdDraw.BLACK };
	/**
	 * This is the index of the current invincibility color on the color scheme
	 */
	private static int currentInvColor = 0;
	/**
	 * The default color of the ship
	 */
	private static final Color shipColor = StdDraw.WHITE;
	/**
	 * The default color of the bullets
	 */
	private static final Color bulletColor = StdDraw.ORANGE;
	/**
	 * The default color of the asteroids
	 */
	private static final Color asteroidColor = StdDraw.CYAN;
	/**
	 * The default color of the stars
	 */
	private static final Color starColor = StdDraw.LIGHT_GRAY;
	/**
	 * The number of lives
	 */
	private static int lives = 2;
	/**
	 * The number of stars to spawn
	 */
	private static int numberOfStars = 400;
	/**
	 * The number of asteroids to spawn
	 */
	private static int numberOfAsteroids = 30;
	/**
	 * True if you are invincible, false if otherwise
	 */
	private static boolean isInvincible = true;
	/**
	 * Random generator
	 */
	private static Random random = new Random();
	/**
	 * Our player or ship
	 */
	private static Ship ship;
	/**
	 * Our asteroids
	 */
	private static Asteroid[] asteroids;
	/**
	 * Our stars
	 */
	private static Star[] stars;
	/**
	 * Our bullet
	 */
	public static Bullet bullet;

	/**
	 * Initializes the Asteroids game.
	 */
	public Asteroids() {
		// Call the constructor of Game to create the canvas.
		// 800 pixels by 600 pixels [Screen size]
		super(800, 600);
		// Create the ship.
		Point[] shipShape = new Point[4];

		shipShape[0] = new Point(440, 320);
		shipShape[1] = new Point(400, 340);
		shipShape[2] = new Point(420, 320);
		shipShape[3] = new Point(400, 300);
		// Set the initial position of the ship (center of the screen)
		Point shipInitialPosition = new Point(400, 300);
		// Create our Ship instance
		ship = new Ship(shipShape, shipInitialPosition, 45);

		// Initialize asteroids array
		asteroids = new Asteroid[numberOfAsteroids];
		// Generate random asteroids
		for (int i = 0; i < numberOfAsteroids; i++) {
			asteroids[i] = Asteroid.generateRandom();
		}

		// Initialize stars array
		stars = new Star[numberOfStars];
		// Generate random stars
		for (int i = 0; i < numberOfStars; i++) {
			stars[i] = Star.generateRandom();
		}
	}

	/**
	 * Draws (or redraws) the game state to the screen.
	 */
	public void draw() {
		// Set the background to black
		StdDraw.clear(StdDraw.BLACK);

		// We create a variable to hold the ship color
		// in case we are invincible to change the color
		// Set it to the default color
		Color currentShipColor = shipColor;

		// Check if we are alive
		if (lives > 0) {
			// Move the ship
			ship.move();

			// Check if we are invincible
			if (isInvincible) {
				// Get the current invincibilty color
				// and set it as the current color of the ship
				currentShipColor = invColors[currentInvColor];
				// Increment to go to the next color
				currentInvColor++;
				// Check if we are on the last color to start again from 0
				if (currentInvColor >= invColors.length)
					currentInvColor = 0;

				// We increase by 5 milliseconds our count
				currentInvFrames += 5;
				// Check that we still have invincibility
				if (currentInvFrames >= INVINCIBILITY_INTERVAL) {
					isInvincible = false;
					currentInvFrames = 0;
				}
			}
			// Set the pen color to draw the ship
			StdDraw.setPenColor(currentShipColor);
			// We draw the ship
			ship.draw();

			// Draw text
			// Set the pen color for text
			StdDraw.setPenColor(StdDraw.WHITE);
			// Draw the lives text aligned to the left
			StdDraw.textLeft(10, 10, "Lives: " + lives);
			// If we are invincible, we tell them how much time is left
			if (isInvincible) {
				StdDraw.textLeft(10, 30, "Invincibility Timer: ["
						+ currentInvFrames + "/" + INVINCIBILITY_INTERVAL + "]");
			}
		} else { // Our lives are 0 or less
			// Set the color to red
			StdDraw.setPenColor(StdDraw.RED);
			// Tell them they lost and how to restart
			StdDraw.textLeft(400, 300, "Game Over");
			StdDraw.textLeft(400, 400, "Press Enter to restart");

			// Check if they pressed enter to restart
			boolean isEnterPressed = StdDraw.isKeyPressed(KeyEvent.VK_ENTER);
			// If they press enter
			if (isEnterPressed) {
				// Set the number of spawning asteroids to 20
				//numberOfAsteroids = 20;
				// Create our asteroids
				asteroids = new Asteroid[numberOfAsteroids];
				// Generate random asteroids
				for (int i = 0; i < numberOfAsteroids; i++) {
					asteroids[i] = Asteroid.generateRandom();
				}
				// Set the number of spawning stars to 20
				//numberOfStars = 30;
				// Create our stars
				stars = new Star[numberOfStars];
				// Generate random stars
				for (int i = 0; i < numberOfStars; i++) {
					stars[i] = Star.generateRandom();
				}
				// Set the lives again to 3
				lives = 3;
			}
		}
		// Set the color of the bullets
		StdDraw.setPenColor(bulletColor);
		// If we have a bullet
		// and our bullet is active
		if (bullet != null && bullet.isActive) {
			// We move the bullet
			bullet.move();
			// We draw it
			bullet.draw();
		}
		// Set the color of the asteroids
		StdDraw.setPenColor(asteroidColor);
		// Go through every asteroid
		for (int i = 0; i < asteroids.length; i++) {
			// Get the current one
			Asteroid current = asteroids[i];
			// Check if it is still alive or active
			if (!current.isActive)
				continue;
			// Otherwise, move the asteroid
			current.move();
			// Draw the asteroid
			current.draw();

			// --== Collision Detection --==
			// Check if it hit the ship
			boolean intersectsShip = Polygon.intersects(ship, current);
			// If it hits the ship
			// and we are NOT invincible
			if (intersectsShip && !isInvincible) {
				// Subtract one life
				lives--;
				// Make us invincible
				isInvincible = true;
			}
			if (bullet != null && bullet.isActive) {
				// Check if it hit the bullet
				boolean intersectsBullet = Polygon.intersects(bullet, current);
				if (intersectsBullet) {
					// The asteroid died and is not active
					current.isActive = false;
					// The bullet also died and is not active
					bullet.isActive = false;
				}
			}
		}
		// Set the color of the stars
		StdDraw.setPenColor(starColor);
		// Go through every star
		for (int i = 0; i < stars.length; i++) {
			// Get the current one
			Star s = stars[i];
			// and draw it
			s.draw();
		}
		// Wait 5ms between frames.
		StdDraw.show(5);
	}

	/**
	 * This generates a random location on the screen
	 * 
	 * @return Random location on screen
	 */
	public static Point generateRandomLocation() {
		double randomX = random.nextInt(800);
		double randomY = random.nextInt(600);
		return new Point(randomX, randomY);
	}

	/**
	 * Generates a random rotation [in degrees]
	 * 
	 * @return Random rotation in degrees
	 */
	public static double generateRandomRotation() {
		double rotation = random.nextInt(360);
		return rotation;
	}

	/**
	 * This is the main loop of the game.
	 */
	public static void main(String[] args) {
		// -----
		// NOTE: You should NOT need to modify this loop.
		// -----
		Asteroids a = new Asteroids();
		while (true) { // In an infinite loop ...
			a.draw(); // Redraw the game.
		}
	}
}
