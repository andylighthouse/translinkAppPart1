package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Andy on 3/11/17.
 */
public class RoutePatternTest {
    Route r;
    RoutePattern rp1;
    RoutePattern rp2;
    RoutePattern rp3;


    @Before
    public void runBefore() {
        Route r = new Route("Test Route");
        rp1 = new RoutePattern("pattern1", "dest1", "dir1", r);
        rp2 = new RoutePattern("pattern1", "dest1", "dir1", r);
        rp3 = new RoutePattern("pattern3", "dest3", "dir3", r);
    }

    @Test
    public void testEqual() {
        assertTrue(rp1.equals(rp2));
        assertFalse(rp1.equals(rp3));
    }


}
