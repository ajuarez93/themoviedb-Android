package com.example.themoviedb.utils;

import com.example.themoviedb.models.MovieModel;
import com.example.themoviedb.models.VideoModel;
import com.example.themoviedb.response.MovieListResponse;
import com.example.themoviedb.response.VideoListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // URL ejemplo https://api.themoviedb.org/3/trending/movie/day?api_key=b0228fac6060384f563dc413fd933a6f
    @GET("3/trending/movie/day?")
    Call<MovieListResponse>  trending(
            @Query("api_key") String api_key
    );

    @GET("3/movie/upcoming?")
    Call<MovieListResponse>  upcoming(
            @Query("api_key") String api_key
    );

    @GET("3/movie/{movie_id}")
    Call<MovieModel> detail(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    @GET("3/movie/{movie_id}/recommendations?")
    Call<MovieListResponse> recommendations(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    @GET("3/discover/movie?")
    Call<MovieListResponse> discover(
            @Query("api_key") String api_key,
            @Query("with_original_language") String with_original_language,
            @Query("year") String year
    );

    @GET("3/movie/{movie_id}/videos?")
    Call<VideoListResponse> videos(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

}
