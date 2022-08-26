package com.example.themoviedb.response;

import com.example.themoviedb.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieListResponse {

    @SerializedName("total_results")
    @Expose
    private int total_results;

    @SerializedName("results")
    @Expose
    private List<MovieModel> movies;

    public int getTotal_results(){
        return  total_results;
    }
    public  List<MovieModel> getMovies(){
        return movies;
    }

    @Override
    public String toString() {
        return "MovieListResponse{" +
                "total_results=" + total_results +
                ", movies=" + movies +
                '}';
    }
}
