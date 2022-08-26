package com.example.themoviedb.request;

import com.example.themoviedb.utils.Credenciales;
import com.example.themoviedb.utils.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Services {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(Credenciales.URL_BASE).addConverterFactory(GsonConverterFactory.create());

    private  static Retrofit retrofit = retrofitBuilder.build();

    private  static MovieApi movieApi= retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){
        return movieApi;
    }
}
