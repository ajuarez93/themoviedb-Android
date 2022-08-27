package com.example.themoviedb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.themoviedb.R;
import com.example.themoviedb.models.CompaniesModel;
import com.example.themoviedb.utils.Credenciales;

import java.util.List;

public class CompaniesRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CompaniesModel> mCompanies;

    public CompaniesRecyclerView() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.companies_list_item, parent, false);
        return new CompaniesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = ((CompaniesViewHolder) holder).company_image;
        if(mCompanies.get(position).getLogo_path() != null){
            Glide.with(holder.itemView.getContext()).load(Credenciales.URL_IMAGES +mCompanies.get(position).getLogo_path()).into(imageView);
        }

    }

    @Override
    public int getItemCount() {
        if(mCompanies == null){
            return  0;
        }
        return mCompanies.size();
    }

    public void setmCompanies(List<CompaniesModel> mCompanies) {
        this.mCompanies = mCompanies;
        notifyDataSetChanged();
    }
}
