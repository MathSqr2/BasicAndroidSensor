package com.example.sensor;

public class SensorValues {
    Gyroscope gyr_data;
    Accelerometer acc_data;
    Magnometer mag_data;

    public SensorValues(Gyroscope gyr_data, Accelerometer acc_data, Magnometer mag_data) {
        this.gyr_data = gyr_data;
        this.acc_data = acc_data;
        this.mag_data = mag_data;
    }

    public Gyroscope getGyr_data() {
        return gyr_data;
    }

    public void setGyr_data(Gyroscope gyr_data) {
        this.gyr_data = gyr_data;
    }

    public Accelerometer getAcc_data() {
        return acc_data;
    }

    public void setAcc_data(Accelerometer acc_data) {
        this.acc_data = acc_data;
    }

    public Magnometer getMag_data() {
        return mag_data;
    }

    public void setMag_data(Magnometer mag_data) {
        this.mag_data = mag_data;
    }

    @Override
    public String toString() {
        return acc_data.toString() + "," + gyr_data.toString() + "," + mag_data.toString() + "\n";
    }
}
