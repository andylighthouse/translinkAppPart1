package ca.ubc.cs.cpsc210.translink.util;

import java.awt.geom.Line2D;

/**
 * Compute relationships between points, lines, and rectangles represented by LatLon objects
 */
public class Geometry {
    /**
     * Return true if the point is inside of, or on the boundary of, the rectangle formed by northWest and southeast
     *
     * @param northWest the coordinate of the north west corner of the rectangle
     * @param southEast the coordinate of the south east corner of the rectangle
     * @param point     the point in question
     * @return true if the point is on the boundary or inside the rectangle
     */
    public static boolean rectangleContainsPoint(LatLon northWest, LatLon southEast, LatLon point) {

        double x = point.getLatitude();
        double y = point.getLongitude();

        return (between(northWest.getLatitude(), southEast.getLatitude(), x) &&
                between(southEast.getLongitude(), northWest.getLongitude(), y));
    }

    /**
     * Return true if the rectangle intersects the line
     *
     * @param northWest the coordinate of the north west corner of the rectangle
     * @param southEast the coordinate of the south east corner of the rectangle
     * @param src       one end of the line in question
     * @param dst       the other end of the line in question
     * @return true if any point on the line is on the boundary or inside the rectangle
     */
    public static boolean rectangleIntersectsLine(LatLon northWest, LatLon southEast, LatLon src, LatLon dst) {

        //stackoverflow http://stackoverflow.com/questions/15514906/how-to-check-intersection-between-a-line-and-a-rectangle


        Line2D left = new Line2D.Double(northWest.getLongitude(), northWest.getLatitude(), northWest.getLongitude(), southEast.getLatitude());
        Line2D right = new Line2D.Double(southEast.getLongitude(), northWest.getLatitude(), southEast.getLongitude(), southEast.getLatitude());
        Line2D top = new Line2D.Double(northWest.getLongitude(), northWest.getLatitude(), southEast.getLongitude(), northWest.getLatitude());
        Line2D bottom = new Line2D.Double(northWest.getLongitude(), southEast.getLatitude(), southEast.getLongitude(), southEast.getLatitude());


        Line2D line = new Line2D.Double(src.getLongitude(), src.getLatitude(), dst.getLongitude(), dst.getLatitude()); //the test line


        return ((left.intersectsLine(line) || top.intersectsLine(line) || right.intersectsLine(line) || bottom.intersectsLine(line)) ||  checkMiddle(northWest, southEast, src, dst));
        //InterectLine method does not check if line is complete inside, need to use checkMiddle method too

    }

    private static boolean checkMiddle(LatLon northWest, LatLon southEast, LatLon src, LatLon dst) {
        return ((rectangleContainsPoint(northWest, southEast, src)) && (rectangleContainsPoint(northWest, southEast, dst)));
    }


    /**
     * A utility method that you might find helpful in implementing the two previous methods
     * Return true if x is >= lwb and <= upb
     *
     * @param lwb the lower boundary
     * @param upb the upper boundary
     * @param x   the value in question
     * @return true if x is >= lwb and <= upb
     */
    private static boolean between(double lwb, double upb, double x) {
        return Math.min(lwb, upb) <= x && x <= Math.max(upb, lwb);
    }
}
