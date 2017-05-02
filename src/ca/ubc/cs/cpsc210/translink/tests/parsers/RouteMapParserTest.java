package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.RouteMapParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the parser for route pattern map information
 */

// TODO: Write more tests

public class RouteMapParserTest {
    private RouteManager testRouteManager;
    private StopManager testStopManager;

    @Before
    public void setup() {
        testRouteManager = RouteManager.getInstance();
        testRouteManager.clearRoutes();

        testStopManager = StopManager.getInstance();
        testStopManager.clearStops();



    }

    private int countNumRoutePatterns() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();

        int count = 0;
        for (Route r : RouteManager.getInstance()) {
            for (RoutePattern rp : r.getPatterns()) {
                count ++;
            }
        }
        return count;
    }

    @Test
    public void testRouteParserNormal() {
        assertEquals(1232, countNumRoutePatterns());
    }

//    @Test
//    public void test() {
//        Double TOL = 0.0001;
//        RouteMapParser p = new RouteMapParser("someroutemaps.txt");
//        p.parse();
//        RoutePattern pattern = testRouteManager.getRouteWithNumber("C43").getPattern("EB2");
//
//        //System.out.println(testRouteManager.getNumRoutes());
//        //System.out.println(testRouteManager.getRouteWithNumber("C43").getPatterns());
//        //System.out.println(testRouteManager.getRouteWithNumber("C43").getPatterns().get(0).getName());
//        //System.out.println(testRouteManager.getRouteWithNumber("C43").getPatterns().get(1).getName());
//        //System.out.println(testRouteManager.getRouteWithNumber("C43").getPattern("EB2").getPath());
//
//        assertEquals(1, pattern.getPath().size());
//        LatLon latLon = pattern.getPath().get(0);
//        assertEquals(49.21716, latLon.getLatitude(), TOL);
//        assertEquals(-122.667252, latLon.getLongitude(), TOL);
//        //System.out.println(latLon.getLatitude() + "\n" + latLon.getLongitude());
//
//        //NA00-EB3;49.216757;-122.666235;49.216749;-122.666051;49.216821;-122.665898;49.216904;-122.665834;49.217338;-122.665827;49.217172;-122.664836;49.216194;-122.664844;49.216105;-122.664796;49.216024;-122.664707;49.215095;-122.662546;49.214817;-122.661944;49.214555;-122.661496;49.214082;-122.660871;49.212001;-122.658598;49.21339;-122.658532;49.214171;-122.658548;49.215168;-122.658576;49.216176;-122.658593;49.216824;-122.658594;49.217366;-122.658456;49.217811;-122.658262;49.219239;-122.657635;49.221433;-122.656684;49.22173;-122.656649;49.223409;-122.656726;49.223942;-122.656743;49.224666;-122.656787;49.22546;-122.656814;49.226322;-122.656818;49.226311;-122.655587;49.226288;-122.650382;49.226292;-122.644869;49.226284;-122.644258;49.226291;-122.643596;49.226294;-122.641545;49.226304;-122.639451;49.226317;-122.634188;49.226324;-122.631801;49.225558;-122.63181;49.224774;-122.631824;49.224249;-122.631828;49.223905;-122.631822;49.223441;-122.631822;49.223;-122.631824;49.220558;-122.631842;49.219647;-122.631857;49.218387;-122.631877;49.217099;-122.631897;49.215985;-122.631903;49.215104;-122.63193;49.21469;-122.631922;49.213046;-122.631945;49.213166;-122.631314;49.213374;-122.630109;49.213539;-122.629099;49.213672;-122.628405;49.213744;-122.627806;49.213825;-122.626359;49.214007;-122.622911;49.214299;-122.617298;49.214286;-122.616729;49.214253;-122.614608;49.214215;-122.614175;49.214231;-122.614006;49.214612;-122.612506;49.214542;-122.611886;49.214483;-122.611592;49.214387;-122.6114;49.214269;-122.611237;49.214019;-122.611018;49.213846;-122.610829;49.213664;-122.610454;49.213511;-122.609968;49.213079;-122.608204;49.212772;-122.607036;49.212238;-122.604738;49.211515;-122.601716;49.211524;-122.601537;49.211181;-122.600525;49.210713;-122.599383;49.209883;-122.597378;49.209109;-122.595386;49.209426;-122.595115;49.209732;-122.594908;49.210275;-122.594655;49.210633;-122.594574;49.210556;-122.595273;49.210552;-122.595589;49.210602;-122.595956;49.210698;-122.596347;49.210818;-122.596648;49.211089;-122.597094;49.211368;-122.597359;49.212061;-122.597709;49.212345;-122.597925;49.212582;-122.598361;49.212693;-122.598984;49.213717;-122.598981;49.21541;-122.598918;49.215974;-122.598934;49.215965;-122.600122;49.215971;-122.601449;49.216328;-122.601429;49.216929;-122.601543;49.217729;-122.601635;49.218585;-122.601721;49.219081;-122.601722;49.219531;-122.601719;49.219968;-122.601705;49.220463;-122.601689;49.220462;-122.60138;49.220438;-122.600376;49.220427;-122.599257;49.220421;-122.598386;49.220418;-122.597837;49.219623;-122.597862;49.219652;-122.59628;
//    }

}