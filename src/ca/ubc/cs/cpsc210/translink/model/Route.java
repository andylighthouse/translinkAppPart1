package ca.ubc.cs.cpsc210.translink.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a bus route with a route number, name, list of stops, and list of RoutePatterns.
 */
public class Route implements Iterable<Stop> {
    private List<Stop> stops;
    private List<RoutePattern> routePatterns;
    private String name;
    private String number;


    /**
     * Constructs a route with given number.
     */
    public Route(String number) {
        name = "";
        stops = new ArrayList<>();
        routePatterns = new ArrayList<>();
        this.number = number;

    }


    public String getNumber() {
        return number;
    }


    public void setName(String name) {
        this.name = name;
    }

    /**
     * Add the pattern to the route if it is not already there
     */
    public void addPattern(RoutePattern pattern) {
        if (!routePatterns.contains(pattern)) {
            routePatterns.add(pattern);
        }
    }

    /**
     * Add stop to route.
     */
    public void addStop(Stop stop) {
        if (!stops.contains(stop)) {
            stops.add(stop);
            stop.addRoute(this);
        }
    }

    /**
     * Remove stop from route
     */
    public void removeStop(Stop stop) {
        if (stops.remove(stop)) {
            stops.remove(stop);
            stop.removeRoute(this);
        }

    }

    /**
     * Return all the stops in this route, in the order in which they were added, make it unmodifiable
     */
    public List<Stop> getStops() {
        return Collections.unmodifiableList(stops);
    }

    /**
     * Determines if this route has a stop at a given stop
     */
    public boolean hasStop(Stop stop) {
        return stops.contains(stop);
    }

    /**
     * Two routes are equal if their numbers are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;

        Route route = (Route) o;
        return number.equals(route.number);
    }


    @Override
    public int hashCode() {
        return number.hashCode();
    }


    @Override
    public Iterator<Stop> iterator() {
        return stops.iterator();
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Route " + getNumber();
    }

    /**
     * Return the pattern with the given name. If it does not exist, then create it and add it to the patterns.
     * Must update the destination and direction of the pattern in both cases.
     */
    public RoutePattern getPattern(String patternName, String destination, String direction) {
        RoutePattern pattern = getPattern(patternName);
        pattern.setDirection(direction);
        pattern.setDestination(direction);
        return pattern;
    }

    /**
     * Return the pattern with the given name. If it does not exist, then create it and add it to the patterns
     * with empty strings as the destination and direction.
     */
    public RoutePattern getPattern(String patternName) {
        for (RoutePattern rp : routePatterns) {
            if (rp.getName().equals(patternName)) {
                return rp;
            }
        }
        return new RoutePattern(patternName, "", "", this);
    }

    /**
     * Return all the patterns for this route as a unmodifiable list
     */
    public List<RoutePattern> getPatterns() {
        return Collections.unmodifiableList(routePatterns);
    }
}
