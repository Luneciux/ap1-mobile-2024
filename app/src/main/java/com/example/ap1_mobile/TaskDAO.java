package com.example.ap1_mobile;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE uid in (:taskIds)")
    List<Task> loadAllByIds(int[] taskIds);

    @Query("SELECT * FROM task WHERE title LIKE :title AND " +
            "desc LIKE :desc LIMIT 1"
    )
    Task findByName(String title, String desc);

    @Insert
    void insertAll(Task... users);

    @Delete
    void delete(Task user);

}
