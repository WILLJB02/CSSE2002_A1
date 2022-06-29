package bms;

import bms.sensors.NoiseSensor;
import bms.sensors.OccupancySensor;
import bms.sensors.TemperatureSensor;
import bms.sensors.TimedSensor;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.*;

public class SensorsTests {

    @Test
    public void TimedSensorTest1() {
        int[] readings = {67, 75, 82};
        TimedSensor testsensor1 = new NoiseSensor(readings,2);

        assertEquals(2,testsensor1.getUpdateFrequency());

        assertEquals(0, testsensor1.getTimeElapsed());
        assertEquals(67, testsensor1.getCurrentReading());
        testsensor1.elapseOneMinute();
        assertEquals(1, testsensor1.getTimeElapsed());
        assertEquals(67, testsensor1.getCurrentReading());
        testsensor1.elapseOneMinute();
        assertEquals(2, testsensor1.getTimeElapsed());
        assertEquals(75, testsensor1.getCurrentReading());
        testsensor1.elapseOneMinute();
        testsensor1.elapseOneMinute();
        testsensor1.elapseOneMinute();
        testsensor1.elapseOneMinute();
        assertEquals(6, testsensor1.getTimeElapsed());
        assertEquals(67, testsensor1.getCurrentReading());
    }

    @Test (expected = IllegalArgumentException.class)
    public void TimedSensorTest2() {
        int[] readings = {67, 75, 82};
        TimedSensor testsensor1 = new NoiseSensor(readings,0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void TimedSensorTest3() {
        int[] readings = {67, 75, 82};
        TimedSensor testsensor1 = new NoiseSensor(readings,6);
    }

    @Test (expected = IllegalArgumentException.class)
    public void TimedSensorTest4() {
        TimedSensor testsensor1 = new NoiseSensor(null,6);
    }

    @Test (expected = IllegalArgumentException.class)
    public void TimedSensorTest5() {
        int[] readings = {};
        TimedSensor testsensor1 = new NoiseSensor(readings,6);
    }

    @Test (expected = IllegalArgumentException.class)
    public void TimedSensorTest6() {
        int[] readings = {5,6,-1,8};
        TimedSensor testsensor1 = new NoiseSensor(readings,6);
    }

    @Test
    public void NoiseSensorTest() {
        int[] readings = {67, 75, 82};
        NoiseSensor testsensor1 = new NoiseSensor(readings,2);

        //calculateRelativeLoudness
        System.out.println(testsensor1.calculateRelativeLoudness());
        assertEquals(0.8123, testsensor1.calculateRelativeLoudness(), 0.01);
        testsensor1.elapseOneMinute();
        testsensor1.elapseOneMinute();
        testsensor1.elapseOneMinute();
        testsensor1.elapseOneMinute();
        System.out.println(testsensor1.calculateRelativeLoudness());
        assertEquals(2.2974, testsensor1.calculateRelativeLoudness(), 0.01);


        //test getHazardLevel
        //test case where should round up
        assertEquals(100, testsensor1.getHazardLevel());
        testsensor1.elapseOneMinute();
        testsensor1.elapseOneMinute();
        assertEquals(81, testsensor1.getHazardLevel());

        //test toString
        assertEquals("TimedSensor: freq=2, readings=67,75,82, type=NoiseSensor", testsensor1.toString());
    }

    @Test
    public void OccupancySensorTest() {
        int[] readings = {8, 9, 42};
        OccupancySensor testsensor1 = new OccupancySensor(readings,3, 21);

        //get Capacity Test
        assertEquals(21, testsensor1.getCapacity());

        //getHazardLevel
        assertEquals(38, testsensor1.getHazardLevel());
        testsensor1.elapseOneMinute();
        testsensor1.elapseOneMinute();
        testsensor1.elapseOneMinute();
        assertEquals(43, testsensor1.getHazardLevel());
        testsensor1.elapseOneMinute();
        testsensor1.elapseOneMinute();
        testsensor1.elapseOneMinute();
        assertEquals(100, testsensor1.getHazardLevel());

        //test to String
        assertEquals("TimedSensor: freq=3, readings=8,9,42, type=OccupancySensor, capacity=21", testsensor1.toString());
    }

    @Test (expected = IllegalArgumentException.class)
    public void OccupancySensorConstructorTest() {
        int[] readings = {8, 9, 42};
        OccupancySensor testsensor1 = new OccupancySensor(readings,3, -1);
    }

    @Test
    public void TemperatureSensorTest() {
        int[] readings = {5, 68, 69};
        TemperatureSensor testsensor1 = new TemperatureSensor(readings);

        assertEquals(1, testsensor1.getUpdateFrequency());


        assertEquals(0,testsensor1.getHazardLevel());
        testsensor1.elapseOneMinute();
        assertEquals(100,testsensor1.getHazardLevel());
        testsensor1.elapseOneMinute();
        assertEquals(100,testsensor1.getHazardLevel());

        assertEquals("TimedSensor: freq=1, readings=5,68,69, type=TemperatureSensor", testsensor1.toString());


    }


}
