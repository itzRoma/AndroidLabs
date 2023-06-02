package com.itzroma.kpi.semester6.lab4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.itzroma.kpi.semester6.lab4.audio.fragment.AudioListFragment;
import com.itzroma.kpi.semester6.lab4.util.FragmentUtil;
import com.itzroma.kpi.semester6.lab4.video.fragment.VideoListFragment;

public class MainActivity extends AppCompatActivity implements FragmentHolder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new AudioListFragment());

        ((BottomNavigationView) findViewById(R.id.bottom_nav))
                .setOnItemSelectedListener(typeSelectedListener());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentUtil.replaceFragmentFor(getSupportFragmentManager(), fragment, getFragmentPlaceholderId());
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

    @Override
    public int getFragmentPlaceholderId() {
        return R.id.fragment_placeholder;
    }
}