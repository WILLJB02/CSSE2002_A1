package bms.floor;

import bms.exceptions.DuplicateRoomException;
import bms.exceptions.InsufficientSpaceException;
import bms.room.Room;
import bms.room.RoomType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.*;

public class FloorTest {

    private Floor floor;
    private Room room1;
    private Room roomDuplicate;
    private Room room2;
    private Room room3;
    private Room roomToLarge;
    private Room roomToSmall;


    @Before
    public void setUp() throws Exception {
        floor = new Floor(1,20,25);
        room1 = new Room(1, RoomType.STUDY, 30);
        roomDuplicate = new Room(1, RoomType.STUDY, 40);
        room2 = new Room(2, RoomType.LABORATORY, 35);
        room3 = new Room(3, RoomType.LABORATORY, 45);
        roomToLarge = new Room(4, RoomType.OFFICE, 400);
        roomToSmall = new Room(5, RoomType.OFFICE, 4);

        floor.addRoom(room1);
        floor.addRoom(room2);
        floor.addRoom(room3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addOneRoom() throws InsufficientSpaceException, DuplicateRoomException {
        floor = new Floor(1,20,25);
        floor.addRoom(room1);
        assertEquals(1, floor.getRooms().size());
        assertEquals(room1, floor.getRooms().get(0));


    }

    @Test
    public void addMultipleRooms() throws InsufficientSpaceException, DuplicateRoomException {
        floor = new Floor(1,20,25);
        floor.addRoom(room1);
        floor.addRoom(room2);
        assertEquals(2, floor.getRooms().size());
        assertEquals(room1, floor.getRooms().get(0));
        assertEquals(room2, floor.getRooms().get(1));
    }

    @Test (expected = DuplicateRoomException.class)
    public void addDuplicateRoom() throws InsufficientSpaceException, DuplicateRoomException {
        floor.addRoom(roomDuplicate);
    }

    @Test (expected = InsufficientSpaceException.class)
    public void addInsufficientSpace() throws InsufficientSpaceException, DuplicateRoomException {
        floor.addRoom(roomToLarge);
    }

    @Test (expected = IllegalArgumentException.class)
    public void addIllegalArgument() throws InsufficientSpaceException, DuplicateRoomException {
        floor.addRoom(roomToSmall);
    }


    @Test
    public void getRooms() {
        List<Room> expected = new ArrayList<Room>();
        expected.add(room1);
        expected.add(room2);
        expected.add(room3);
        assertEquals(expected, floor.getRooms());
    }

    @Test
    public void calculateArea() {
        double calculatedArea =  floor.getWidth() * floor.getLength();
        assertEquals(500,calculatedArea, 0.01);
    }

    @Test
    public void getFloorNumber() {
        int floorNumber = floor.getFloorNumber();
        assertEquals(1, floorNumber);
    }

    @Test
    public void getMinWidth() {
        int minWidth = Floor.getMinWidth();
        assertEquals(5, minWidth);
    }

    @Test
    public void getMinLength() {
        int minLength = Floor.getMinLength();
        assertEquals(5, minLength);
    }

    @Test
    public void getWidth() {
        assertEquals(20, floor.getWidth(), 0.01);
    }

    @Test
    public void getLength() {
        assertEquals(25, floor.getLength(), 0.01);
    }

    @Test
    public void getRoomByNumber() throws InsufficientSpaceException, DuplicateRoomException {

        assertEquals(room2, floor.getRoomByNumber(2));

    }

    @Test
    public void occupiedArea() throws InsufficientSpaceException, DuplicateRoomException {
        assertEquals(110, floor.occupiedArea(), 0.01);
    }

    @Test
    public void fireDrillNoneOfType() throws InsufficientSpaceException, DuplicateRoomException {
        floor.fireDrill(RoomType.OFFICE);
        assertEquals(FALSE,room1.fireDrillOngoing());
        assertEquals(FALSE,room2.fireDrillOngoing());
        assertEquals(FALSE,room3.fireDrillOngoing());
    }

    @Test
    public void fireDrillOneOfType() {
        floor.fireDrill(RoomType.STUDY);
        assertEquals(TRUE,room1.fireDrillOngoing());
        assertEquals(FALSE,room2.fireDrillOngoing());
        assertEquals(FALSE,room3.fireDrillOngoing());
    }

    @Test
    public void fireDrillMultipleOfType() {
        floor.fireDrill(RoomType.LABORATORY);
        assertEquals(FALSE,room1.fireDrillOngoing());
        assertEquals(TRUE,room2.fireDrillOngoing());
        assertEquals(TRUE,room3.fireDrillOngoing());
    }

    @Test
    public void fireDrillNull() {
        floor.fireDrill(null);
        assertEquals(TRUE,room1.fireDrillOngoing());
        assertEquals(TRUE,room2.fireDrillOngoing());
        assertEquals(TRUE,room3.fireDrillOngoing());
    }

    @Test
    public void cancelFireDrill() {
        floor.fireDrill(null);
        floor.cancelFireDrill();
        assertEquals(FALSE,room1.fireDrillOngoing());
        assertEquals(FALSE,room2.fireDrillOngoing());
        assertEquals(FALSE,room3.fireDrillOngoing());
    }

    @Test
    public void testToString() throws InsufficientSpaceException, DuplicateRoomException {
        assertEquals("Floor #1: width=20.00m, length=25.00m, rooms=3", floor.toString());
    }
}