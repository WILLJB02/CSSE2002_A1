package bms.floor;

import bms.exceptions.DuplicateRoomException;
import bms.exceptions.FireDrillException;
import bms.exceptions.InsufficientSpaceException;
import bms.room.Room;
import bms.room.RoomType;
import bms.util.FireDrill;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a floor of a building.
 * All floors have a floor number (ground floor is floor 1), a list of rooms,
 * and a width and length.
 *
 * A floor can be evacuated (leading to all rooms on the floor to be evacuated).
 */
public class Floor implements FireDrill {

    /** number of floor */
    private int floorNumber;

    /** width of floor */
    private double width;

    /** length of floor */
    private double length;

    /** constant describing the minimal allowable floor width */
    static int MIN_WIDTH = 5;

    /** constant describing the minimal allowable floor length */
    static int MIN_LENGTH = 5;

    /** list containing all the rooms in a specific floor */
    private List<Room> floorRooms;

    /** how much area is available on a floor for other rooms */
    private double availableArea;

    /**
     * Creates a new floor with the given floor number.
     *
     * @param floorNumber a unique floor number, corresponds to how many floors
     *                    above ground floor (inclusive)
     * @param width the width of the floor in metres
     * @param length the length of the floor in metres
     */
    public Floor(int floorNumber, double width, double length) {
        this.floorNumber = floorNumber;
        this.width = width;
        this.length = length;
        floorRooms = new ArrayList<Room>();
        availableArea = width * length;
    }

    /**
     * Adds a room to the floor.
     * The dimensions of the room are managed automatically. The length and
     * width of the room do not need to be specified, only the required space.
     *
     * @param newRoom object representing the new room
     * @throws IllegalArgumentException if area is less than Room.getMinArea()
     * @throws DuplicateRoomException if the room number on this floor is
     * already taken
     * @throws InsufficientSpaceException if there is insufficient space
     * available on the floor to be able to add the room
     */
    public void addRoom(Room newRoom)
            throws DuplicateRoomException, InsufficientSpaceException {

        if (newRoom.getArea() < Room.getMinArea()) {
            throw new IllegalArgumentException();
        }

        for (Room floorRoom : floorRooms) {
            if (newRoom.getRoomNumber() == floorRoom.getRoomNumber()) {
                throw new DuplicateRoomException();
            }

            if (newRoom.getArea() > availableArea) {
                throw new InsufficientSpaceException();
            }
        }
        floorRooms.add(newRoom);
        availableArea = availableArea - newRoom.getArea();
    }

    /**
     * Returns a new list containing all the rooms on this floor.
     * Adding or removing rooms from this list should not affect the
     * floor's internal list of rooms.
     *
     * @return new list containing all rooms on the floor
     */
    public List<Room> getRooms() {
        return new ArrayList<Room>(floorRooms);
    }

    /**
     * Calculates the area of the floor in square metres.
     * The area should be calculated as getWidth() multiplied by getLength().
     *
     * For example, a floor with a length of 20.5 and width of 35.2,
     * would be 721.6 square metres.
     *
     * @return area of the floor in square metres
     */
    public double calculateArea() {
        return getLength()*getWidth();
    }

    /**
     * Returns the floor number of this floor.
     *
     * @return floor number
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * Returns the minimum width for all floors.
     *
     * @return 5
     */
    public static int getMinWidth() {
        return MIN_WIDTH;
    }

    /**
     * Returns the minimum length for all floors.
     *
     * @return 5
     */
    public static int getMinLength() {
        return MIN_LENGTH;
    }

    /**
     * Returns width of the floor.
     *
     * @return floor width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns length of the floor.
     *
     * @return floor length
     */
    public double getLength() {
        return length;
    }

    /**
     * Search for the room with the specified room number.
     * Returns the corresponding Room object, or null if the room was not found.
     *
     * @param roomNumber room number of room to search for
     * @return room with the given number if found; null if not found
     */
    public Room getRoomByNumber(int roomNumber) {

        Room result = null;
        for (Room floorRoom : floorRooms) {
            if (floorRoom.getRoomNumber() == roomNumber) {
                result = floorRoom;
            }
        }

        return result;
    }

    /**
     * Calculates the area of the floor which is currently occupied by all
     * the rooms on the floor.
     *
     * @return area of the floor that is currently occupied, in square metres
     */
    public float occupiedArea() {
        return (float) (calculateArea() - availableArea);
    }

    @Override
    public void fireDrill(RoomType roomType) {
        for (Room floorRoom : floorRooms) {
            if (roomType != null && floorRoom.getType() == roomType) {
                floorRoom.setFireDrill(true);
            } else if (roomType == null) {
                floorRoom.setFireDrill(true);
            }
        }
    }

    /**
     * Cancels any ongoing fire drill in rooms on the floor.
     * All rooms must have their fire alarm cancelled regardless of room type.
     */
    public void cancelFireDrill() {
        for (Room floorRoom : floorRooms) {
            floorRoom.setFireDrill(false);
        }
    }

    @Override
    public String toString() {
        String strLength = String.format("%.2f", length);
        String strWidth = String.format("%.2f", width);
        return "Floor #" + getFloorNumber() + ": width=" + strWidth +
                "m, length=" + strLength + "m, rooms=" + floorRooms.size();
    }

}
