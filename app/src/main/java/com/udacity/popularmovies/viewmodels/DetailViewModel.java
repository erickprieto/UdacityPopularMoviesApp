package com.udacity.popularmovies.viewmodels;


import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.udacity.popularmovies.models.Movie;


/**
 * Used to hold {@link Movie} from {@link com.udacity.popularmovies.activities.DetailActivity}.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class DetailViewModel extends ViewModel {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = DetailViewModel.class.getSimpleName();

    /**
     * Hold Details of Movie to show.
     */
    private Movie movieDetails;

    /**
     * Default constructor.
     */
    public DetailViewModel() {
        super();
    }

    public Movie getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(Movie movieDetails) {
        this.movieDetails = movieDetails;
    }

    @Override
    protected void onCleared() {
        Log.v(TAG, "onCleared()");
        super.onCleared();
    }
}
