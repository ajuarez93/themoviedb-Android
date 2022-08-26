package com.example.themoviedb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.themoviedb.R;
import com.example.themoviedb.models.MovieModel;
import com.example.themoviedb.utils.Credenciales;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private boolean imageFull;
    private String type;

    private OnMovieListerner onMovieListerner;

    public MovieRecyclerView(OnMovieListerner onMovieListerner, boolean imageFull, String type) {
        this.onMovieListerner = onMovieListerner;
        this.imageFull = imageFull;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view, onMovieListerner, type);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = ((MovieViewHolder) holder).movie_image;
        if(this.imageFull){
            imageView.getLayoutParams().height = 420;
        }
        Glide.with(holder.itemView.getContext()).load(Credenciales.URL_IMAGES +mMovies.get(position).getPoster_path()).into(imageView);
    }

    @Override
    public int getItemCount() {
        if(mMovies == null){
            return  0;
        }
        return mMovies.size();
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }
}
