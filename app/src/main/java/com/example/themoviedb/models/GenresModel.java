package com.example.themoviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GenresModel implements Parcelable {
    private int id;
    private String name;

    public GenresModel(int id, String name) {
        this.id = id;
        this.name = name;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    protected GenresModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<GenresModel> CREATOR = new Creator<GenresModel>() {
        @Override
        public GenresModel createFromParcel(Parcel in) {
            return new GenresModel(in);
        }

        @Override
        public GenresModel[] newArray(int size) {
            return new GenresModel[size];
        }
    };
}
