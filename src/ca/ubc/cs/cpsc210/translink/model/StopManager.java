package ca.ubc.cs.cpsc210.translink.model;

import ca.ubc.cs.cpsc210.translink.model.exception.StopException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import ca.ubc.cs.cpsc210.translink.util.SphericalGeometry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Manages all bus stops. Singleton
 */

public class StopManager implements Iterable<Stop> {
    public static final int RADIUS = 10000;
    private static StopManager instance;
    private Map<Integer, Stop> stopMap;
    private Stop selectedStop;

    /**
     * Constructs stop manager with empty collection of stops and null as the selected stop
     */
    private StopManager() {
        stopMap = new HashMap<>();
        selectedStop = null;
    }

    /**
     * Gets one and only instance of this class
     */
    public static StopManager getInstance() {
        if (instance == null) {
            instance = new StopManager();
        }
        return instance;
    }

    public Stop getSelected() {
        return selectedStop;
    }

    /**
     * Get stop with given number, or make new with empty string as name
     * and a default location somewhere in the lower mainland as its location.
     * In this case, the correct name and location of the stop will be provided later
     */
    public Stop getStopWithNumber(int number) {
        if (stopMap.containsKey(number)) {
            return stopMap.get(number);
        } else {
            Stop newStop = new Stop(number, "", new LatLon(49.2611752, 123.2492004));
            stopMap.put(number, newStop);
            return newStop;
        }
    }

    /**
     * Overloading for the getStopWithNumber method
     */
    public Stop getStopWithNumber(int number, String name, LatLon locn) {
        if (stopMap.containsKey(number)) {
            return stopMap.get(number);
        } else {
            Stop newStop = new Stop(number, name, locn);
            stopMap.put(number, newStop);
            return newStop;
        }
    }

    /**
     * Set the stop selected by user
     * throw StopException when stop manager doesn't contain selected stop
     */
    public void setSelected(Stop selected) throws StopException {
        if (!stopMap.containsValue(selected)) {
            throw new StopException("No such stop");
        }
        this.selectedStop = selected;
    }

    /**
     * Clear selected stop and set to null
     */
    public void clearSelectedStop() {
        selectedStop = null;
    }


    public int getNumStops() {
        return stopMap.size();
    }

    /**
     * Remove all stops from stop manager
     */
    public void clearStops() {
        stopMap.clear();
    }

    /**
     * Find nearest stop to given point.  Returns null if no stop is closer than RADIUS metres.
     */
    public Stop findNearestTo(LatLon pt) {
        double closestDistance = RADIUS;
        Stop nearestStop = null;

        for (Stop currentStop : stopMap.values()) {
            double currentDistanceToStop = SphericalGeometry.distanceBetween(currentStop.getLocn(), pt);
            if (currentDistanceToStop < closestDistance) {
                closestDistance = currentDistanceToStop;
                nearestStop = currentStop;
            }
        }
        return nearestStop;

    }

    @Override
    public Iterator<Stop> iterator() {
        return stopMap.values().iterator();
    }
}
