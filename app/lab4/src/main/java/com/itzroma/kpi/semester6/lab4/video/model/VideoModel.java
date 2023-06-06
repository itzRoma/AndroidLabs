package com.itzroma.kpi.semester6.lab4.video.model;

import android.graphics.Bitmap;

public class VideoModel {

    private final String videoName;
    private final String videoPath;
    private final Bitmap thumbNail;

    public VideoModel(String videoName, String videoPath, Bitmap thumbNail) {
        this.videoName = videoName;
        this.videoPath = videoPath;
        this.thumbNail = thumbNail;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public Bitmap getThumbNail() {
        return thumbNail;
    }

}
