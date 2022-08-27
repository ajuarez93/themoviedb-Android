package com.example.themoviedb;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.themoviedb.models.MovieModel;
import com.example.themoviedb.request.Services;
import com.example.themoviedb.utils.Credenciales;
import com.example.themoviedb.utils.MovieApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    ImageView image_cover;
    TextView movie_name, release_date, original_language, vote_average;
    ImageButton btn_back;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Themoviedb);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        image_cover = findViewById(R.id.image_cover);
        movie_name = findViewById(R.id.movie_name);
        release_date = findViewById(R.id.release_date);
        original_language = findViewById(R.id.original_language);
        vote_average = findViewById(R.id.vote_average);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> {
            finish();
        });
        Bundle b = getIntent().getExtras();
        if(b != null){
            id = b.getInt("id");
        }
        GetRetrofitResponseDetail(id);
    }


    private void  GetRetrofitResponseDetail(int id){
        MovieApi movieApi = Services.getMovieApi();
        Call<MovieModel> responseCall = movieApi.detail(id, Credenciales.API_KEY);
        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200){
                    MovieModel movieModel = response.body();
                    dataSet(movieModel);
                }else{
                    try {
                        Log.e("Cledata", "Response error" + response.errorBody().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }
    public void dataSet(MovieModel movieModel){
        try {
            Glide.with(getApplicationContext()).load(Credenciales.URL_IMAGES +movieModel.getPoster_path()).into(image_cover);
            movie_name.setText(movieModel.getTitle());
            release_date.setText(movieModel.getRelease_date().split("-")[0]);
            original_language.setText(movieModel.getOriginal_language());
            vote_average.setText(String.format("%.2f", movieModel.getVote_average()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}