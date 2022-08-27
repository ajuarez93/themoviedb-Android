package com.example.themoviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.themoviedb.adapters.MovieRecyclerView;
import com.example.themoviedb.adapters.OnMovieListener;
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

public class MainActivity extends AppCompatActivity implements OnMovieListener {

    private RecyclerView recyclerViewTrending, recyclerViewUpComing, recyclerViewDiscover;
    private MovieRecyclerView movieRecyclerTrendingAdapter, movieRecyclerUpComingAdapter, movieRecyclerDiscoverAdapter;
    private List<MovieModel> moviesTrending, moviesUpComing, moviesDiscover;
    private TextView result_text;
    private MaterialButton btn_1993, btn_spanish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Themoviedb);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        recyclerViewTrending = findViewById(R.id.recyclerViewTrending);
        recyclerViewUpComing= findViewById(R.id.recyclerViewUpComing);
        recyclerViewDiscover= findViewById(R.id.recyclerViewDiscover);
        result_text = findViewById(R.id.result_text);
        btn_spanish =  findViewById(R.id.btn_filter_spanish);
        btn_1993 =  findViewById(R.id.btn_filter_1993);

        btn_spanish.setCheckable(true);
        btn_spanish.addOnCheckedChangeListener((button, isChecked) -> {
            if(isChecked){
                btn_spanish.setBackgroundColor(getResources().getColor(android.R.color.white));
                btn_spanish.setTextColor(getResources().getColor(android.R.color.black));
            }else{
                btn_spanish.setBackgroundColor(getResources().getColor(android.R.color.black));
                btn_spanish.setTextColor(getResources().getColor(android.R.color.white));
            }
            String spanish = "";
            if(isChecked) spanish = "es";
            String year = "";
            if(btn_1993.isChecked()) year = "1993";

            GetRetrofitResponseDiscover(spanish, year);
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

            String spanish = "";
            if(btn_spanish.isChecked()) spanish = "es";
            String year = "";
            if(isChecked) year = "1993";

            GetRetrofitResponseDiscover(spanish, year);

        });



        intAdapter();
        GetRetrofitResponseUpComing();
        GetRetrofitResponseTrending();
        GetRetrofitResponseDiscover("", "");
    }




    private  void intAdapter(){
        movieRecyclerTrendingAdapter = new MovieRecyclerView(this,0, "trending");
        movieRecyclerUpComingAdapter = new MovieRecyclerView(this,0, "upcoming");
        movieRecyclerDiscoverAdapter = new MovieRecyclerView(this,1, "discover");

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
                    moviesUpComing = new ArrayList<>(response.body().getMovies());
                    movieRecyclerUpComingAdapter.setmMovies(moviesUpComing);
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
                        result_text.setText("Mostrando: "+ limit + " de "+ response.body().getTotal_results());
                    }
                    moviesDiscover = new ArrayList<>();
                    int l =limit;
                    if(_movies.size() < limit ){
                        l = _movies.size();
                    }
                    for (int i = 0; i < l; i++) {
                        moviesDiscover.add(_movies.get(i));
                    }
                    movieRecyclerDiscoverAdapter.setmMovies(moviesDiscover);
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
        MovieModel movieModel = null;
        switch (type) {
            case "trending":
                movieModel = moviesTrending.get(p);
                break;
            case "upcoming":
                movieModel = moviesUpComing.get(p);
                break;
            case "discover":
                movieModel = moviesDiscover.get(p);
                break;
        }

        if(movieModel != null){
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            Bundle b = new Bundle();
            b.putInt("id", movieModel.getId());
            intent.putExtras(b);
            startActivity(intent);
        }



    }
}