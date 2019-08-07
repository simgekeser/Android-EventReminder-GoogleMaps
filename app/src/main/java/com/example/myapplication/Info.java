package com.example.myapplication;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.widget.EditText;

import com.example.myapplication.Login.Event;


@Entity(tableName = "dialog", foreignKeys = @ForeignKey(entity = Event.class,
        parentColumns = "username",
        childColumns = "Username",
        onDelete = ForeignKey.CASCADE))

public class Info {


    public Info(String event, String detail, String spinner, double lat, double lit,String username){

        this.location = new Location(lat,lit);
        this.event  = event;
        this.detail = detail;
        this.spinner = spinner;
        this.username_info=username;

     }


    public String getUsername() {
        return username_info;
    }

    public void setUsername(String username) {
        this.username_info = username;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name="event_name")
    private String event;

    @ColumnInfo(name="detail_name")
    private String detail;

    @ColumnInfo(name="type_name")
    private String spinner;

    @Embedded
    public Location location;


    public Location getLocation() {
        return location;
    }


    @ColumnInfo(name="Username")
    private String username_info;


    public Info() {
        //empty constucter is requiered
    }

    public String getUsername_info() {
        return username_info;
    }

    public void setUsername_info(String username_info) {
        this.username_info = username_info;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

}
