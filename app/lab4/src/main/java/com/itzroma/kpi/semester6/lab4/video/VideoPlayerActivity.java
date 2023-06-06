package com.itzroma.kpi.semester6.lab4.video;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.itzroma.kpi.semester6.lab4.R;

public class VideoPlayerActivity extends AppCompatActivity {

    private String videoName;
    private String videoPath;

    private RelativeLayout controlsLayout;
    private boolean isOpen = true;
    private RelativeLayout videoLayout;

    private TextView videoNameTV;
    private TextView videoTime;
    private ImageButton replay;
    private ImageButton playPause;
    private ImageButton forward;
    private SeekBar videoSeekBar;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoName = getIntent().getStringExtra("videoName");
        videoPath = getIntent().getStringExtra("videoPath");

        videoNameTV = findViewById(R.id.video_controls_title);
        videoTime = findViewById(R.id.video_controls_time);
        replay = findViewById(R.id.video_controls_replay);
        playPause = findViewById(R.id.video_controls_play_pause);
        forward = findViewById(R.id.video_controls_forward);
        videoSeekBar = findViewById(R.id.video_controls_seekbar);
        videoView = findViewById(R.id.video_view);
        controlsLayout = findViewById(R.id.video_controls);
        videoLayout = findViewById(R.id.video_player_layout);

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.setOnPreparedListener(mp -> {
            videoSeekBar.setMax(videoView.getDuration());
            videoView.start();
        });

        videoNameTV.setText(videoName);

        replay.setOnClickListener(v -> videoView.seekTo(videoView.getCurrentPosition() - 10_000));

        playPause.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                playPause.setImageDrawable(getDrawable(R.drawable.baseline_play_circle_outline_24));
            } else {
                videoView.start();
                playPause.setImageDrawable(getDrawable(R.drawable.baseline_pause_circle_outline_24));
            }
        });

        forward.setOnClickListener(v -> videoView.seekTo(videoView.getCurrentPosition() + 10_000));

        videoLayout.setOnClickListener(v -> {
            if (isOpen) {
                hideControls();
                isOpen = false;
            } else {
                showControls();
                isOpen = true;
            }
        });

        setHandler();
        initializeSeekBar();
    }

    private void hideControls() {
        controlsLayout.setVisibility(View.GONE);
        Window window = this.getWindow();
        if (window == null) {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorView = window.getDecorView();
        if (decorView != null) {
            int uiOption = decorView.getSystemUiVisibility();

            if (Build.VERSION.SDK_INT >= 14) {
                uiOption |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
            } else if (Build.VERSION.SDK_INT >= 16) {
                uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            } else if (Build.VERSION.SDK_INT >= 19) {
                uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }

            decorView.setSystemUiVisibility(uiOption);
        }
    }

    private void showControls() {
        controlsLayout.setVisibility(View.VISIBLE);
        Window window = this.getWindow();
        if (window == null) {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorView = window.getDecorView();
        if (decorView != null) {
            int uiOption = decorView.getSystemUiVisibility();

            if (Build.VERSION.SDK_INT >= 14) {
                uiOption &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
            } else if (Build.VERSION.SDK_INT >= 16) {
                uiOption &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            } else if (Build.VERSION.SDK_INT >= 19) {
                uiOption &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }

            decorView.setSystemUiVisibility(uiOption);
        }
    }

    private void setHandler() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (videoView.getDuration() > 0) {
                    int currPosition = videoView.getCurrentPosition();
                    videoSeekBar.setProgress(currPosition);
                    videoTime.setText(convertTime(videoView.getDuration() - currPosition));
                }
                handler.postDelayed(this, 0);
            }
        };
        handler.postDelayed(runnable, 500);
    }

    private String convertTime(int ms) {
        int x = ms / 1000;

        int seconds = x % 60;
        x /= 60;

        int minutes = x % 60;
        x /= 60;

        int hours = x % 24;

        if (hours != 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    private void initializeSeekBar() {
        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (videoSeekBar.getId() == R.id.video_controls_seekbar && fromUser) {
                    videoView.seekTo(progress);
                    videoView.start();
                    videoTime.setText(convertTime(videoView.getDuration() - videoView.getCurrentPosition()));
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
}