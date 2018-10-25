package com.udacity.popularmovies.services;

import android.app.Service;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;

import android.os.IBinder;

import android.support.annotation.Nullable;
import android.util.Log;

import com.udacity.popularmovies.database.PopularMoviesDatabase;
import com.udacity.popularmovies.database.entities.MovieEntity;
import com.udacity.popularmovies.utils.AppExecutors;

import java.util.Date;
import java.util.List;


public class PopularMoviesDatabaseService extends Service {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = PopularMoviesDatabaseService.class.getSimpleName();


    PopularMoviesDatabase db;
    Context context = this;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "onCreate ===========================================================");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy ===========================================================");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "onStartCommand ===========================================================");
        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                db = Room
                        .databaseBuilder(context
                                , PopularMoviesDatabase.class
                                , PopularMoviesDatabase.NAME_POPULAR_MOVIES_DATABASE)
                        .build();
                db.movieDao().insertMovie(
                        new MovieEntity(1
                                ,""
                                ,""
                                , new Date(1980,1,1)
                                ,2.0
                                ,""
                                ,null
                                ,0
                                ,0
                                , new Date(2018,1,1)));

                Object a = db.movieDao().getAll();


                Log.v(TAG, a != null ? a.toString() : "NULL ================================");

            }
       });

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
