package bms.sensors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarbonDioxideSensorTest {

    private CarbonDioxideSensor carbonDioxideSensor;
    private int[] readings;

    @Before
    public void setUp() throws Exception {
        readings = new int[]{750, 1000, 1500, 2000, 2500, 5000, 5100};
        carbonDioxideSensor = new CarbonDioxideSensor(readings, 2, 600, 100);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test (expected = IllegalArgumentException.class)
    public void IllegalArgumentConstructor1() {
        carbonDioxideSensor = new CarbonDioxideSensor(readings, 2, -100, 100);
    }

    @Test (expected = IllegalArgumentException.class)
    public void IllegalArgumentConstructor2() {
        carbonDioxideSensor = new CarbonDioxideSensor(readings, 2, 600, -100);
    }

    @Test (expected = IllegalArgumentException.class)
    public void IllegalArgumentConstructor3() {
        carbonDioxideSensor = new CarbonDioxideSensor(readings, 2, 50, 100);
    }


    @Test
    public void getIdealValueTest() {
        int idealValue = carbonDioxideSensor.getIdealValue();
        assertEquals(600,idealValue);
    }

    @Test
    public void getVariationLimitTest() {
        int variationLimit = carbonDioxideSensor.getVariationLimit();
        assertEquals(100,variationLimit);
    }

    @Test
    public void toStringTest() {
        String toString = carbonDioxideSensor.toString();
        assertEquals("TimedSensor: freq=2, readings=750,1000,1500,2000,2500,5000,5100, type=CarbonDioxideSensor, idealPPM=600, varLimit=100", toString);
    }

    @Test
    public void getHazardLevel0() {
        int hazardLevel = carbonDioxideSensor.getHazardLevel();
        assertEquals(0, hazardLevel);
    }

    @Test
    public void getHazardLevelBoundary1() {
        int i = 0;
        while (i < 2) {
            carbonDioxideSensor.elapseOneMinute();
            i++;
        }

        int hazardLevel = carbonDioxideSensor.getHazardLevel();
        assertEquals(25, hazardLevel);
    }


    @Test
    public void getHazardLevel25() {

        int i = 0;
        while (i < 4) {
            carbonDioxideSensor.elapseOneMinute();
            i++;
        }

        int hazardLevel = carbonDioxideSensor.getHazardLevel();
        assertEquals(25, hazardLevel);
    }

    @Test
    public void getHazardLevelBoundary2() {
        int i = 0;
        while (i < 6) {
            carbonDioxideSensor.elapseOneMinute();
            i++;
        }
        int hazardLevel = carbonDioxideSensor.getHazardLevel();
        assertEquals(50, hazardLevel);
    }


    @Test
    public void getHazardLevel50() {
        int i = 0;
        while (i < 8) {
            carbonDioxideSensor.elapseOneMinute();
            i++;
        }

        int hazardLevel = carbonDioxideSensor.getHazardLevel();
        assertEquals(50, hazardLevel);
    }

    @Test
    public void getHazardLevelBoundary3() {
        int i = 0;
        while (i < 10) {
            carbonDioxideSensor.elapseOneMinute();
            i++;
        }
        int hazardLevel = carbonDioxideSensor.getHazardLevel();
        assertEquals(100, hazardLevel);
    }

    @Test
    public void getHazardLevel100() {
        int i = 0;
        while (i < 12) {
            carbonDioxideSensor.elapseOneMinute();
            i++;
        }
        int hazardLevel = carbonDioxideSensor.getHazardLevel();
        assertEquals(100,hazardLevel);
    }

}