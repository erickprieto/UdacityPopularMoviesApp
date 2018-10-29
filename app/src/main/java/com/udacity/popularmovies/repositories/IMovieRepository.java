package com.udacity.popularmovies.repositories;

import android.arch.lifecycle.LiveData;

import com.udacity.popularmovies.models.Movie;

import java.util.List;

public interface IMovieRepository {

    LiveData<List<Movie>> getPopularMovies();
    LiveData<List<Movie>> getRatedMovies();
    LiveData<List<Movie>> getFavoriteMovies();
}