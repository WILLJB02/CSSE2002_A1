package bms.room;

import bms.exceptions.DuplicateSensorException;
import bms.sensors.Sensor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a room on a floor of a building.
 * Each room has a room number (unique for this floor, ie. no two rooms on the
 * same floor can have the same room number), a type to indicate its intended
 * purpose, and a total area occupied by the room in square metres.
 * Rooms also need to record whether a fire drill is currently taking place in
 * the room.
 * Rooms can have one or more sensors to monitor hazard levels in the room.
 */
public class Room {

    /** the unique room number of the room on this floor */
    private int roomNumber;

    /** type of Room */
    private RoomType roomType;

    /** area of the room */
    private double area;

    /** constant for the minimum allowable area of a room*/
    static int MIN_AREA = 5;
    private boolean firedrill;
    private List<Sensor> sensorArrayList;

    /**
     * Creates a new room with the given room number.
     *
     * @param roomNumber the unique room number of the room on this floor
     * @param type the type of room
     * @param area the area of the room in square metres
     */
    public Room(int roomNumber, RoomType type, double area) {
        this.roomNumber = roomNumber;
        this.roomType = type;
        this.area = area;
        firedrill = false;
        sensorArrayList = new ArrayList<Sensor>();
    }

    /**
     * Returns room number of the room.
     *
     * @return the room number on the floor
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Returns area of the room.
     *
     * @return the room area in square metres
     */
    public double getArea() {
        return area;
    }

    /**
     * Returns the minimum area for all rooms.
     * Rooms must be at least 5 square metres in area.
     *
     * @return the minimum room area in square metres
     */
    public static int getMinArea() {
        return MIN_AREA;
    }

    /**
     * Returns the type of the room.
     *
     * @return the room type
     */
    public RoomType getType() {
        return roomType;
    }

    /**
     * Returns whether there is currently a fire drill in progress.
     *
     * @return current status of fire drill
     */
    public boolean fireDrillOngoing() {
        return firedrill;
    }

    /**
     * Returns the list of sensors in the room.
     * The list of sensors stored by the room should always be in alphabetical
     * order, by the sensor's class name.
     *
     * Adding or removing sensors from this list should not affect the
     * room's internal list of sensors.
     *
     * @return list of all sensors in alphabetical order of class name
     */
    public List<Sensor> getSensors() {
        return new ArrayList<Sensor>(sensorArrayList);
    }

    /**
     * Change the status of the fire drill to the given value.
     *
     * @param fireDrill whether there is a fire drill ongoing
     */
    public void setFireDrill(boolean fireDrill) {
        this.firedrill = fireDrill;
    }

    /**
     * Return the given type of sensor if there is one in the list of sensors;
     * return null otherwise.
     *
     * @param sensorType the type of sensor which matches the class name
     *                   returned by the getSimpleName() method,
     *                   e.g. "NoiseSensor" (no quotes)
     * @return the sensor in this room of the given type; null if none found
     */
    public Sensor getSensor(String sensorType) {

        Sensor result = null;
        for (Sensor s : sensorArrayList) {
            Class sensor = s.getClass();
            String sName = sensor.getSimpleName();
            if (sName.equals(sensorType)) {
                result = s;
            }
        }
        return result;
    }

    /**
     * Adds a sensor to the room if a sensor of the same type is not already
     * in the room.
     * The list of sensors should be sorted after adding the new sensor,
     * in alphabetical order by simple class name (Class.getSimpleName()).
     *
     * @param sensor the sensor to add to the room
     * @throws DuplicateSensorException if the sensor to add is of the same
     * type as a sensor already in this room
     */
    public void addSensor(Sensor sensor) throws DuplicateSensorException {

        boolean duplicateSensor = false;

        //checking for duplicate sensor
        for (Sensor s : sensorArrayList){
            Class sensorList = s.getClass();
            String sNameList = sensorList.getSimpleName();

            Class sensorArg = sensor.getClass();
            String sNameArg = sensorArg.getSimpleName();

            if (sNameList.equals(sNameArg)) {
                duplicateSensor = true;
                break;
            }
        }

        if (duplicateSensor) {
            throw new DuplicateSensorException();
        } else {
            sensorArrayList.add(sensor);
            sensorArrayList.sort(Comparator.comparing(o ->
                    o.getClass().getSimpleName()));
        }
    }

    @Override
    public String toString() {
        String strArea = String.format("%.2f", area);
        return "Room #" + roomNumber +": type=" + roomType + ", area=" +
                strArea + "m^2, sensors=" + sensorArrayList.size();
    }








}
