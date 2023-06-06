package com.example.lab5;

import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView circle = findViewById(R.id.circle);

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
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    int offset = 75 + 75 / 3;
                    float newX = (float) (Resources.getSystem().getDisplayMetrics().widthPixels / 2 + event.values[0] * Resources.getSystem().getDisplayMetrics().widthPixels / 9.81 - offset);
                    float newY = (float) (Resources.getSystem().getDisplayMetrics().heightPixels / 2 - event.values[1] * Resources.getSystem().getDisplayMetrics().heightPixels / 9.81 - offset);

                    if (newX < -offset) newX = -offset;
                    if (newY < -offset) newY = -offset;

                    circle.setTranslationX(Math.min(newX, Resources.getSystem().getDisplayMetrics().widthPixels - 75));
                    circle.setTranslationY(Math.min(newY, Resources.getSystem().getDisplayMetrics().heightPixels - 75));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}