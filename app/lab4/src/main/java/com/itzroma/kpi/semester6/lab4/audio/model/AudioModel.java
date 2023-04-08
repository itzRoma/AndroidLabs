package com.itzroma.kpi.semester6.lab4.audio.model;

public class AudioModel implements java.io.Serializable {

    private final String path;
    private final String title;
    private final String duration;
    private byte[] bitmap;

    public AudioModel(String path, String title, String duration) {
        this.path = path;
        this.title = title;
        this.duration = duration;
        bitmap = null;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }
}
