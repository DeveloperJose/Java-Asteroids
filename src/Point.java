/*
CLASS: Point
DESCRIPTION: Ah, if only real-life classes were this straight-forward. We'll
             use 'Point' throughout the program to store and access
             coordinates.
Original code by Dan Leyzberg and Art Simon.
Modified to use Princeton's stdlib by Rafael Escalante-Ruiz.
*/
class Point {
    // The fields below should be private, but we made them
    // public to make the code that uses them more readable.
    public double x, y;

    /** Creates a new point.
     *
     * @param inX The x coordinate of the point.
     * @param inY The y coordinate of the point.
     */
    public Point(double inX, double inY) {
        x = inX;
        y = inY;
    }

    /** Returns a copy of the point.
     *
     * @return A copy of the point object.
     */
    public Point clone() {
        return new Point(x,y);
    }
}