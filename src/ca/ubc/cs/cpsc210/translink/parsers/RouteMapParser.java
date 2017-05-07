package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser for routes stored in a compact format in a  allroutemaps.txt file
 * Translink might modify routes, then the allroutemaps.txt file must be updated
 */
public class RouteMapParser {
    private String fileName;

    public RouteMapParser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Parse the route map txt file
     */
    public void parse() {
        DataProvider dataProvider = new FileDataProvider(fileName);
        try {
            String c = dataProvider.dataSourceToString();
            if (!c.equals("")) {
                int posn = 0;
                while (posn < c.length()) {
                    int endposn = c.indexOf('\n', posn);
                    String line = c.substring(posn, endposn);
                    parseOnePattern(line);
                    posn = endposn + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Each line begins with a capital N, which is not part of the route number, followed by the
     * bus route number, a dash, the pattern name, a semicolon, and a series of 0 or more
     * numbers corresponding to the latitude and longitude of a point in the pattern,
     * separated by semicolons.
     */
    private void parseOnePattern(String str) {
        // seperate the long string by the ;
        String[] line = str.split(";");

        String routeNumber;
        String patternName;
        List<LatLon> location = new ArrayList<>();

        //sample:
        // N106-WB3A;49.224089;-122.940994;49.222913;-122.943226;49.222819;-122.943404;49.221934;-122.94505;49.220911;-122.946958;49.219872;-122.948896;49.219204;-122.950142;49.218901;-122.950705;49.218507;-122.951344;49.218316;-122.951539;49.218052;-122.951813;49.217019;-122.953669;49.215217;-122.957033;49.214349;-122.958619;49.214114;-122.959051;49.214067;-122.959136;49.213069;-122.957816;49.212564;-122.958661;49.212408;-122.958501;49.212089;-122.958092;49.211968;-122.958244;49.211894;-122.958444;49.211901;-122.958599;49.211996;-122.958764;49.212124;-122.958806;49.212265;-122.958728;49.212408;-122.958501;49.212564;-122.958661;49.213068;-122.957815;49.213116;-122.957734;49.213784;-122.958619;49.21398;-122.958726;49.214168;-122.958738;49.214349;-122.958619;49.215216;-122.957032;49.21702;-122.95367;49.218052;-122.951813;49.218316;-122.951539;49.218347;-122.952196;49.218372;-122.952712;49.218394;-122.954927;49.218409;-122.955718;49.218429;-122.957522;49.218433;-122.959379;49.218436;-122.960987;49.218433;-122.959379;49.218436;-122.960987;49.218451;-122.964775;49.218459;-122.965071;49.218518;-122.965468;49.21907;-122.96804;49.219665;-122.970798;49.219926;-122.972036;49.220271;-122.973476;49.220582;-122.97476;49.220953;-122.976121;49.221015;-122.97635;49.221144;-122.976844;49.221368;-122.977543;49.221812;-122.978935;49.22195;-122.979416;49.222208;-122.98022;49.222377;-122.98075;49.222475;-122.981033;49.222927;-122.982478;49.223126;-122.98309;49.223491;-122.984232;49.223591;-122.98455;49.225001;-122.988934;49.22545;-122.990325;49.225847;-122.991551;49.226717;-122.99422;49.227003;-122.995092;49.227438;-122.996408;49.22785;-122.997713;49.228382;-122.99935;49.229501;-123.002652;49.229058;-123.00314;49.228694;-123.003688;49.227939;-123.004471;49.226821;-123.005648;49.225973;-123.003757;49.226195;-123.003503;49.226341;-123.003314;
        //Each line begins with a capital N, which is not part of the route number
        if (line[0].charAt(0) == 'N') {
            int endOfRouteNumberIndex = line[0].indexOf("-");

            //106
            routeNumber = line[0].substring(1, endOfRouteNumberIndex);

            //WB3A
            patternName = line[0].substring(endOfRouteNumberIndex + 1);

            //add lat and long
            for (int i = 1; i < line.length; i += 2) {
                location.add(new LatLon(Double.parseDouble(line[i]), Double.parseDouble(line[i + 1])));
            }
            storeRouteMap(routeNumber, patternName, location);
        }

    }

    /**
     * Store the parsed pattern into the named route
     */
    private void storeRouteMap(String routeNumber, String patternName, List<LatLon> elements) {
        Route r = RouteManager.getInstance().getRouteWithNumber(routeNumber);
        RoutePattern rp = r.getPattern(patternName);
        rp.setPath(elements);
    }
}
