package bms.sensors;

import bms.util.TimedItem;

/**
 * A sensor that measures ambient temperature in a room.
 */
public class TemperatureSensor extends TimedSensor
        implements HazardSensor, Sensor, TimedItem {

    /**
     * Creates a new temperature sensor with the given sensor readings and
     * update frequency. For safety reasons, all temperature sensors must have
     * an update frequency of 1 minute.
     *
     * @param sensorReadings a non-empty array of sensor readings.
     */
    public TemperatureSensor(int[] sensorReadings) {
        super(sensorReadings, 1);
    }

    @Override
    public int getHazardLevel() {
        if (getCurrentReading() >= 68) {
            return 100;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", type=TemperatureSensor";
    }

}
