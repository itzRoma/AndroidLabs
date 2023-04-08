package com.itzroma.kpi.semester6.lab4.audio;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itzroma.kpi.semester6.lab4.R;
import com.itzroma.kpi.semester6.lab4.audio.model.AudioModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AudioPlayerActivity extends AppCompatActivity {

    private TextView audioTitle;
    private TextView audioCurrentTime;
    private TextView audioTotalTime;
    private SeekBar audioSeekBar;
    private ImageView audioPlayPause;
    private ImageView audioPrevious;
    private ImageView audioNext;
    private ImageView audioImage;
    private List<AudioModel> audioList;

    private final MediaPlayer audioPlayerInstance = MyAudioPlayer.getInstance();

    private AudioModel currentAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        audioTitle = findViewById(R.id.audio_title);
        audioCurrentTime = findViewById(R.id.audio_current_time);
        audioTotalTime = findViewById(R.id.audio_total_time);
        audioSeekBar = findViewById(R.id.audio_seek_bar);
        audioPlayPause = findViewById(R.id.audio_play_pause);
        audioPrevious = findViewById(R.id.audio_previous);
        audioNext = findViewById(R.id.audio_next);
        audioImage = findViewById(R.id.audio_image);

        audioList = (List<AudioModel>) getIntent().getSerializableExtra("LIST");

        audioTitle.setSelected(true);
        prepareAndPlayAudio();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (audioPlayerInstance != null) {
                    audioSeekBar.setProgress(audioPlayerInstance.getCurrentPosition());
                    audioCurrentTime.setText(convertToMMSS(audioPlayerInstance.getCurrentPosition() + ""));

                    if (audioPlayerInstance.isPlaying()) {
                        audioPlayPause.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                    } else {
                        audioPlayPause.setImageResource(R.drawable.baseline_play_circle_outline_24);
                    }
                }
                new Handler().postDelayed(this, 100);
            }
        });

        audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (audioPlayerInstance != null && fromUser) {
                    audioPlayerInstance.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // nothing to do
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // nothing to do
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        audioPlayerInstance.reset();
    }

    private void prepareAndPlayAudio() {
        currentAudio = audioList.get(MyAudioPlayer.getCurrentIndex());

        audioTitle.setText(currentAudio.getTitle());
        audioTotalTime.setText(convertToMMSS(currentAudio.getDuration()));

        if (currentAudio.getBitmap() == null) {
            audioImage.setImageResource(R.drawable.baseline_audiotrack_24);
        } else {
            audioImage.setImageBitmap(BitmapFactory.decodeByteArray(
                    currentAudio.getBitmap(), 0, currentAudio.getBitmap().length)
            );
        }

        audioPrevious.setOnClickListener(v -> playPreviousAudio());
        audioPlayPause.setOnClickListener(v -> playPauseAudio());
        audioNext.setOnClickListener(v -> playNextAudio());

        playAudio();
    }

    private void playPreviousAudio() {
        if (MyAudioPlayer.getCurrentIndex() == 0) {
            Toast.makeText(this, "No previous audio", Toast.LENGTH_SHORT).show();
            return;
        }
        MyAudioPlayer.decrementCurrentIndex();
        audioPlayerInstance.reset();
        prepareAndPlayAudio();
    }

    private void playPauseAudio() {
        if (audioPlayerInstance.isPlaying()) {
            audioPlayerInstance.pause();
        } else {
            audioPlayerInstance.start();
        }
    }

    private void playNextAudio() {
        if (MyAudioPlayer.getCurrentIndex() == audioList.size() - 1) {
            Toast.makeText(this, "No next audio", Toast.LENGTH_SHORT).show();
            return;
        }
        MyAudioPlayer.incrementCurrentIndex();
        audioPlayerInstance.reset();
        prepareAndPlayAudio();
    }

    private void playAudio() {
        audioPlayerInstance.reset();

        try {
            audioPlayerInstance.setDataSource(currentAudio.getPath());
            audioPlayerInstance.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        audioPlayerInstance.start();
        audioSeekBar.setProgress(0);
        audioSeekBar.setMax(audioPlayerInstance.getDuration());
    }

    @SuppressLint("DefaultLocale")
    private static String convertToMMSS(String duration) {
        long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}