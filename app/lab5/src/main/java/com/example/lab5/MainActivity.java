package com.example.lab5;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager == null) {
            Toast.makeText(this, "Sensor service not detected", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometerSensor == null) {
            Toast.makeText(this, "Accelerometer sensor not detected", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            @SuppressLint("DefaultLocale")
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    TextView sensorValues = findViewById(R.id.sensor_values);
                    sensorValues.setText(String.format(
                            "x: %f%ny: %f%nz: %f",
                            event.values[0], event.values[1], event.values[2]
                    ));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}