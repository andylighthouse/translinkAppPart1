package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Andy on 3/11/17.
 */
public class RouteTest {
    private Route testRoute;

    @Before
    public void runBefore() {
        testRoute = new Route("");
    }

    @Test
    public void testPattern() {
        RoutePattern testPattern1 = new RoutePattern("name1", "dest1", "dir1", testRoute);
        RoutePattern testPattern2 = new RoutePattern("name2", "dest2", "dir2", testRoute);

        RoutePattern getPattern1 = testRoute.getPattern("name1", "dest1", "dir1");
        RoutePattern getPattern2 = testRoute.getPattern("name2", "dest2", "dir2");
        RoutePattern getPattern3 = testRoute.getPattern("name3", "dest3", "dir3");

        List<RoutePattern> routePatterns = testRoute.getPatterns();

        assertEquals(3, routePatterns.size());

        assertTrue(getPattern1.equals(testPattern1));
        assertTrue(getPattern2.equals(testPattern2));
        assertFalse(getPattern3.equals(testPattern2));

    }

    @Test
    public void testStop() {
        Stop s1 = new Stop(11, "Stop1", null);
        Stop s2 = new Stop(22, "Stop2", null);
        Stop s3 = new Stop(33, "Stop2", null);



        testRoute.addStop(s1);
        testRoute.addStop(s2);

        assertEquals(2, testRoute.getStops().size());
        testRoute.addStop(s1);
        assertEquals(2, testRoute.getStops().size());

        assertTrue(testRoute.hasStop(s1));
        assertTrue(testRoute.hasStop(s2));
        assertFalse(testRoute.hasStop(s3));

    }

    @Test
    public void equalTest()
    {
        Route route1 = new Route("name1");
        Route route2 = new Route("name2");
        Route route3 = new Route("name1");

        assertFalse(route1.equals(route2));
        assertTrue(route1.equals(route3));
    }
}
