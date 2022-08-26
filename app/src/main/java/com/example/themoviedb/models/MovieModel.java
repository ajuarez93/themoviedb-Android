package com.example.themoviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    // Clase para nuestras peliculas

    private String title;
    private String poster_path;
    private int movie_id;


    public MovieModel(String title, String poster_path, int movie_id) {
        this.title = title;
        this.poster_path = poster_path;
        this.movie_id = movie_id;
    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        movie_id = in.readInt();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeInt(movie_id);
    }
}
