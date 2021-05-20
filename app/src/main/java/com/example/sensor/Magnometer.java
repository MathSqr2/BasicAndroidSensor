package com.example.sensor;

public class Magnometer {
    float magno_x;
    float magno_y;
    float magno_z;

    public Magnometer(float magno_x, float magno_y, float magno_z) {
        this.magno_x = magno_x;
        this.magno_y = magno_y;
        this.magno_z = magno_z;
    }

    public float getMagno_x() {
        return magno_x;
    }

    public void setMagno_x(float magno_x) {
        this.magno_x = magno_x;
    }

    public float getMagno_y() {
        return magno_y;
    }

    public void setMagno_y(float magno_y) {
        this.magno_y = magno_y;
    }

    public float getMagno_z() {
        return magno_z;
    }

    public void setMagno_z(float magno_z) {
        this.magno_z = magno_z;
    }

    @Override
    public String toString() {
        return magno_x +
                "," + magno_y +
                "," + magno_z ;
    }
}
