package bms;

import bms.building.Building;
import bms.exceptions.*;
import bms.floor.Floor;
import bms.room.Room;
import bms.room.RoomType;
import org.junit.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.*;

public class BuildingTest {
    @Test (expected = FireDrillException.class)
    public void BuildingTest() throws FireDrillException {
        Building testBuilding = new Building("Test");

        testBuilding.fireDrill(null);

    }

    @Test (expected = FireDrillException.class)
    public void BuildingTest1() throws FireDrillException, DuplicateFloorException, NoFloorBelowException, FloorTooSmallException {
        Building testBuilding = new Building("Test");
        Floor floor1 = new Floor(1,10,10);
        Floor floor2 = new Floor(2,10,10);
        Floor floor3 = new Floor(3,10,10);
        testBuilding.addFloor(floor1);
        testBuilding.addFloor(floor2);
        testBuilding.addFloor(floor3);

        testBuilding.fireDrill(null);
    }

    @Test
    public void BuildingTest2() throws FireDrillException, DuplicateFloorException, NoFloorBelowException, FloorTooSmallException, InsufficientSpaceException, DuplicateRoomException {
        Building testBuilding = new Building("Test");
        Floor floor1 = new Floor(1,10,10);
        Room room5 = new Room(1, RoomType.OFFICE, 6);
        floor1.addRoom(room5);
        Floor floor2 = new Floor(2,10,10);
        Room room1 = new Room(1, RoomType.STUDY, 6);
        floor2.addRoom(room1);
        Floor floor3 = new Floor(3,10,10);
        Room room2 = new Room(1, RoomType.STUDY, 6);
        Room room3 = new Room(2, RoomType.STUDY, 6);
        Room room4 = new Room(3, RoomType.LABORATORY, 6);
        floor3.addRoom(room2);
        floor3.addRoom(room3);
        floor3.addRoom(room4);
        testBuilding.addFloor(floor1);
        testBuilding.addFloor(floor2);
        testBuilding.addFloor(floor3);

        testBuilding.fireDrill(null);
        testBuilding.cancelFireDrill();

        assertEquals(FALSE, room1.fireDrillOngoing());
        assertEquals(FALSE, room2.fireDrillOngoing());
        assertEquals(FALSE, room3.fireDrillOngoing());
        assertEquals(FALSE, room4.fireDrillOngoing());
        assertEquals(FALSE, room5.fireDrillOngoing());


    }

    @Test (expected = IllegalArgumentException.class)
    public void BuildingTest3() throws FireDrillException, DuplicateFloorException, NoFloorBelowException, FloorTooSmallException {
        Building testBuilding = new Building("Test");
        Floor floor1 = new Floor(1,4,10);
        testBuilding.addFloor(floor1);
    }

    @Test (expected = DuplicateFloorException.class)
    public void BuildingTest4() throws FireDrillException, DuplicateFloorException, NoFloorBelowException, FloorTooSmallException {
        Building testBuilding = new Building("Test");
        Floor floor1 = new Floor(1,10,5);
        Floor floor2 = new Floor(1,10,5);
        testBuilding.addFloor(floor1);
        testBuilding.addFloor(floor2);
    }
    @Test (expected = NoFloorBelowException.class)
    public void BuildingTest5() throws FireDrillException, DuplicateFloorException, NoFloorBelowException, FloorTooSmallException {
        Building testBuilding = new Building("Test");
        Floor floor1 = new Floor(2, 10, 5);
        testBuilding.addFloor(floor1);
    }

    @Test (expected = FloorTooSmallException.class)
    public void BuildingTest6() throws FireDrillException, DuplicateFloorException, NoFloorBelowException, FloorTooSmallException {
        Building testBuilding = new Building("Test");
        Floor floor1 = new Floor(1, 10, 5);
        Floor floor2 = new Floor(2, 10, 6);
        testBuilding.addFloor(floor1);
        testBuilding.addFloor(floor2);
    }

    @Test
    public void BuildingTest7() throws FireDrillException, DuplicateFloorException, NoFloorBelowException, FloorTooSmallException, InsufficientSpaceException, DuplicateRoomException {
        Building testBuilding = new Building("Test");
        Floor floor1 = new Floor(1, 100, 100);
        Floor floor2 = new Floor(2, 90, 90);
        Floor floor3 = new Floor(3, 80, 80);
        Room room1 = new Room(1, RoomType.STUDY, 10);
        Room room2 = new Room(2, RoomType.LABORATORY, 10);
        Room room3 = new Room(3, RoomType.LABORATORY, 10);
        Room room4 = new Room(4, RoomType.STUDY, 10);
        Room room5 = new Room(5, RoomType.STUDY, 10);
        Room room6 = new Room(6, RoomType.STUDY, 10);
        Room room7 = new Room(7, RoomType.STUDY, 10);

        floor1.addRoom(room1);
        floor1.addRoom(room2);
        floor2.addRoom(room3);
        floor2.addRoom(room4);
        floor3.addRoom(room5);
        floor3.addRoom(room6);
        floor3.addRoom(room7);

        testBuilding.addFloor(floor1);
        testBuilding.addFloor(floor2);
        testBuilding.addFloor(floor3);
        System.out.println(testBuilding.getFloors());
        assertEquals(floor2,testBuilding.getFloorByNumber(2));

        testBuilding.fireDrill(RoomType.STUDY);

        System.out.println(room1.fireDrillOngoing());
        System.out.println(room2.fireDrillOngoing());
        System.out.println(room3.fireDrillOngoing());
        System.out.println(room4.fireDrillOngoing());
        System.out.println(room5.fireDrillOngoing());
        System.out.println(room6.fireDrillOngoing());
        System.out.println(room7.fireDrillOngoing());

        testBuilding.cancelFireDrill();

        System.out.println(room1.fireDrillOngoing());
        System.out.println(room2.fireDrillOngoing());
        System.out.println(room3.fireDrillOngoing());
        System.out.println(room4.fireDrillOngoing());
        System.out.println(room5.fireDrillOngoing());
        System.out.println(room6.fireDrillOngoing());
        System.out.println(room7.fireDrillOngoing());


        assertEquals("Building: name=\"Test\", floors=3",testBuilding.toString());

    }


}
