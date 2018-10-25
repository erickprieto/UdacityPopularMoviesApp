package com.udacity.popularmovies.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.repositories.MovieRepository;

import java.util.List;



/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class MainViewModel extends ViewModel {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = MainViewModel.class.getSimpleName();

    public MovieRepository movieRepo;

    public MainViewModel() {
        super();
        this.movieRepo = new MovieRepository();
    }

    public List<Movie> getMovies() {
        return movieRepo.getPopularMovies().getValue();
    }

    public void setMovies(List<Movie> movies) {
        this.movieRepo.updatePopularMovies(movies);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.v(TAG, "onCleared");
    }

}
