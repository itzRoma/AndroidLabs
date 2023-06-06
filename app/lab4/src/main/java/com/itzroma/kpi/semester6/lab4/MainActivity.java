package com.itzroma.kpi.semester6.lab4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.itzroma.kpi.semester6.lab4.audio.fragment.AudioListFragment;
import com.itzroma.kpi.semester6.lab4.video.fragment.VideoListFragment;

public class MainActivity extends AppCompatActivity implements FragmentHolder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new AudioListFragment());

        ((BottomNavigationView) findViewById(R.id.bottom_nav))
                .setOnItemSelectedListener(typeSelectedListener());

        if (!checkPermission()) {
            requestPermission();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentUtil.replaceFragmentFor(getSupportFragmentManager(), fragment, getFragmentPlaceholderId());
    }

    @Override
    public int getFragmentPlaceholderId() {
        return R.id.fragment_placeholder;
    }

    @NonNull
    private NavigationBarView.OnItemSelectedListener typeSelectedListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.audio_option:
                    replaceFragment(new AudioListFragment());
                    item.setChecked(true);
                    break;
                case R.id.video_option:
                    replaceFragment(new VideoListFragment());
                    item.setChecked(true);
                    break;
            }
            return false;
        };
    }

    private boolean checkPermission() {
        int res = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "READ EXTERNAL STORAGE PERMISSION IS REQUIRED, PLEASE ALLOW FROM SETTING", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
    }
}