package com.example.scancode.History.recycleviewhistory;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDAO {
    @Insert
    void insertHistory(History history);

    @Query("SELECT * FROM CreateHistory ORDER BY id DESC")
    List<History> getListHistory();

    @Delete
    void deleteHistory(History history);
}
