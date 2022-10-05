package com.mksk.rxdemo.data.network.response;

import java.util.ArrayList;

public class Response {
    private int totalPassengers;
    private int totalPages;
    private ArrayList<Datum> data;

    public Response() {
    }

    public Response(int totalPassengers, int totalPages, ArrayList<Datum> data) {
        this.totalPassengers = totalPassengers;
        this.totalPages = totalPages;
        this.data = data;
    }

    public int getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(int totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }
}
