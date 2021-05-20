package com.example.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


// SensorEventLister interface allows to implement two callback methods to monitor the raw data
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // TAG for debugging
    private static final String TAG = "SENSOR_GATHERING";

    // Get application context
    int duration = Toast.LENGTH_SHORT;

    // Sensor Manager that allows to get the listing of every sensor
    private SensorManager sensorManager;
    private Sensor accel_sensor;
    private Sensor gyro_sensor;
    private Sensor magnetic_field;

    // Interface textviews
    private TextView tv_accel_x, tv_accel_y, tv_accel_z;
    private TextView tv_gyro_x, tv_gyro_y, tv_gyro_z;
    private TextView tv_mag_x, tv_mag_y, tv_mag_z;
    private TextView tv_teste;
    private Button record_btt;

    // Variables needed to hold values
    private float acc_x, acc_y, acc_z;
    private float gyro_x, gyro_y, gyro_z;
    private float mag_x, mag_y, mag_z;
    private boolean record = false;

    // Queue to hold values if a recording is started
    private Queue<SensorValues> latestResults = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create our Sensor Manager
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer, Gyroscope and Magnetic Field Sensors
        accel_sensor = checkIfAvailableSensor(Sensor.TYPE_ACCELEROMETER) ? null : sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyro_sensor = checkIfAvailableSensor(Sensor.TYPE_GYROSCOPE) ? null : sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        magnetic_field = checkIfAvailableSensor(Sensor.TYPE_MAGNETIC_FIELD) ? null : sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // Register sensor listener
        sensorManager.registerListener(this, accel_sensor, SensorManager.SENSOR_DELAY_FASTEST );
        sensorManager.registerListener(this, gyro_sensor, SensorManager.SENSOR_DELAY_FASTEST );
        sensorManager.registerListener(this, magnetic_field, SensorManager.SENSOR_DELAY_FASTEST );

        // Get textViews values
        tv_accel_x = (TextView)findViewById(R.id.accel_x);
        tv_accel_y = (TextView)findViewById(R.id.accel_y);
        tv_accel_z = (TextView)findViewById(R.id.accel_z);

        tv_gyro_x = (TextView)findViewById(R.id.type_or_x);
        tv_gyro_y = (TextView)findViewById(R.id.type_or_y);
        tv_gyro_z = (TextView)findViewById(R.id.type_or_z);

        tv_mag_x = (TextView)findViewById(R.id.mag_x);
        tv_mag_y = (TextView)findViewById(R.id.mag_y);
        tv_mag_z = (TextView)findViewById(R.id.mag_z);

        record_btt = (Button)findViewById(R.id.record_btt);

        record_btt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, R.string.recording_start, duration);
                toast.show();

                record = !record;

                latestResults.clear();
            }
        });


        // Record a
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (record) {

                    // Accelerometer data
                    Accelerometer newMeasureAcc = new Accelerometer(acc_x, acc_y, acc_z);
                    Gyroscope newMeasureGyr = new Gyroscope(gyro_x, gyro_y, gyro_z);
                    Magnometer newMeasureMag = new Magnometer(mag_x, mag_y, mag_z);

                    if (latestResults.size() < 250) {

                        latestResults.add(new SensorValues(newMeasureGyr, newMeasureAcc, newMeasureMag));
                    }
                    else {
                        writeWrite(latestResults);

                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, R.string.recording_done, duration);
                        toast.show();

                        record = false;
                    }
                }
                new Handler().postDelayed(this, 10);
            }
        }, 10);

    }

    /*
     *
     * @param typeAccelerometer - id of the sensor
     * @return
     */
    private boolean checkIfAvailableSensor(int typeAccelerometer) {
        return sensorManager.getDefaultSensor(typeAccelerometer) == null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType())
        {
            case Sensor.TYPE_ACCELEROMETER:
                acc_x = event.values[0];
                acc_y = event.values[1];
                acc_z = event.values[2];

                tv_accel_x.setText("X: " + acc_x);
                tv_accel_y.setText("Y: " + acc_y);
                tv_accel_z.setText("Z: " + acc_z);

                break;
            case Sensor.TYPE_GYROSCOPE:
                gyro_x = event.values[0];
                gyro_y = event.values[1];
                gyro_z = event.values[2];

                tv_gyro_x.setText("X: " + gyro_x);
                tv_gyro_y.setText("Y: " + gyro_y);
                tv_gyro_z.setText("Z: " + gyro_z);

                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mag_x = event.values[0];
                mag_y = event.values[1];
                mag_z = event.values[2];

                tv_mag_x.setText("X: " + mag_x);
                tv_mag_y.setText("Y: " + mag_y);
                tv_mag_z.setText("Z: " + mag_z);

                break;
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Registers the Queue onto a file named "example.csv" that can be found at the phones storage
     *
     * @param values - corresponding to the Queue of values to be recorded in a file
     */
    public void writeWrite(Queue<SensorValues> values) {
        StringBuilder file_content = new StringBuilder();

        for (SensorValues line : values) {
            file_content.append(line.toString());
        }

        String packageName = this.getPackageName();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Android/data/" + packageName + "/files/";
        String filename = "sensor_values.csv";

        try {
            boolean exists = (new File(path)).exists();
            if (!exists) {
                new File(path).mkdirs();
            }
            // Open output stream
            FileOutputStream fOut = new FileOutputStream(path + filename,true);
            // write integers as separated ascii's
            fOut.write(file_content.toString().getBytes());
            // Close output stream
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}