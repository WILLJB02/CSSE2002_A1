package bms.sensors;

import bms.util.TimedItem;

/**
 * A sensor that measures the number of people in a room.
 */
public class OccupancySensor extends TimedSensor
        implements HazardSensor, Sensor, TimedItem {

    /** maximum allowable number of people in the room which contains sensor */
    private int capacity;

    /**
     * Creates a new occupancy sensor with the given sensor readings, update
     * frequency and capacity.
     * The given capacity must be greater than or equal to zero.
     *
     * @param sensorReadings a non-empty array of sensor readings
     * @param updateFrequency  indicates how often the sensor readings update,
     *                         in minutes
     * @param capacity  maximum allowable number of people in the room
     * @throws IllegalArgumentException if capacity is less than zero
     */
    public OccupancySensor(int[] sensorReadings, int updateFrequency,
                           int capacity) throws IllegalArgumentException
    {
        super(sensorReadings, updateFrequency);

        if (capacity < 0) {
            throw new IllegalArgumentException();
        }

        this.capacity = capacity;
    }

    /**
     * Returns the capacity of this occupancy sensor.
     *
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getHazardLevel() {

        float currentReading = (float) getCurrentReading();
        float capacity = (float) getCapacity();
        float hazard = (currentReading / capacity) * 100;

        if (hazard >= 100) {
            return 100;
        } else {
            return (int) Math.round(hazard);
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", " +
                "type=OccupancySensor, capacity=" + getCapacity();
    }
}
