package com.example.sensor;

public class Accelerometer {
    float accel_x;
    float accel_y;
    float accel_z;

    public Accelerometer(float accel_x, float accel_y, float accel_z) {
        this.accel_x = accel_x;
        this.accel_y = accel_y;
        this.accel_z = accel_z;
    }

    public float getAccel_x() {
        return accel_x;
    }

    public void setAccel_x(float accel_x) {
        this.accel_x = accel_x;
    }

    public float getAccel_y() {
        return accel_y;
    }

    public void setAccel_y(float accel_y) {
        this.accel_y = accel_y;
    }

    public float getAccel_z() {
        return accel_z;
    }

    public void setAccel_z(float accel_z) {
        this.accel_z = accel_z;
    }

    @Override
    public String toString() {
        return accel_x +
                "," + accel_y +
                "," + accel_z;

    }
}
