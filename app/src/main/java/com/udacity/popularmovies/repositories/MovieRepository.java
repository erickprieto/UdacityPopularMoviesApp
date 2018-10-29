package com.udacity.popularmovies.repositories;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.udacity.popularmovies.PopularMoviesApplication;
import com.udacity.popularmovies.events.DownloadDiscoveryMovieListEvent;
import com.udacity.popularmovies.events.DownloadMovieListEvent;
import com.udacity.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.List;


public class MovieRepository implements IMovieRepository {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = MovieRepository.class.getSimpleName();

    /**
     * List of {@link Movie} from WebService or <code>Bundle</code>.
     */
    private MutableLiveData<List<Movie>> moviesMutable;



    public MovieRepository() {
        PopularMoviesApplication.getEventBus().register(this);

        this.moviesMutable = new MutableLiveData<List<Movie>>();
        moviesMutable.setValue(new ArrayList<Movie>());

    }

    public MovieRepository(MutableLiveData<List<Movie>> moviesMutable) {
        this.moviesMutable = moviesMutable;
    }


    @Override
    protected void finalize() throws Throwable {
        Log.v(TAG, "finalize");
        PopularMoviesApplication.getEventBus().unregister(this);
        super.finalize();
    }


    @Override
    public LiveData<List<Movie>> getPopularMovies() {
        Log.v(TAG, "getPopularMovies: ======");
        if (moviesMutable == null) {
            Log.v(TAG, "getPopularMovies: NULL ======");
            moviesMutable = new MutableLiveData<List<Movie>>();
            moviesMutable.setValue(new ArrayList<Movie>());
        }
        return moviesMutable;
    }

    @Override
    public LiveData<List<Movie>> getRatedMovies() {
        return this.moviesMutable;
    }

    @Override
    public LiveData<List<Movie>> getFavoriteMovies() {
        return this.moviesMutable;
    }






    public void updatePopularMovies(List<Movie> movies) {
        this.moviesMutable.setValue(movies);
    }

    @Subscribe
    public void downloadedDiscoveryMovieList(DownloadDiscoveryMovieListEvent event) {
        Log.v(TAG, "Repo D evento recibido ==============================================");
        updatePopularMovies(event.getModelos());
    }

    @Subscribe
    public void downloadedMovieList(DownloadMovieListEvent event) {
        Log.v(TAG, "Repo evento recibido ==============================================");
        updatePopularMovies(event.getModelos());
        Log.v(TAG, this.moviesMutable.toString());
    }
}
