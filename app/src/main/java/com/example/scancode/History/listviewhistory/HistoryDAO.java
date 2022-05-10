package com.example.scancode.History.listviewhistory;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDAO {
    @Insert
    void insertUser(History history);
    @Query("SELECT * FROM CreateHistory")
    List<History> getListUser();

}
