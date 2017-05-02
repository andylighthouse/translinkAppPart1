package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Andy on 3/13/17.
 */
public class StopTest {
    private Stop testStop1;
    private Stop testStop2;
    private Stop testStop3;



    @Before
    public void runBefore(){
        testStop1 = new Stop(1, "dest1", null);
        testStop2 = new Stop(2, "dest2", null);
        testStop3 = new Stop(1, "dest1", null);
    }

    @Test
    public void equalTest()
    {
        assertFalse(testStop1.equals(testStop2));
        assertTrue(testStop1.equals(testStop3));
    }

    @Test
    public void onRouteTest()
    {
        Route r1 = new Route("route1");
        Route r2 = new Route("route2");

        r1.addStop(testStop1);
        r1.addStop(testStop2);

        r2.addStop(testStop3);

        assertTrue(testStop1.onRoute(r1));
        assertTrue(testStop2.onRoute(r1));

        assertTrue(testStop3.onRoute(r2));
        assertFalse(testStop2.onRoute(r2));
    }

}
