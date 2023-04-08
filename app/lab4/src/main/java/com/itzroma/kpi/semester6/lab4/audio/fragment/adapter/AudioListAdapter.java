package com.itzroma.kpi.semester6.lab4.audio.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itzroma.kpi.semester6.lab4.R;
import com.itzroma.kpi.semester6.lab4.audio.AudioPlayerActivity;
import com.itzroma.kpi.semester6.lab4.audio.MyAudioPlayer;
import com.itzroma.kpi.semester6.lab4.audio.model.AudioModel;

import java.io.Serializable;
import java.util.List;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.AudioViewHolder> {

    private final List<AudioModel> audioList;
    private final Context context;

    public AudioListAdapter(List<AudioModel> audioList, Context context) {
        this.audioList = audioList;
        this.context = context;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.audio_recycler_item, parent, false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        AudioModel currentAudio = audioList.get(position);

        holder.audioItemTitle.setText(currentAudio.getTitle());
        holder.itemView.setOnClickListener(v -> {
            MyAudioPlayer.getInstance().reset();
            MyAudioPlayer.setCurrentIndex(position);
            Intent intent = new Intent(context, AudioPlayerActivity.class);
            intent.putExtra("LIST", (Serializable) audioList);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    public static class AudioViewHolder extends RecyclerView.ViewHolder {

        private final TextView audioItemTitle;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            audioItemTitle = itemView.findViewById(R.id.audio_item_title);
        }
    }
}
