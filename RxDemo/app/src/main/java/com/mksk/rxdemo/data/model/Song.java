package com.mksk.rxdemo.data.model;

import com.google.gson.annotations.SerializedName;

public class Song {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("artists_names")
    private String artistsNames;

    @SerializedName("thumbnail")
    private String thumbnail;

    private String titleAlbum;

    public Song() {
    }

    public Song(String id, String title, String artistsNames, String thumbnail, String titleAlbum) {
        this.id = id;
        this.title = title;
        this.artistsNames = artistsNames;
        this.thumbnail = thumbnail;
        this.titleAlbum = titleAlbum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistsNames() {
        return artistsNames;
    }

    public void setArtistsNames(String artistsNames) {
        this.artistsNames = artistsNames;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitleAlbum() {
        if (titleAlbum == null) {
            setTitleAlbum(this.title + " (Single)");
        }
        return this.titleAlbum;
    }

    public void setTitleAlbum(String titleAlbum) {
        this.titleAlbum = titleAlbum;
    }
}
