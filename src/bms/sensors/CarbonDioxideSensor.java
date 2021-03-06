package bms.sensors;

import bms.util.TimedItem;

/**
 * A sensor that measures levels of carbon dioxide (CO2) in the air,
 * in parts per million (ppm).
 */
public class CarbonDioxideSensor extends TimedSensor implements HazardSensor, TimedItem {

    /** ideal CO2 value of a Room in ppm */
    private int idealValue;
    /** acceptable range above and below ideal value in ppm */
    private int variationLimit;

    /**
     * Creates a new carbon dioxide sensor with the given sensor readings,
     * update frequency, ideal CO2 value and acceptable variation limit.
     * Different rooms and environments may naturally have different "normal"
     * CO2 concentrations, for example, a large room with many windows may have
     * lower typical CO2 concentrations than a small room with poor airflow.
     *
     * To allow for these discrepancies, each CO2 sensor has an "ideal" CO2
     * concentration and a maximum acceptable variation from this value.
     * Both the ideal value and variation limit must be greater than zero.
     * These two values must be such that (idealValue - variationLimit) >= 0.
     *
     * @param sensorReadings array of CO2 sensor readings in ppm
     * @param updateFrequency indicates how often the sensor readings update,
     *                        in minutes
     * @param idealValue ideal CO2 value in ppm
     * @param variationLimit acceptable range above and below ideal value in ppm
     * @throws IllegalArgumentException if idealValue <= 0; or if variationLimit
     *                                  <= 0; or if
     *                                  (idealValue - variationLimit) < 0
     */
    public CarbonDioxideSensor(int[] sensorReadings,
                               int updateFrequency,
                               int idealValue,
                               int variationLimit)
            throws IllegalArgumentException {
        super(sensorReadings,updateFrequency);

        if (idealValue <=0 || variationLimit <= 0 ||
                (idealValue - variationLimit) < 0) {
            throw new IllegalArgumentException();
        } else {
            this.idealValue = idealValue;
            this.variationLimit = variationLimit;
        }


    }


    @Override
    public int getHazardLevel() {
        if (getCurrentReading() < 1000) {
            return 0;
        } else if (getCurrentReading() < 2000) {
            return 25;
        } else if (getCurrentReading() < 5000) {
            return 50;
        } else {
            return 100;
        }
    }

    /**
     * Returns the sensor's CO2 variation limit.
     *
     * @return variation limit in ppm.
     */
    public int getVariationLimit(){
        return variationLimit;
    }

    /**
     * Returns the sensor's ideal CO2 value.
     *
     * @return ideal value in ppm
     */
    public int getIdealValue(){
        return idealValue;
    }

    @Override
    public String toString() {
        return super.toString() + ", type=CarbonDioxideSensor, idealPPM=" +
                getIdealValue() + ", varLimit=" + getVariationLimit();
    }





}
