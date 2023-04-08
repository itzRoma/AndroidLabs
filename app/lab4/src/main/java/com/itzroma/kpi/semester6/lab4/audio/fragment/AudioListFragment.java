package com.itzroma.kpi.semester6.lab4.audio.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itzroma.kpi.semester6.lab4.R;
import com.itzroma.kpi.semester6.lab4.audio.fragment.adapter.AudioListAdapter;
import com.itzroma.kpi.semester6.lab4.audio.model.AudioModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AudioListFragment extends Fragment {

    private final List<AudioModel> audioList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio_list, container, false);

        TextView noAudioTitle = view.findViewById(R.id.no_audio_title);
        RecyclerView audioRecyclerView = view.findViewById(R.id.audio_recycler_view);

        if (!checkPermission()) {
            requestPermission();
            return view;
        }

        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = requireContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
        System.out.println("SCANNING STORAGE STARTED");
        while (cursor.moveToNext()) {
            AudioModel audio = new AudioModel(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            if (new File(audio.getPath()).exists()) {
                byte[] bitmap;
                try (MediaMetadataRetriever mmr = new MediaMetadataRetriever()) {
                    mmr.setDataSource(audio.getPath());
                    bitmap = mmr.getEmbeddedPicture();
                } catch (IOException e) {
                    bitmap = null;
                }
                audio.setBitmap(bitmap);

                audioList.add(audio);
            }
        }
        System.out.println("SCANNING STORAGE FINISHED");
        cursor.close();

        if (audioList.isEmpty()) {
            noAudioTitle.setVisibility(View.VISIBLE);
        } else {
            audioRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            audioRecyclerView.setAdapter(new AudioListAdapter(audioList, requireContext()));
        }

        return view;
    }

    private boolean checkPermission() {
        int res = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(requireContext(), "READ EXTERNAL STORAGE PERMISSION IS REQUIRED, PLEASE ALLOW FROM SETTING", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
    }
}