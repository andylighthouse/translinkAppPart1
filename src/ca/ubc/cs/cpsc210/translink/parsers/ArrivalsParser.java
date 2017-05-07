package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A parser for the data returned by the Translink arrivals at a stop query
 */
public class ArrivalsParser {

    private static boolean missingSchedule;

    /**
     * From the file arrivals.json
     * Throw JSONException JSON response does not have expected format
     * throws ArrivalsDataMissingException when any required data is missing
     */
    public static void parseArrivals(Stop stop, String jsonResponse) throws JSONException, ArrivalsDataMissingException {
        missingSchedule = false;

        JSONArray routes = new JSONArray(jsonResponse);
        //System.out.println(routes);

        for (int index = 0; index < routes.length(); index++) {
            JSONObject currentRoute = routes.getJSONObject(index);
            parseSchedule(stop, currentRoute);
        }
        // check the flag
        if (missingSchedule) {
            throw new ArrivalsDataMissingException();
        }
    }

    /**
     * Each Json object must have a RouteNo and Schedules
     * if any required data is missing, the arrival is not added to the stop and set missingSchedule flag to true
     */
    private static void parseSchedule(Stop stop, JSONObject currentRoute) throws JSONException, ArrivalsDataMissingException {
        if (currentRoute.has("RouteNo") && currentRoute.has("Schedules")) {
            String routeNumber = currentRoute.getString("RouteNo");
            JSONArray schedules = currentRoute.getJSONArray("Schedules");

            Route route = RouteManager.getInstance().getRouteWithNumber(routeNumber);
            parseSchedule(schedules, route, stop);
        } else {
            missingSchedule = true;
        }
    }

    /**
     * Each schedule must have an ExpectedCountdown, ScheduleStatus, and Destination.
     * if any required data is missing, the arrival is not added to the stop and set missingSchedule flag to true
     */
    private static void parseSchedule(JSONArray schedules, Route route, Stop stop) throws JSONException, ArrivalsDataMissingException {

        int count = 0;
        for (int i = 0; i < schedules.length(); i++) {
            JSONObject schedule = schedules.getJSONObject(i);
            if (schedule.has("ExpectedCountdown") && schedule.has("Destination") && schedule.has("ScheduleStatus")) {

                int expectedCountdown = schedule.getInt("ExpectedCountdown");
                String destination = schedule.getString("Destination");
                String scheduleStatus = schedule.getString("ScheduleStatus");

                Arrival currentArrival = new Arrival(expectedCountdown, destination, route);
                currentArrival.setStatus(scheduleStatus);
                stop.addArrival(currentArrival);
                count++;
            }
        }
        //make sure it is not empty
        if (count == 0) {
            missingSchedule = true;
        }
    }
}
