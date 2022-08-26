package com.example.themoviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.themoviedb.adapters.MovieRecyclerView;
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

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Themoviedb);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewTrending);
        intAdapter();
        GetRetrofitResponse();
    }




    private  void intAdapter(){
       movieRecyclerAdapter = new MovieRecyclerView();
       recyclerView.setAdapter(movieRecyclerAdapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false ));

    }

    private void  GetRetrofitResponse(){
        MovieApi movieApi = Services.getMovieApi();
        Call<MovieListResponse> responseCall = movieApi.trending(Credenciales.API_KEY);

        responseCall.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.code() == 200){
                    Log.v("Cledata", "Te response "+ response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    movieRecyclerAdapter.setmMovies(movies);
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


}