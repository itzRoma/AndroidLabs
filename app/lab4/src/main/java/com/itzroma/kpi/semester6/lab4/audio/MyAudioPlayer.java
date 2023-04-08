package com.itzroma.kpi.semester6.lab4.audio;

import android.media.MediaPlayer;

public class MyAudioPlayer {

    private static MediaPlayer INSTANCE;

    private static int currentIndex = -1;

    public static MediaPlayer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MediaPlayer();
        }
        return INSTANCE;
    }

    public static int getCurrentIndex() {
        return currentIndex;
    }

    public static void setCurrentIndex(int currentIndex) {
        MyAudioPlayer.currentIndex = currentIndex;
    }

    public static void decrementCurrentIndex() {
        MyAudioPlayer.currentIndex--;
    }

    public static void incrementCurrentIndex() {
        MyAudioPlayer.currentIndex++;
    }
}
