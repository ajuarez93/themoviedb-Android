package com.example.themoviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    // Clase para nuestras peliculas

    private String title;
    private String poster_path;
    private int id;
    private String release_date;
    private String original_language;
    private float vote_average;


    public MovieModel(String title, String poster_path, int id, String release_date, String original_language, float vote_average) {
        this.title = title;
        this.poster_path = poster_path;
        this.id = id;
        this.release_date = release_date;
        this.original_language = original_language;
        this.vote_average = vote_average;
    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        id = in.readInt();
        release_date = in.readString();
        original_language = in.readString();
        vote_average = in.readFloat();
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

    public String getRelease_date() {
        return release_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public float getVote_average() {
        return vote_average;
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


    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
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
        parcel.writeString(release_date);
        parcel.writeString(original_language);
        parcel.writeFloat(vote_average);
    }
}
