package com.mksk.rxdemo.data.model;

import com.google.gson.annotations.SerializedName;

public class Airline {
    private int id;
    private String name;
    private String country;
    private String logo;
    private String slogan;
    @SerializedName("head_quaters")
    private String headQuarters;
    private String website;
    private String established;

    public Airline() {
    }

    public Airline(int id, String name, String country, String logo, String slogan, String headQuarters, String website, String established) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.logo = logo;
        this.slogan = slogan;
        this.headQuarters = headQuarters;
        this.website = website;
        this.established = established;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getHeadQuarters() {
        return headQuarters;
    }

    public void setHeadQuarters(String headQuarters) {
        this.headQuarters = headQuarters;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEstablished() {
        return established;
    }

    public void setEstablished(String established) {
        this.established = established;
    }
}
