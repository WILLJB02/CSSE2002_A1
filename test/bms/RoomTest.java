package bms;

import bms.exceptions.DuplicateSensorException;
import bms.room.Room;
import bms.room.RoomType;
import bms.sensors.*;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.*;

public class RoomTest {

    @Test
    public void roomTest() throws DuplicateSensorException {
        Room testRoom = new Room(1, RoomType.STUDY, 12.12345678);

        //getRoomNumber
        assertEquals(1, testRoom.getRoomNumber());
        //getArea
        assertEquals(12.12345678, testRoom.getArea(), 0.01);
        //getMinArea
        assertEquals(5, Room.getMinArea());
        //GetRoomType
        assertEquals(RoomType.STUDY, testRoom.getType());

        //setFireDrill and FireDrillOngoing
        assertEquals(false, testRoom.fireDrillOngoing());
        testRoom.setFireDrill(true);
        assertEquals(true, testRoom.fireDrillOngoing());

        //getSensors
        System.out.println(testRoom.getSensors());

        int[] readings = {67, 75, 82};
        TimedSensor sensor1 = new CarbonDioxideSensor(readings, 2, 300,150);
        TimedSensor sensor2 = new NoiseSensor(readings, 2);
        TimedSensor sensor3 = new OccupancySensor(readings, 2, 20);
        TimedSensor sensor4 = new TemperatureSensor(readings);
        TimedSensor sensor5 = new OccupancySensor(readings, 3, 25);




        testRoom.addSensor(sensor4);
        testRoom.addSensor(sensor3);
        testRoom.addSensor(sensor2);
        testRoom.addSensor(sensor1);

        //getSensors
        System.out.println(testRoom.getSensors());

        assertEquals(sensor2, testRoom.getSensor("NoiseSensor"));

        assertEquals("Room #1: type=STUDY, area=12.12m^2, sensors=4", testRoom.toString());
        System.out.println(testRoom);
    }

    @Test (expected = DuplicateSensorException.class)
    public void roomTest2() throws DuplicateSensorException {
        Room testRoom = new Room(1, RoomType.STUDY, 12);

        int[] readings = {67, 75, 82};
        TimedSensor sensor1 = new CarbonDioxideSensor(readings, 2, 300,150);
        TimedSensor sensor2 = new NoiseSensor(readings, 2);
        TimedSensor sensor3 = new OccupancySensor(readings, 2, 20);
        TimedSensor sensor4 = new TemperatureSensor(readings);
        TimedSensor sensor5 = new OccupancySensor(readings, 3, 25);
        testRoom.addSensor(sensor4);
        testRoom.addSensor(sensor3);
        testRoom.addSensor(sensor2);
        testRoom.addSensor(sensor1);
        testRoom.addSensor(sensor5);

    }
}
