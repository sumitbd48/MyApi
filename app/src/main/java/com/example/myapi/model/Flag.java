package com.example.myapi.model;

public class Flag {

    private int id;
    private String country;
    private String file;

    String flag;
    public Flag(int id, String country, String file) {
        this.id = id;
        this.country = country;
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getFile() {
        return file;
    }
}
