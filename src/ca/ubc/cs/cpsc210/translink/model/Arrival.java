package ca.ubc.cs.cpsc210.translink.model;

/**
 * Represents an estimated arrival with time to arrival in minutes, the route, the name of destination, and the status
 * " " = on time,
 * "*" = scheduled time,
 * "-" = late,
 * "+" = early.
 */
public class Arrival implements Comparable<Arrival>{

    private Route route;
    private int timeToStop;
    private String destination;
    private String status;


    /**
     * Constructs a new arrival with the given time to stop (in minutes), destination and platform.
     */
    public Arrival(int timeToStop, String destination, Route route) {
        this.route = route;
        this.timeToStop = timeToStop;
        this.destination = destination;
        status = "";

    }

    /**
     * Get time until bus arrives at stop in minutes.
     */
    public int getTimeToStopInMins() {
        return timeToStop;
    }

    public String getDestination() {
        return destination;
    }

    public Route getRoute() {
        return route;
    }

    /**
     * Order bus arrivals by time until bus arrives at stop
     */
    @Override
    public int compareTo(Arrival arrival) {
        return this.timeToStop - arrival.timeToStop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
