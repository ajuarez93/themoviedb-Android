package com.example.themoviedb.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;

public class MovieViewHolder extends RecyclerView.ViewHolder  {

    ImageView movie_image;


    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        movie_image = itemView.findViewById(R.id.movie_image);
        movie_image.setOnClickListener(view -> {

        });
    }

}
