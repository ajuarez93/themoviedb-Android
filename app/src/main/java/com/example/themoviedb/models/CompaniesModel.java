package com.example.themoviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CompaniesModel implements Parcelable {

    private int id;
    private String logo_path;
    private String name;

    public CompaniesModel(int id, String logo_path, String name) {
        this.id = id;
        this.logo_path = logo_path;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected CompaniesModel(Parcel in) {
        logo_path =in.readString();
        name =in.readString();
        id =in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(logo_path);
        parcel.writeString(name);
        parcel.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CompaniesModel> CREATOR = new Creator<CompaniesModel>() {
        @Override
        public CompaniesModel createFromParcel(Parcel in) {
            return new CompaniesModel(in);
        }

        @Override
        public CompaniesModel[] newArray(int size) {
            return new CompaniesModel[size];
        }
    };
}
