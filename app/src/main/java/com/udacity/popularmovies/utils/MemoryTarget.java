package com.udacity.popularmovies.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.udacity.popularmovies.PopularMoviesApplication;
import com.udacity.popularmovies.events.ImageDownloadedEvent;
import com.udacity.popularmovies.services.PopularMoviesDatabaseService;


public class MemoryTarget implements Target {

    private Context ctx;
    private String url;
    private int movieId;


    public MemoryTarget(int movieId, String url, Context ctx) {
        this.movieId = movieId;
        this.url = url;
        this.ctx = ctx;
    }

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                PopularMoviesApplication.getEventBus().post(new ImageDownloadedEvent(movieId, url, bitmap));
            }
        }).start();

    }


    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
