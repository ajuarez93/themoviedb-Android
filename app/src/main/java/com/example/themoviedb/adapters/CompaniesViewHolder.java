package com.example.themoviedb.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;

public class CompaniesViewHolder extends RecyclerView.ViewHolder  {

    ImageView company_image;
    public CompaniesViewHolder(@NonNull View itemView) {
        super(itemView);
        company_image = itemView.findViewById(R.id.company_image);
    }
}
