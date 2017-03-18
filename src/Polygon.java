/*
CLASS: Polygon
DESCRIPTION: A polygon is a sequence of points in space defined by a set of
             such points, an offset, and a rotation. The offset is the
             distance between the origin and the center of the shape.
             The rotation is measured in degrees, 0-360.
USAGE: You are intended to instantiate this class with a set of points that
       defines its shape, and then modify it by repositioning and
       rotating that shape. In defining the shape, the relative positions
       of the points you provide are used, in other words: {(0,1),(1,1),(1,0)}
       is the same shape as {(9,10),(10,10),(10,9)}.
NOTE: You don't need to worry about the "magic math" details.
Original code by Dan Leyzberg and Art Simon.
Modified to use Princeton's stdlib by Rafa Escalante
*/

public class Polygon {
    private Point[] shape;   // An array of points.
    public Point position;   // The offset mentioned above.
    public double rotation;  // Zero degrees is due east.

    /**
     * Creates a new polygon.
     *
     * @param inShape    An array of points that define the polygon's shape.
     * @param inPosition The offset of the polygon, used to reposition the polygon.
     * @param inRotation The rotation of the polygon in degrees.
     */
    public Polygon(Point[] inShape, Point inPosition, double inRotation) {
        shape = inShape;
        position = inPosition;
        rotation = inRotation;

        // Changes the position of this polygon.
        reposition(inPosition);
    }
    /**
     * @author The Omega 3
     * Checks if two polygons are intersecting or touching each other
     * for collision detection
     * @param first First polygon to check
     * @param second Second polygon to check
     * @return True if they are intersecting, false if otherwise
     */
    public static boolean intersects(Polygon first, Polygon second){
    	// We get the points of the first polygon
    	Point[] firstPoints = first.getPoints();
    	// and the second
    	Point[] secondPoints = second.getPoints();

    	// We go through the vertices on the first
    	for(int i = 0; i < firstPoints.length; i++){
    		Point current = firstPoints[i];
    		// If we are inside the second
    		// we are colliding
    		if(second.contains(current))
    			return true;
    	}
    	// Do the same on the vertices of the second
    	for(int i = 0; i < secondPoints.length; i++){
    		Point current = secondPoints[i];
    		if(first.contains(current))
    			return true;
    	}
    	// We didn't find any collisions, return false
    	return false;
    }

    /**
     * Returns the set of points needed to draw the polygon. The offset
     * and the rotation has been applied to the original points.
     *
     * @return The set of points needed to draw the polygon.
     */
    public Point[] getPoints() {
        Point center = findCenter();
        Point[] points = new Point[shape.length];
        for (int i = 0; i < shape.length; i++) {
            Point p = shape[i];
            double x = ((p.x - center.x) * Math.cos(Math.toRadians(rotation)))
                    - ((p.y - center.y) * Math.sin(Math.toRadians(rotation)))
                    + center.x / 2 + position.x;
            double y = ((p.x - center.x) * Math.sin(Math.toRadians(rotation)))
                    + ((p.y - center.y) * Math.cos(Math.toRadians(rotation)))
                    + center.y / 2 + position.y;
            points[i] = new Point(x, y);
        }
        return points;
    }

    /**
     * Determines whether the given point is inside the polygon. Used for
     * collision detection.
     *
     * @param point The point to check for inclusion.
     * @return true if the point collides with the shape; false otherwise.
     */
    public boolean contains(Point point) {
        Point[] points = getPoints();
        double crossingNumber = 0;
        for (int i = 0, j = 1; i < shape.length; i++, j = (j + 1) % shape.length) {
            if ((((points[i].x < point.x) && (point.x <= points[j].x)) ||
                    ((points[j].x < point.x) && (point.x <= points[i].x))) &&
                    (point.y > points[i].y + (points[j].y - points[i].y) /
                            (points[j].x - points[i].x) * (point.x - points[i].x))) {
                crossingNumber++;
            }
        }
        return crossingNumber % 2 == 1;
    }

    /**
     * Rotates the polygon by the given number of degrees.
     *
     * @param degrees The degrees to rotate.
     */
    public void rotate(int degrees) {
        rotation = (rotation + degrees) % 360;
    }

    /** Changes the position of this polygon.
     *
     * @param inPosition The point containing the new x and y position of the polygon.
     */
    public void reposition(Point inPosition) {
        position = inPosition;
        // First, we find the shape's top-most left-most boundary, its origin.
        Point origin = shape[0].clone();
        for (int i = 0; i < shape.length; i++) {
            Point p = shape[i];
            if (p.x < origin.x) origin.x = p.x;
            if (p.y < origin.y) origin.y = p.y;
        }

        // Then, we orient all of its points relative to the real origin.
        for (int i = 0; i < shape.length; i++) {
            shape[i].x -= origin.x;
            shape[i].y -= origin.y;
        }
    }

    /** Draws the polygon to the canvas.
     */
    public void draw() {
        // Grab the rotated points
        Point[] shape = getPoints();
        // Let's extract our array of points into two arrays:
        // One containing just x coordinates and another
        //     containing just y coordinates.
        double[] xPoints = new double[shape.length];
        double[] yPoints = new double[shape.length];
        for(int i = 0; i < shape.length; i++) {
            xPoints[i] = shape[i].x;
            yPoints[i] = shape[i].y;
        }
        // Finally, draw the polygon to the screen.
        StdDraw.polygon(xPoints, yPoints);
    }
    /*
     *   The following methods are private access restricted because, as this access
     *   level always implies, they are intended for use only as helpers of the
     *   methods in this class that are not private. They can't be used anywhere else.
     */

    // "findArea" implements some more magic math.
    private double findArea() {
        double sum = 0;
        for (int i = 0, j = 1; i < shape.length; i++, j = (j + 1) % shape.length) {
            sum += shape[i].x * shape[j].y - shape[j].x * shape[i].y;
        }
        return Math.abs(sum / 2);
    }

    // "findCenter" implements another bit of math.
    private Point findCenter() {
        Point sum = new Point(0, 0);
        for (int i = 0, j = 1; i < shape.length; i++, j = (j + 1) % shape.length) {
            sum.x += (shape[i].x + shape[j].x)
                    * (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
            sum.y += (shape[i].y + shape[j].y)
                    * (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
        }
        double area = findArea();
        return new Point(Math.abs(sum.x / (6 * area)), Math.abs(sum.y / (6 * area)));
    }

}
