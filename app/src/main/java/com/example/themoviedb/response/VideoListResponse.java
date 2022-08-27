package com.example.themoviedb.response;

import com.example.themoviedb.models.MovieModel;
import com.example.themoviedb.models.VideoModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoListResponse {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("results")
    @Expose
    private List<VideoModel> movies;

    public int getId(){
        return  id;
    }
    public  List<VideoModel> getVideos(){
        return movies;
    }

    @Override
    public String toString() {
        return "MovieListResponse{" +
                "total_results=" + id +
                ", movies=" + movies +
                '}';
    }
}
