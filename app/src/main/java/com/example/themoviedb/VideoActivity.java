package com.example.themoviedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.themoviedb.adapters.OnVideoListener;
import com.example.themoviedb.adapters.VideoRecyclerView;
import com.example.themoviedb.models.MovieModel;
import com.example.themoviedb.models.VideoModel;
import com.example.themoviedb.request.Services;
import com.example.themoviedb.response.VideoListResponse;
import com.example.themoviedb.utils.Credenciales;
import com.example.themoviedb.utils.MovieApi;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity implements OnVideoListener {
    YouTubePlayerView youtube_player_view;
    TextView movie_title;
    ImageButton btn_back;
    List<VideoModel> videos;

    LinearLayout layout;
    private RecyclerView recyclerVideosView;
    private VideoRecyclerView videoRecyclerAdapter;
    private  String videoId;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        String title = "";
        Bundle b = getIntent().getExtras();
        if(b != null){
            id = b.getInt("id");
            title = b.getString("title");
        }
        movie_title = findViewById(R.id.movie_title);
        movie_title.setText(title);

        layout = findViewById(R.id.layout);

        youtube_player_view = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youtube_player_view);

        recyclerVideosView = findViewById(R.id.recyclerVideosView);
        intAdapter();
        GetRetrofitResponseVideos(id);
    }
    private  void intAdapter(){
        videoRecyclerAdapter = new VideoRecyclerView(this);
        recyclerVideosView.setAdapter(videoRecyclerAdapter);
        recyclerVideosView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }



    private void GetRetrofitResponseVideos(int movie_id){
        MovieApi movieApi = Services.getMovieApi();
        Call<VideoListResponse> responseCall = movieApi.videos(movie_id, Credenciales.API_KEY);
        responseCall.enqueue(new Callback<VideoListResponse>() {
            @Override
            public void onResponse(Call<VideoListResponse> call, Response<VideoListResponse> response) {
                if (response.code() == 200){
                    videos= new ArrayList<>(response.body().getVideos());
                    videoRecyclerAdapter.setmVideos(videos);
                    VideoModel videoTrailer = null;
                    for (VideoModel v: videos){
                         if(v.getSite().equals("YouTube") && v.getType().equals("Trailer")){
                            videoTrailer = v;
                        }
                    }
                    if(videoTrailer != null){
                        videoId = videoTrailer.getKey();
                        youtube_player_view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                youTubePlayer.loadVideo(videoId, 0);
                            }
                        });
                    }

                }else{
                    try {
                        Log.e("Cledata", "Response error" + response.errorBody().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoListResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youtube_player_view.release();
    }

    @Override
    public void onVideoClick(int p) {

        VideoModel v = videos.get(p);
        videoId = v.getKey();
        Log.v("Cledata", v.getSite());
        Log.v("Cledata", v.getType());
        youtube_player_view.release();

        YouTubePlayerView youTubePlayerView = new YouTubePlayerView(this);
        layout.addView(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }
}