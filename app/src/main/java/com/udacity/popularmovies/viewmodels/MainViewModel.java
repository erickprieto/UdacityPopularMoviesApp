package com.udacity.popularmovies.viewmodels;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;


import com.udacity.popularmovies.PopularMoviesApplication;
import com.udacity.popularmovies.models.Movie;

import java.util.ArrayList;
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

    /**
     * List of {@link Movie} from WebService or <code>Bundle</code>.
     */
    public MutableLiveData<List<Movie>> moviesPopularMutable;
    public MutableLiveData<List<Movie>> moviesTopRatedMutable;
    public MutableLiveData<List<Movie>> moviesFavoritesMutable;


    public List<Movie> movies = new ArrayList<>();


    public MainViewModel() {
        super();
        PopularMoviesApplication.getEventBus().register(this);

    }

    @Override
    protected void onCleared() {
        Log.v(TAG, "onCleared()");
        PopularMoviesApplication.getEventBus().unregister(this);
        super.onCleared();

    }


}
