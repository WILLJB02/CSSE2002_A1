package bms.sensors;

import bms.util.TimedItem;
import bms.util.TimedItemManager;

/**
 * An abstract class to represent a sensor that iterates through observed values
 * on a timer.
 */
public abstract class TimedSensor implements Sensor, TimedItem {

    /** array which contains readings from a given sensor */
    private int[] sensorReadings;

    /** update frequency of the given sensor*/
    private int updateFrequency;

    /** how long the sensor has been running for */
    private int timeElapsed;

    /** stores the current index of the sensorReadings array based on the
     * time elapsed */
    private int sensorReadingIndex;

    /**
     * Creates a new timed sensor, using the provided list of sensor readings.
     * These represent "raw" data values, and have different meanings depending
     * on the concrete sensor class used.
     * The provided update frequency must be greater than or equal to one (1),
     * and less than or equal to five (5). The provided sensor readings array
     * must not be null, and must have at least one element. All sensor readings
     * must be non-negative.
     *
     * The new timed sensor should be configured such that the first call to
     * getCurrentReading() after calling the constructor must return the first
     * element of the given array.
     *
     * The sensor should be registered as a timed item, see
     * TimedItemManager.registerTimedItem(TimedItem).
     *
     * @param sensorReadings a non-empty array of sensor readings
     * @param updateFrequency indicates how often the sensor readings updates,
     *                        in minutes
     *
     * @throws IllegalArgumentException if updateFrequency is < 1 or > 5; or if
     * sensorReadings is null; if sensorReadings is empty; or if any value in
     * is less than zero
     */
    public TimedSensor(int[] sensorReadings, int updateFrequency)
            throws IllegalArgumentException {

        if (sensorReadings == null) {
            throw new IllegalArgumentException();
        } else {

            boolean readingsLessThanZero = false;

            // checking sensorReadings for integers less than zero
            for(int i : sensorReadings) {
                if (i < 0) {
                    readingsLessThanZero = true;
                    break;
                }
            }

            if (updateFrequency < 1 || updateFrequency > 5 ||
                    readingsLessThanZero) {
                throw new IllegalArgumentException();
            } else {
                this.sensorReadings = sensorReadings;
                this.updateFrequency = updateFrequency;
                timeElapsed = 0;
                sensorReadingIndex = 0;
                TimedItemManager.getInstance().registerTimedItem(this);
            }
        }
    }

    /**
     * Returns the number of minutes in between updates to the current sensor
     * reading.
     *
     * @return the sensor's update frequency in minutes
     */
    public int getUpdateFrequency() {
        return updateFrequency;
    }

    /**
     * Returns the number of minutes that have elapsed since the sensor was
     * instantiated. Should return 0 immediately after the constructor is called
     *
     * @return the sensor's time elapsed in minutes
     */
    public int getTimeElapsed() {
        return timeElapsed;
    }

    @Override
    public int getCurrentReading() {
        return sensorReadings[sensorReadingIndex];
    }

    @Override
    public void elapseOneMinute() {
        timeElapsed += 1;
        if (timeElapsed % updateFrequency == 0){
            sensorReadingIndex = (timeElapsed / updateFrequency)
                    % sensorReadings.length;
        }
    }

    private String arrayString(){

        String commaSeparatedList = "";

        // converts array of ints to a String of comma separated values
        for (int i = 0; i < sensorReadings.length; i++) {
            if (i == sensorReadings.length - 1) {
                commaSeparatedList = commaSeparatedList + sensorReadings[i];
            } else {
                commaSeparatedList = commaSeparatedList +
                        sensorReadings[i] + ",";
            }
        }
        return commaSeparatedList;
    }

    @Override
    public String toString() {
        return "TimedSensor: freq=" + getUpdateFrequency() +
                ", readings=" + arrayString();
    }
}


