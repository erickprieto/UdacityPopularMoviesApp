package com.udacity.popularmovies.viewmodels;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;


import com.udacity.popularmovies.PopularMoviesApplication;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.services.PopularMoviesRepositoryService;

import java.util.ArrayList;
import java.util.List;


/**
 * Used to hold {@link List} of {@link Movie}
 * from {@link com.udacity.popularmovies.activities.MainActivity}.
 * Holds {@link MutableLiveData} of database querys to display automatically on
 * {@link com.udacity.popularmovies.activities.MainActivity}.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class MainViewModel extends ViewModel {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = MainViewModel.class.getSimpleName();

    /**
     * List of {@link Movie} from WebService or <code>Bundle</code>.
     */
    private MutableLiveData<List<Movie>> moviesPopularMutable;
    private MutableLiveData<List<Movie>> moviesTopRatedMutable;
    private MutableLiveData<List<Movie>> moviesFavoritesMutable;


    /**
     * Default constructor.
     */
    public MainViewModel() {
        super();
        PopularMoviesApplication.getEventBus().register(this);

    }

    public MutableLiveData<List<Movie>> getMoviesPopularMutable() {
        return moviesPopularMutable;
    }

    public void setMoviesPopularMutable(MutableLiveData<List<Movie>> moviesPopularMutable) {
        this.moviesPopularMutable = moviesPopularMutable;
    }

    public MutableLiveData<List<Movie>> getMoviesTopRatedMutable() {
        return moviesTopRatedMutable;
    }

    public void setMoviesTopRatedMutable(MutableLiveData<List<Movie>> moviesTopRatedMutable) {
        this.moviesTopRatedMutable = moviesTopRatedMutable;
    }

    public MutableLiveData<List<Movie>> getMoviesFavoritesMutable() {
        return moviesFavoritesMutable;
    }

    public void setMoviesFavoritesMutable(MutableLiveData<List<Movie>> moviesFavoritesMutable) {
        this.moviesFavoritesMutable = moviesFavoritesMutable;
    }

    @Override
    protected void onCleared() {
        Log.v(TAG, "onCleared()");
        PopularMoviesApplication.getEventBus().unregister(this);
        super.onCleared();
    }


}
