package com.example.themoviedb.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;

public class MovieViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

    ImageView movie_image;
    private String type;
    OnMovieListerner onMovieListerner;

    public MovieViewHolder(@NonNull View itemView, OnMovieListerner onMovieListerner, String type) {
        super(itemView);
        this.onMovieListerner = onMovieListerner;
        this.type = type;
        movie_image = itemView.findViewById(R.id.movie_image);
        movie_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onMovieListerner.onMovieClick(getAdapterPosition(), this.type);
    }
}
