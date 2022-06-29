package bms;

import bms.sensors.NoiseSensor;
import bms.sensors.OccupancySensor;
import bms.sensors.TemperatureSensor;
import bms.sensors.TimedSensor;
import bms.util.TimedItem;
import bms.util.TimedItemManager;
import org.junit.Test;

import java.sql.Time;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TimedItemManagerTest {

    @Test
    public void Test1() {

        TimedItemManager.getInstance();



        int[] readings1 = {8, 9, 42};
        TimedSensor testsensor1 = new OccupancySensor(readings1,3, 21);

        int[] readings2 = {8, 9, 42};
        TimedSensor testsensor2 = new TemperatureSensor(readings2);


        OccupancySensor a = new OccupancySensor(readings1,3, 21);
        TimedSensor test = (TimedSensor) a;
        System.out.println(Arrays.equals(readings1, readings2));

        int[] readings3 = {8, 10, 42};
        TimedSensor testsensor3 = new TemperatureSensor(readings3);

        assertEquals(0, testsensor1.getTimeElapsed());
        assertEquals(0, testsensor2.getTimeElapsed());
        assertEquals(0, testsensor3.getTimeElapsed());


        TimedItemManager.getInstance().elapseOneMinute();
        TimedItemManager.getInstance().elapseOneMinute();

        assertEquals(2, testsensor1.getTimeElapsed());
        assertEquals(2, testsensor2.getTimeElapsed());
        assertEquals(2, testsensor3.getTimeElapsed());


    }
}
