package com.example.movies;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract MovieDAO movieDAO();
}