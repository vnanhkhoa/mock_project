package com.mksk.rxdemo.data.network.response;

import com.mksk.rxdemo.data.model.Airline;

import java.util.ArrayList;

public class Datum {
    private String _id;
    private String name;
    private int trips;
    private ArrayList<Airline> airline;
    private int __v;

    public Datum() {
    }

    public Datum(String _id, String name, int trips, ArrayList<Airline> airline, int __v) {
        this._id = _id;
        this.name = name;
        this.trips = trips;
        this.airline = airline;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrips() {
        return trips;
    }

    public void setTrips(int trips) {
        this.trips = trips;
    }

    public ArrayList<Airline> getAirline() {
        return airline;
    }

    public void setAirline(ArrayList<Airline> airline) {
        this.airline = airline;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
