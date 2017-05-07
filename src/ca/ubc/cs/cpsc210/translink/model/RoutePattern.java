package ca.ubc.cs.cpsc210.translink.model;

import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A description of one pattern of a route
 * Each pattern has a name, destination, direction, list of points (of class LatLon), and Route
 */


public class RoutePattern {
    private Route route;
    private List<LatLon> listOfLatLons;
    private String name;
    private String destination;
    private String direction;


    /**
     * Construct a new RoutePattern with the given information
     */
    public RoutePattern(String name, String destination, String direction, Route route) {
        this.name = name;
        this.destination = destination;
        this.direction = direction;
        this.route = route;
        listOfLatLons = new ArrayList<>();
        route.addPattern(this);
    }


    public String getName() {
        return name;
    }


    public String getDestination() {
        return destination;
    }


    public String getDirection() {
        return direction;
    }

    /**
     * Two route patterns are equal if their names are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoutePattern that = (RoutePattern) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }


    /**
     * Set the pattern path, which is a list of coordinates
     */
    public void setPath(List<LatLon> path) {
        listOfLatLons = path;
    }

    /**
     * Return the list of coordinates making up this pattern
     */
    public List<LatLon> getPath() {
        return Collections.unmodifiableList(listOfLatLons);
    }


    public void setDirection(String direction) {
        this.direction = direction;
    }


    public void setDestination(String destination) {
        this.destination = destination;
    }
}
