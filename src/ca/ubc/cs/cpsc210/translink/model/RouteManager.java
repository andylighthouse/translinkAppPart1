package ca.ubc.cs.cpsc210.translink.model;

import java.util.*;

/**
 * Manages all routes. Singleton
 */

public class RouteManager implements Iterable<Route> {
    private static RouteManager instance;

    private Map<String, Route> routeMap;

    /**
     * Constructs Route manager with empty collection of routes
     */
    private RouteManager() {
        routeMap = new HashMap<>();
    }

    /**
     * Gets one and only instance of this class
     */
    public static RouteManager getInstance() {
        if (instance == null) {
            instance = new RouteManager();
        }
        return instance;
    }

    /**
     * Get route with given number or create and add it to the collection of all routes
     */
    public Route getRouteWithNumber(String number) {
        if (routeMap.containsKey(number)) {
            return routeMap.get(number);
        }
        Route newRoute = new Route(number);
        routeMap.put(number, newRoute);
        return newRoute;
    }

    /**
     * Overloading for getRouteWithNumber with name param
     */
    public Route getRouteWithNumber(String number, String name) {
        if (routeMap.containsKey(number)) {
            return routeMap.get(number);
        }

        Route newRoute = new Route(number);
        newRoute.setName(name);
        routeMap.put(number, newRoute);
        return newRoute;
    }

    /**
     * Get number of routes managed
     */
    public int getNumRoutes() {
        return routeMap.size();
    }

    @Override
    public Iterator<Route> iterator() {
        return routeMap.values().iterator();
    }

    /**
     * Remove all routes from the route manager
     */
    public void clearRoutes() {
        routeMap.clear();
    }
}
