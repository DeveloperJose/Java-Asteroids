/**
 * This is our Asteroid class
 */
public class Asteroid extends Polygon {
	/**
	 * Are we alive?
	 */
	public boolean isActive = true;

	/**
	 * Creates a random asteroid in a random location
	 * with a random rotation
	 * @return Random asteroid somewhere in the game
	 */
	public static Asteroid generateRandom() {
		// We create our shape
		Point[] asteroidShape = new Point[11];

		asteroidShape[0] = new Point(200, 100);
		asteroidShape[1] = new Point(190, 110);
		asteroidShape[2] = new Point(190, 130);
		asteroidShape[3] = new Point(205, 140);
		asteroidShape[4] = new Point(210, 135);
		asteroidShape[5] = new Point(215, 130);
		asteroidShape[6] = new Point(230, 120);
		asteroidShape[7] = new Point(240, 100);
		asteroidShape[8] = new Point(230, 90);
		asteroidShape[9] = new Point(210, 93);
		asteroidShape[10] = new Point(205, 97);

		// Generate a random location
		Point location = Asteroids.generateRandomLocation();
		// A random rotation
		double rotation = Asteroids.generateRandomRotation();
		// Just create the asteroid there
		return new Asteroid(asteroidShape, location, rotation);
	}
	/**
	 * Moves the asteroid constantly
	 */
	public void move() {
		double increase = .8; // Number of pixels to move the asteroid
		position.x += increase * Math.cos(Math.toRadians(rotation));
		position.y += increase * Math.sin(Math.toRadians(rotation));
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
	}

	public Asteroid(Point[] asteroidShape, Point inPosition, double rotation) {
		super(asteroidShape, inPosition, rotation);
	}

}
