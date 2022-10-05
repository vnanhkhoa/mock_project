package com.mksk.rxdemo.data.network.response;

import com.google.gson.annotations.SerializedName;
import com.mksk.rxdemo.data.model.Song;

import java.util.ArrayList;

public class DataResponse {

    @SerializedName(value = "songs", alternate = {"song", "items"})
    private ArrayList<Song> songs;

    public DataResponse() {
    }

    public DataResponse(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}
