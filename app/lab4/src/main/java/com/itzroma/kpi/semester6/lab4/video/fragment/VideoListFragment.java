package com.itzroma.kpi.semester6.lab4.video.fragment;

import android.database.Cursor;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itzroma.kpi.semester6.lab4.R;
import com.itzroma.kpi.semester6.lab4.video.fragment.adapter.VideoListAdapter;
import com.itzroma.kpi.semester6.lab4.video.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class VideoListFragment extends Fragment {

    private final List<VideoModel> videoList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);

        TextView noVideoTitle = view.findViewById(R.id.no_video_title);
        RecyclerView videoRecyclerView = view.findViewById(R.id.video_recycler_view);
        VideoListAdapter videoListAdapter = new VideoListAdapter(videoList, requireContext());

        String[] projection = {
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
        };

        Cursor cursor = requireContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        System.out.println("SCANNING STORAGE FOR VIDEOS STARTED");
        while (cursor.moveToNext()) {
            String videoName = cursor.getString(0);
            String videoPath = cursor.getString(1);

            VideoModel video = new VideoModel(
                    videoName,
                    videoPath,
                    ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND)
            );
            videoList.add(video);
        }
        videoListAdapter.notifyDataSetChanged();
        System.out.println("SCANNING STORAGE FOR VIDEOS FINISHED");
        cursor.close();

        if (videoList.isEmpty()) {
            noVideoTitle.setVisibility(View.VISIBLE);
        } else {
            videoRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            videoRecyclerView.setAdapter(videoListAdapter);
        }

        return view;
    }
}