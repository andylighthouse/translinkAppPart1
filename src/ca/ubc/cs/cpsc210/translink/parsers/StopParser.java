package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A parser for the data returned by Translink stops query
 */
public class StopParser {

    private String filename;
    private boolean missingData;

    public StopParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse stop data from the stops.json file and add all stops to stop manager.
     */
    public void parse() throws IOException, StopDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);
            parseStops(dataProvider.dataSourceToString());
    }

    /**
     * Parse stop information from JSON response produced by Translink.
     * Stores all stops and routes found in the StopManager and RouteManager.
     * throw JSONException when data does not have expected format
     *If a JSONException is thrown, no stops should be added to the stop manager
     * throw StopDataMissingException when data is missing Name, StopNo, Routes or location (Latitude or Longitude) elements for any stop
     * If a StopDataMissingException is thrown, all correct stops are first added to the stop manager.
     */

    public void parseStops(String jsonResponse)
            throws JSONException, StopDataMissingException {

        missingData = false;

        JSONArray stops = new JSONArray(jsonResponse);

        for (int index = 0; index < stops.length(); index++) {
            JSONObject stop = stops.getJSONObject(index);
            parseStop(stop);
        }

        if(missingData)
            throw new StopDataMissingException();
    }

    private void parseStop(JSONObject stop) throws JSONException, StopDataMissingException {
        if (stop.has("StopNo") && stop.has("Name") && stop.has("Latitude") && stop.has("Longitude") && stop.has("Routes")) {
            String stopName = stop.getString("Name");
            Integer stopNumber = stop.getInt("StopNo");
            Double latitude = stop.getDouble("Latitude");
            Double longitude = stop.getDouble("Longitude");
            String routes = stop.getString("Routes");

            Stop currentStop = StopManager.getInstance().getStopWithNumber(stopNumber, stopName, new LatLon(latitude, longitude));

            addRoutes(currentStop, routes);

        } else {
            // if the Name, StopNo, Routes or location is missing
            missingData = true;
        }

    }


    private void addRoutes(Stop currentStop, String routes) throws JSONException {

        String[] route = routes.split(", ");
        for (int i = 0; i < route.length; i++) {
            Route currentRoute = RouteManager.getInstance().getRouteWithNumber(route[i]);
            currentRoute.addStop(currentStop);
        }

    }


}
