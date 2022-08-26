package com.example.themoviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    // Clase para nuestras peliculas

    private String title;
    private String poster_path;
    private int id;
    private int total_results;


    public MovieModel(String title, String poster_path, int id, int total_results) {
        this.title = title;
        this.poster_path = poster_path;
        this.id = id;
    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        id = in.readInt();
        total_results = in.readInt();
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

    public int getId() {
        return id;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeInt(id);
        parcel.writeInt(total_results);
    }
}
