package com.example.myapplication.Login;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "login")
public class Event {


        public Event(String username, String name, String surname, String email, String password){
        this.username=username;
        this.name=name;
        this.surname=surname;
        this.password=password;
        this.email=email;
    }



    @PrimaryKey
    @NonNull String username ;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name="surname")
    String surname;

    @ColumnInfo(name = "email")
    String email;

    @ColumnInfo(name = "password")
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
