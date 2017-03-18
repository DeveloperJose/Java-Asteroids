/**
 * Our bullet class
 */
public class Bullet extends Polygon {
	/**
	 * Are we alive?
	 */
	public boolean isActive = true;
	/**
	 * Generates a bullet where you want it! :D
	 * @param initialLocation Location where you want the bullet
	 * @param rotation Rotation for the bullet
	 * @return Bullet where you want it
	 */
	public static Bullet generate(Point initialLocation, double rotation) {
		// Create the shape of the bullet
		Point[] bulletShape = new Point[4];

		bulletShape[0] = new Point(440, 320);
		bulletShape[1] = new Point(400, 340);
		bulletShape[2] = new Point(420, 320);
		bulletShape[3] = new Point(400, 300);
		// Return the bullet
		// Don't forget to clone the location
		// or you're going to be controlling the ship too
		return new Bullet(bulletShape, initialLocation.clone(), rotation);
	}
	/**
	 * Moves the bullet forward until it leaves the screen
	 */
	public void move() {
		double increase = 10;
		position.x += increase * Math.cos(Math.toRadians(rotation));
		position.y += increase * Math.sin(Math.toRadians(rotation));

		// If it is out of bounds, stop drawing it. We died
		if (position.x > 800 || position.x < 0 || position.y > 600 || position.y < 0)
			isActive = false;
	}

	public Bullet(Point[] bulletShape, Point inPosition, double rotation) {
		super(bulletShape, inPosition, rotation);
	}
}
