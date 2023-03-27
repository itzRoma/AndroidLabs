package com.itzroma.kpi.semester6.lab2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.itzroma.kpi.semester6.lab2.fragment.InputFragment;
import com.itzroma.kpi.semester6.lab2.util.FragmentUtil;

public class MainActivity extends AppCompatActivity implements FragmentHolder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentUtil.replaceFragmentFor(getSupportFragmentManager(), new InputFragment(), getFragmentPlaceholderId());
    }

    @Override
    public int getFragmentPlaceholderId() {
        return R.id.fragmentPlaceholder;
    }
}