package com.example.myapplication;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.myapplication.Login.Event;

import java.util.List;

@Dao
public interface MyDao {

    @Query("SELECT * FROM dialog WHERE username =:user")
    LiveData<List<Info>> getAllInfo(String user);

    @Query("SELECT * FROM Login WHERE username=:username and password= :password")
    Event user(String username,String password);


    @Query("UPDATE dialog SET event_name = :event ,detail_name= :detail,type_name= :type  WHERE id LIKE :id ")
    int updateItem(long id, String event, String detail, String type);

    @Insert()
    void insertAll(Info... dialogs);

    @Insert()
    void insertLogin(Event... events);


    @Query("UPDATE dialog SET lat= :lat_ ,lit= :lit_  WHERE id LIKE :id ")
    int updateLocation(long id, double lat_,double lit_);


}
