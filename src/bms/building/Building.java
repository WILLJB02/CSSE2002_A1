package bms.building;

import bms.exceptions.DuplicateFloorException;
import bms.exceptions.FireDrillException;
import bms.exceptions.FloorTooSmallException;
import bms.exceptions.NoFloorBelowException;
import bms.floor.Floor;
import bms.room.RoomType;
import bms.util.FireDrill;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a building of floors, which in turn, contain rooms.
 * A building needs to manage and keep track of the floors that make up the
 * building.
 *
 * A building can be evacuated, which causes all rooms on all floors within
 * the building to be evacuated.
 */
public class Building implements FireDrill {

    /** name of building */
    private final String name;

    /** list containing all floors in a building */
    private List<Floor> floors;

    /**
     * Creates a new empty building with no rooms.
     *
     * @param name name of this building, eg. "General Purpose South"
     */
    public Building(String name) {
        this.name = name;
        floors = new ArrayList<Floor>();
    }

    /**
     * Returns a new list containing all the floors in this building.
     * Adding or removing floors from this list should not affect the
     * building's internal list of floors.
     *
     * @return new list containing all floors in the building
     */
    public List<Floor> getFloors() {
        return new ArrayList<Floor>(floors);
    }

    @Override
    public void fireDrill(RoomType roomType) throws FireDrillException {

        boolean noRooms = true;
        boolean noFloors = false;

        //checking if the building has floors and/or any rooms.
        if (getFloors().size() == 0) {
            noFloors = true;
        } else {
            for (Floor floor : floors) {
                if (floor.getRooms().size() != 0) {
                    noRooms = false;
                }
            }
        }

        // starting fire drills in required rooms
        if (noRooms || noFloors) {
            throw new FireDrillException();
        } else {
            for (Floor floor : floors) {
                for (int j = 0; j < floor.getRooms().size(); j++) {
                    if (roomType == null) {
                        floor.getRooms().get(j).setFireDrill(true);
                    }
                    else if (floor.getRooms().get(j).getType() == roomType) {
                        floor.getRooms().get(j).setFireDrill(true);
                    }
                }
            }
        }
    }

    /**
     * Cancels any ongoing fire drill in the building.
     * All rooms must have their fire alarm cancelled regardless of room type.
     */
    public void cancelFireDrill() {
        for (Floor floor : floors) {
            for (int j = 0; j < floor.getRooms().size(); j++) {
                floor.getRooms().get(j).setFireDrill(false);
            }
        }
    }

    /**
     * Returns the name of the building.
     *
     * @return name of this building
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a floor to the building.
     * If the given arguments are invalid, the floor already exists,
     * there is no floor below, or the floor below does not have enough area to
     * support this floor, an exception should be thrown and no action
     * should be taken.
     *
     * @param newFloor object representing the new floor
     * @throws IllegalArgumentException if floor number is <= 0,
     * width < Floor.getMinWidth(), or length < Floor.getMinLength()
     * @throws DuplicateFloorException if a floor at this level already exists
     * in the building
     * @throws NoFloorBelowException  if this is at level 2 or above and there
     * is no floor below to support this new floor
     * @throws FloorTooSmallException if this is at level 2 or above and the
     * floor below is not big enough to support this new floor
     */
    public void addFloor(Floor newFloor)
            throws IllegalArgumentException, DuplicateFloorException,
            NoFloorBelowException, FloorTooSmallException {

        // checking for IllegalArgument Conditions
        if (newFloor.getFloorNumber() == 0 || newFloor.getWidth() <
                Floor.getMinWidth() || newFloor.getLength() <
                Floor.getMinLength()) {
            throw new IllegalArgumentException();
        }

        // checking for DuplicateFloor Conditions
        for (Floor floor : floors) {
            if (floor.getFloorNumber() == newFloor.getFloorNumber()) {
                throw new DuplicateFloorException();
            }
        }

        // determining if floor below exists and saving as a local variable
        boolean floorBelow = false;
        Floor belowFloor = null;
        if (newFloor.getFloorNumber() == 1) {
            floorBelow = true;
        } else {
            for (Floor floor : floors) {
                if (newFloor.getFloorNumber() == floor.getFloorNumber() + 1) {
                    floorBelow = true;
                    belowFloor = floor;
                }
            }
        }

        if (!floorBelow) {
            throw new NoFloorBelowException();
        }

        // determining if floor to be added is of an appropriate size
        if (belowFloor != null) {
            if (newFloor.getLength() > belowFloor.getLength() ||
                    newFloor.getWidth() > belowFloor.getWidth()) {
                throw new FloorTooSmallException();
            }
        }
        floors.add(newFloor);
    }

    /**
     * Searches for the floor with the specified floor number.
     * Returns the corresponding Floor object, or null if the floor was
     * not found.
     *
     * @param floorNumber floor number of floor to search for
     * @return floor with the given number if found; null if not found
     */
    public Floor getFloorByNumber(int floorNumber) {

        Floor result = null;
        for (Floor floor : floors) {
            if (floor.getFloorNumber() == floorNumber) {
                result = floor;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Building: name=\"" +
                getName() + "\", floors=" + getFloors().size();
    }

}
