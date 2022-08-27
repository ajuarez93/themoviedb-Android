package com.example.themoviedb.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;

public class VideoViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

    OnVideoListener onVideoListener;
    ImageView video_image;
    TextView name, site, type, iso_639_1, iso_3166_1;
    public VideoViewHolder(@NonNull View itemView, OnVideoListener onVideoListener) {
        super(itemView);
        this.onVideoListener = onVideoListener;

        video_image = itemView.findViewById(R.id.video_image);
        name = itemView.findViewById(R.id.name);
        site = itemView.findViewById(R.id.site);
        type = itemView.findViewById(R.id.type);
        iso_639_1 = itemView.findViewById(R.id.iso_639_1);
        iso_3166_1 = itemView.findViewById(R.id.iso_3166_1);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onVideoListener.onVideoClick(getAdapterPosition());
    }
}
