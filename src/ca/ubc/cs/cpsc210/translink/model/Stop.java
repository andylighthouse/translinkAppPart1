package ca.ubc.cs.cpsc210.translink.model;

import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.util.*;

/**
 * Represents a bus stop with an number, name, location(lat and on),
 * set of routes which stop at this stop and a list of arrivals.
 */


public class Stop implements Iterable<Arrival> {
    private List<Arrival> arrivals;
    private Set<Route> routes;
    private LatLon location;
    private int number;
    private String name;


    /**
     * Constructs a stop with given number, name and location.
     * Set of routes and list of arrivals are empty.
     *
     * @param number the number of this stop
     * @param name   name of this stop
     * @param locn   location of this stop
     */
    public Stop(int number, String name, LatLon locn) {
        this.number = number;
        this.name = name;
        location = locn;
        arrivals = new ArrayList<>();
        routes = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public LatLon getLocn() {
        return location;
    }

    public int getNumber() {
        return number;
    }


    public Set<Route> getRoutes() {
        return Collections.unmodifiableSet(routes);
    }

    /**
     * Add route to set of routes with stops at this stop.
     */
    public void addRoute(Route route) {
        if (!routes.contains(route)) {
            routes.add(route);
            route.addStop(this);
        }
    }

    /**
     * Remove route from set of routes with stops at this stop
     */
    public void removeRoute(Route route) {
        routes.remove(route);
        route.removeStop(this);
    }

    /**
     * Determine if this stop is on a given route
     */
    public boolean onRoute(Route route) {
     return routes.contains(route);
    }

    /**
     * Add bus arrival travelling on a particular route at this stop, sorted by arrival time
     */
    public void addArrival(Arrival arrival) {
        arrivals.add(arrival);
        Collections.sort(arrivals);
    }

    /**
     * Remove all arrivals from this stop
     */
    public void clearArrivals() {
        arrivals.clear();
    }

    /**
     * Two stops are equal if their numbers are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stop)) return false;

        Stop arrivals = (Stop) o;
        return getNumber() == arrivals.getNumber();
    }

    @Override
    public int hashCode() {
        return getNumber();
    }

    @Override
    public Iterator<Arrival> iterator() {
        return arrivals.iterator();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocn(LatLon locn) {
        location = locn;
    }
}
