package com.example.themoviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.themoviedb.adapters.MovieRecyclerView;
import com.example.themoviedb.adapters.OnMovieListerner;
import com.example.themoviedb.models.MovieModel;
import com.example.themoviedb.request.Services;
import com.example.themoviedb.response.MovieListResponse;
import com.example.themoviedb.utils.Credenciales;
import com.example.themoviedb.utils.MovieApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMovieListerner {

    private RecyclerView recyclerViewTrending, recyclerViewUpComing, recyclerViewRecommendations;
    private MovieRecyclerView movieRecyclerTrendingAdapter, movieRecyclerUpComingAdapter, movieRecyclerRecommendationsAdapter;
    private List<MovieModel> moviesTrending;
    private TextView based_text, result_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Themoviedb);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        recyclerViewTrending = findViewById(R.id.recyclerViewTrending);
        recyclerViewUpComing= findViewById(R.id.recyclerViewUpComig);
        recyclerViewRecommendations= findViewById(R.id.recyclerViewRecommendations);
        based_text = findViewById(R.id.based_text);
        result_text = findViewById(R.id.result_text);
        intAdapter();
        GetRetrofitResponseUpComing();
        GetRetrofitResponseTrending();

    }




    private  void intAdapter(){
        movieRecyclerTrendingAdapter = new MovieRecyclerView(this,false, "trending");
        movieRecyclerUpComingAdapter = new MovieRecyclerView(this,false, "upcoming");
        movieRecyclerRecommendationsAdapter = new MovieRecyclerView(this,true, "recommendations");

        recyclerViewTrending.setAdapter(movieRecyclerTrendingAdapter);
        recyclerViewTrending.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false ));

        recyclerViewUpComing.setAdapter(movieRecyclerUpComingAdapter);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false ));

        recyclerViewRecommendations.setAdapter(movieRecyclerRecommendationsAdapter);
        recyclerViewRecommendations.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void  GetRetrofitResponseTrending(){
        MovieApi movieApi = Services.getMovieApi();
        Call<MovieListResponse> responseCall = movieApi.trending(Credenciales.API_KEY);

        responseCall.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.code() == 200){
                    moviesTrending = new ArrayList<>(response.body().getMovies());
                    movieRecyclerTrendingAdapter.setmMovies(moviesTrending);
                }else{
                    try {
                        Log.e("Cledata", "Response error" + response.errorBody().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {

            }
        });
    }

    private void  GetRetrofitResponseUpComing(){
        MovieApi movieApi = Services.getMovieApi();
        Call<MovieListResponse> responseCall = movieApi.upcoming(Credenciales.API_KEY);

        responseCall.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.code() == 200){
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    movieRecyclerUpComingAdapter.setmMovies(movies);
                }else{
                    try {
                        Log.e("Cledata", "Response error" + response.errorBody().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {

            }
        });
    }

    private void  GetRetrofitResponseRecommendations(MovieModel movieModel){
        final int limit = 6;
        MovieApi movieApi = Services.getMovieApi();
        based_text.setText("Basado en "+movieModel.getTitle());
        result_text.setText(R.string.result);
        Call<MovieListResponse> responseCall = movieApi.recommendations(movieModel.getId(), Credenciales.API_KEY);

        responseCall.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.code() == 200){
                    List<MovieModel> _movies = new ArrayList<>(response.body().getMovies());
                    if(_movies.size() == 0){
                        result_text.setText(R.string.result);
                    }else{
                        result_text.setText("Mostrando: "+ String.valueOf(limit) + " de "+ String.valueOf(response.body().getTotal_results()));
                    }
                    List<MovieModel> movies = new ArrayList<>();
                    int l =limit;
                    if(_movies.size() < limit ){
                        l = _movies.size();
                    }
                    for (int i = 0; i < l; i++) {
                        movies.add(_movies.get(i));
                    }
                    movieRecyclerRecommendationsAdapter.setmMovies(movies);
                }else{
                    try {
                        Log.e("Cledata", "Response error" + response.errorBody().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onMovieClick(int position, String type) {
        if(type == "trending"){
            MovieModel movieModel = moviesTrending.get(position);
            Log.e("Cledata", "onMovieClick: ."+ movieModel.getId() );
            Log.e("Cledata", "onMovieClick: ."+ movieModel.getTitle() );
            GetRetrofitResponseRecommendations(movieModel);
        }
        Log.e("Cledata", "Position" + position);
        Log.e("Cledata", "type:"+ type);
    }
}