package com.example.themoviedb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.themoviedb.R;
import com.example.themoviedb.models.MovieModel;
import com.example.themoviedb.models.VideoModel;
import com.example.themoviedb.utils.Credenciales;

import java.util.List;

public class VideoRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VideoModel> mVideos;
    private OnVideoListener onVideoListener;

    public VideoRecyclerView(OnVideoListener onVideoListener) {
        this.onVideoListener = onVideoListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);
        return new VideoViewHolder(view, onVideoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoModel video = mVideos.get(position);

        ImageView video_image = ((VideoViewHolder) holder).video_image;
        TextView name = ((VideoViewHolder) holder).name;
        TextView site = ((VideoViewHolder) holder).site;
        TextView type = ((VideoViewHolder) holder).type;
        TextView iso_639_1 = ((VideoViewHolder) holder).iso_639_1;
        TextView iso_3166_1 = ((VideoViewHolder) holder).iso_3166_1;

        if(video.getSite().equals("YouTube")){
            Glide.with(holder.itemView.getContext()).load(Credenciales.URL_IMAGES_YOUTUBE +video.getKey() +"/0.jpg").into(video_image);
        }

        name.setText(video.getName());
        site.setText(video.getSite());
        type.setText(video.getType());
        iso_639_1.setText(video.getIso_639_1());
        iso_3166_1.setText(video.getIso_3166_1());


    }

    @Override
    public int getItemCount() {
        if(mVideos == null){
            return  0;
        }
        return mVideos.size();
    }

    public void setmVideos(List<VideoModel> mVideos) {
        this.mVideos = mVideos;
        notifyDataSetChanged();
    }
}
