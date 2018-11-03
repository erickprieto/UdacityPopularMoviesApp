package com.udacity.popularmovies.database;


import android.arch.persistence.room.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.udacity.popularmovies.database.dao.FavoriteDao;
import com.udacity.popularmovies.database.dao.MovieDao;
import com.udacity.popularmovies.database.entities.FavoriteEntity;
import com.udacity.popularmovies.database.entities.MovieEntity;
import com.udacity.popularmovies.utils.AppExecutors;


@Database(entities = {MovieEntity.class, FavoriteEntity.class}, version = 1, exportSchema = false)
public abstract class PopularMoviesDatabase extends RoomDatabase {

    public static final String NAME_POPULAR_MOVIES_DATABASE = "PopularMoviesDatabase";

    private static PopularMoviesDatabase database;

    public abstract MovieDao movieDao();

    public abstract FavoriteDao favoriteDao();

    public static PopularMoviesDatabase getDatabase(final Context context) {

        if (database == null) {
                if (database == null) {
                    database = Room
                            .databaseBuilder(context
                                    , PopularMoviesDatabase.class
                                    , NAME_POPULAR_MOVIES_DATABASE)
                            .build();
                }
        }

        return database;
    }
}
