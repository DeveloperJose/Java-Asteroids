/**
 * Our star class
 */
public class Star extends Polygon {
	/**
	 * Generates a random star somewhere on the screen
	 * @return A random star
	 */
	public static Star generateRandom() {
		// Set the shape of the star
		Point[] starShape = new Point[3];

		starShape[0] = new Point(300, 400);
		starShape[1] = new Point(300, 401);
	    starShape[2] = new Point(301, 400);


		// Then generate a random location
		Point initialLocation = Asteroids.generateRandomLocation();
		// and random rotation
		double rotation = Asteroids.generateRandomRotation();
		// and return that star
		return new Star(starShape, initialLocation, rotation);
	}

	public Star(Point[] starShape, Point inPosition, double rotation) {
		super(starShape, inPosition, rotation);
	}
}
