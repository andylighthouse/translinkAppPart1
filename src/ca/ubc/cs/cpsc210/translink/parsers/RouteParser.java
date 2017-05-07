package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Parse route information in JSON format.
 */
public class RouteParser {
    private String filename;
    private boolean missingData;

    public RouteParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse route data from the allroutes.json file and add all route to the route manager.
     * * throw JSONException when data does not have expected format
     * If a JSONException is thrown, no stops should be added to the stop manager
     * throw RouteDataMissingException when required data is missing
     * If a RouteDataMissingException is thrown, all correct routes are first added to the route manager.
     */
    public void parse() throws IOException, RouteDataMissingException, JSONException {
        missingData = false;
        DataProvider dataProvider = new FileDataProvider(filename);

        parseRoutes(dataProvider.dataSourceToString());

        if (missingData) {
            throw new RouteDataMissingException();
        }

    }


    public void parseRoutes(String jsonResponse) throws RouteDataMissingException, JSONException {

        JSONArray data = new JSONArray(jsonResponse);

        for (int index = 0; index < data.length(); index++) {
            JSONObject route = data.getJSONObject(index);
            parseCurrentRoute(route);
        }
    }

    /**
     * set missingData flag to true if missing RouteNo, Name, or Patterns element for any route
     */
    public void parseCurrentRoute(JSONObject route) throws RouteDataMissingException, JSONException {
        if (route.has("Name") && route.has("RouteNo") && route.has("Patterns")) {

            String name = route.getString("Name");
            String number = route.getString("RouteNo");
            JSONArray patterns = route.getJSONArray("Patterns");

            Route currentRoute = RouteManager.getInstance().getRouteWithNumber(number, name);
            loopPattern(patterns, currentRoute);
        } else {
            missingData = true;
        }


    }


    private void loopPattern(JSONArray patterns, Route currentRoute) throws JSONException, RouteDataMissingException {
        for (int index = 0; index < patterns.length(); index++) {
            JSONObject currentPattern = patterns.getJSONObject(index);
            parseCurrentPattern(currentPattern, currentRoute);
        }
    }

    /**
     * set missingData flag to true if missing PatternNo, Destination, or Direction element for any route pattern
     */
    private void parseCurrentPattern(JSONObject currentPattern, Route currentRoute) throws RouteDataMissingException, JSONException {
        if (currentPattern.has("Destination") && currentPattern.has("Direction") && currentPattern.has("PatternNo")) {

            String destination = currentPattern.getString("Destination");
            String direction = currentPattern.getString("Direction");
            String patternNo = currentPattern.getString("PatternNo");
            RoutePattern pattern = currentRoute.getPattern(patternNo, destination, direction);
        } else {
            missingData = true;
        }

    }
}
