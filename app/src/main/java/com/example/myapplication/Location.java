package com.example.myapplication;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;

@Entity
public class Location  {

    public double lat;
    public double lit;

    public Location(double lat, double lit) {
        this.lat = lat;
        this.lit = lit;
    }
}

