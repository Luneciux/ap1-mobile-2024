package com.example.ap1_mobile;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class Task {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "desc")
    public String desc;

    public Task(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task : " + this.title + " " + this.desc;
    }
}
