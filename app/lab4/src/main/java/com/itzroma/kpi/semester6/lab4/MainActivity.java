package com.itzroma.kpi.semester6.lab4;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.itzroma.kpi.semester6.lab4.audio.fragment.AudioListFragment;
import com.itzroma.kpi.semester6.lab4.util.FragmentUtil;
import com.itzroma.kpi.semester6.lab4.video.fragment.VideoListFragment;

public class MainActivity extends AppCompatActivity implements FragmentHolder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new AudioListFragment());

        ((RadioGroup) findViewById(R.id.type_options)).setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.audio_type_option) {
                replaceFragment(new AudioListFragment());
            } else if (i == R.id.video_type_option) {
                replaceFragment(new VideoListFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentUtil.replaceFragmentFor(getSupportFragmentManager(), fragment, getFragmentPlaceholderId());
    }

    @Override
    public int getFragmentPlaceholderId() {
        return R.id.fragment_placeholder;
    }
}