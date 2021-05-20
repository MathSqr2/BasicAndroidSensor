package com.example.sensor;

public class Gyroscope {
    float gyros_x;
    float gyros_y;
    float gyros_z;

    public Gyroscope(float gyros_x, float gyros_y, float gyros_z) {
        this.gyros_x = gyros_x;
        this.gyros_y = gyros_y;
        this.gyros_z = gyros_z;
    }

    public float getGyros_x() {
        return gyros_x;
    }

    public void setGyros_x(float gyros_x) {
        this.gyros_x = gyros_x;
    }

    public float getGyros_y() {
        return gyros_y;
    }

    public void setGyros_y(float gyros_y) {
        this.gyros_y = gyros_y;
    }

    public float getGyros_z() {
        return gyros_z;
    }

    public void setGyros_z(float gyros_z) {
        this.gyros_z = gyros_z;
    }

    @Override
    public String toString() {
        return gyros_x +
                "," + gyros_y +
                "," + gyros_z ;
    }
}
