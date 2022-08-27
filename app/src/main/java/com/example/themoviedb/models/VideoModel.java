package com.example.themoviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoModel implements Parcelable {
    private String name;
    private String key;
    private String site;
    private String type;
    private String iso_639_1;
    private String iso_3166_1;
    private Boolean official;
    private String published_at;

    public VideoModel(String name, String key, String site, String type, String iso_639_1, String iso_3166_1, Boolean official, String published_at) {
        this.name = name;
        this.key = key;
        this.site = site;
        this.type = type;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.official = official;
        this.published_at = published_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    protected VideoModel(Parcel in) {
        name = in.readString();
        key = in.readString();
        site = in.readString();
        type = in.readString();
        iso_639_1 = in.readString();
        iso_3166_1 = in.readString();
        byte tmpOfficial = in.readByte();
        official = tmpOfficial == 0 ? null : tmpOfficial == 1;
        published_at = in.readString();
    }

    public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel in) {
            return new VideoModel(in);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(key);
        parcel.writeString(site);
        parcel.writeString(type);
        parcel.writeString(iso_639_1);
        parcel.writeString(iso_3166_1);
        parcel.writeByte((byte) (official == null ? 0 : official ? 1 : 2));
        parcel.writeString(published_at);
    }
}
