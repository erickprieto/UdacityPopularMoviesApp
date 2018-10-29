package com.udacity.popularmovies.database;


import android.arch.persistence.room.Database;

import android.arch.persistence.room.RoomDatabase;

import com.udacity.popularmovies.database.dao.FavoriteDao;
import com.udacity.popularmovies.database.dao.MovieDao;
import com.udacity.popularmovies.database.entities.FavoriteEntity;
import com.udacity.popularmovies.database.entities.MovieEntity;


@Database(entities = {MovieEntity.class, FavoriteEntity.class}, version = 1, exportSchema = false)
public abstract class PopularMoviesDatabase extends RoomDatabase {

    public static final String NAME_POPULAR_MOVIES_DATABASE = "PopularMoviesDatabase";

    public abstract MovieDao movieDao();

    public abstract FavoriteDao favoriteDao();
}
