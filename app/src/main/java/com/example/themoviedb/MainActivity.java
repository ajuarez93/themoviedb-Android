package com.example.themoviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.themoviedb.adapters.MovieRecyclerView;
import com.example.themoviedb.adapters.OnMovieListerner;
import com.example.themoviedb.models.MovieModel;
import com.example.themoviedb.request.Services;
import com.example.themoviedb.response.MovieListResponse;
import com.example.themoviedb.utils.Credenciales;
import com.example.themoviedb.utils.MovieApi;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMovieListerner {

    private RecyclerView recyclerViewTrending, recyclerViewUpComing, recyclerViewDiscover;
    private MovieRecyclerView movieRecyclerTrendingAdapter, movieRecyclerUpComingAdapter, movieRecyclerDiscoverAdapter;
    private List<MovieModel> moviesTrending;
    private TextView result_text;
    private MaterialButton btn_1993, btn_espaniol;


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
        recyclerViewDiscover= findViewById(R.id.recyclerViewDiscover);
        result_text = findViewById(R.id.result_text);

        btn_espaniol =  findViewById(R.id.btn_espaniol);
        btn_1993 =  findViewById(R.id.btn_1993);

        btn_espaniol.setCheckable(true);
        btn_espaniol.addOnCheckedChangeListener((button, isChecked) -> {
            if(isChecked){
                btn_espaniol.setBackgroundColor(getResources().getColor(android.R.color.white));
                btn_espaniol.setTextColor(getResources().getColor(android.R.color.black));
            }else{
                btn_espaniol.setBackgroundColor(getResources().getColor(android.R.color.black));
                btn_espaniol.setTextColor(getResources().getColor(android.R.color.white));
            }
            String espaniol = "";
            if(isChecked) espaniol = "es";
            String year = "";
            if(btn_1993.isChecked()) year = "1993";

            GetRetrofitResponseDiscover(espaniol, year);
        });

        btn_1993.setCheckable(true);
        btn_1993.addOnCheckedChangeListener((button, isChecked) -> {
            if(isChecked){
                btn_1993.setBackgroundColor(getResources().getColor(android.R.color.white));
                btn_1993.setTextColor(getResources().getColor(android.R.color.black));
            }else{
                btn_1993.setBackgroundColor(getResources().getColor(android.R.color.black));
                btn_1993.setTextColor(getResources().getColor(android.R.color.white));
            }

            String espaniol = "";
            if(btn_espaniol.isChecked()) espaniol = "es";
            String year = "";
            if(isChecked) year = "1993";

            GetRetrofitResponseDiscover(espaniol, year);

        });



        intAdapter();
        GetRetrofitResponseUpComing();
        GetRetrofitResponseTrending();
        GetRetrofitResponseDiscover("", "");
    }




    private  void intAdapter(){
        movieRecyclerTrendingAdapter = new MovieRecyclerView(this,false, "trending");
        movieRecyclerUpComingAdapter = new MovieRecyclerView(this,false, "upcoming");
        movieRecyclerDiscoverAdapter = new MovieRecyclerView(this,true, "discover");

        recyclerViewTrending.setAdapter(movieRecyclerTrendingAdapter);
        recyclerViewTrending.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false ));

        recyclerViewUpComing.setAdapter(movieRecyclerUpComingAdapter);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false ));

        recyclerViewDiscover.setAdapter(movieRecyclerDiscoverAdapter);
        recyclerViewDiscover.setLayoutManager(new GridLayoutManager(this, 2));
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
        //based_text.setText("Basado en "+movieModel.getTitle());
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
                    //movieRecyclerRecommendationsAdapter.setmMovies(movies);
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

    private void  GetRetrofitResponseDiscover(String with_original_language, String year){
        final int limit = 6;
        MovieApi movieApi = Services.getMovieApi();
        Call<MovieListResponse> responseCall = movieApi.discover(Credenciales.API_KEY, with_original_language, String.valueOf(year));

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
                    movieRecyclerDiscoverAdapter.setmMovies(movies);
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
    public void onMovieClick(int p, String type) {
        if(type == "trending"){
            MovieModel movieModel = moviesTrending.get(p);
            Log.e("Cledata", "onMovieClick: ."+ movieModel.getId() );
            Log.e("Cledata", "onMovieClick: ."+ movieModel.getTitle() );
            //GetRetrofitResponseRecommendations(movieModel);
        }
        Log.e("Cledata", "Position" + p);
        Log.e("Cledata", "type:"+ type);
    }
}