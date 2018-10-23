package com.udacity.popularmovies.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

import com.udacity.popularmovies.database.dao.MovieDao;
import com.udacity.popularmovies.database.entities.MovieEntity;


@Database(entities = {MovieEntity.class}, version = 1)
public abstract class PopularMoviesDatabase extends RoomDatabase {

        public abstract MovieDao movieDao();
}
