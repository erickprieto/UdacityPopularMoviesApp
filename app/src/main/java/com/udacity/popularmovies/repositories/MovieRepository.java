package com.udacity.popularmovies.repositories;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.models.MovieServiceLanguage;
import com.udacity.popularmovies.models.MovieServiceReleaseYear;
import com.udacity.popularmovies.models.MovieServiceSortBy;
import com.udacity.popularmovies.net.contracts.MovieServiceContract;
import com.udacity.popularmovies.net.contracts.TO.MovieTO;
import com.udacity.popularmovies.net.contracts.TO.PageResultMoviesTO;
import com.udacity.popularmovies.utils.ProxyHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository implements IMovieRepository, Callback<PageResultMoviesTO> {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = MovieRepository.class.getSimpleName();

    /**
     * List of {@link Movie} from WebService or <code>Bundle</code>.
     */
    private MutableLiveData<List<Movie>> moviesMutable;



    public MovieRepository() {
        this.moviesMutable = new MutableLiveData<List<Movie>>();
        moviesMutable.setValue(new ArrayList<Movie>());
    }

    public MovieRepository(MutableLiveData<List<Movie>> moviesMutable) {
        this.moviesMutable = moviesMutable;
    }


    /**
     * Download from webservice list of {@link Movie}.
     * Enqueue Asynchronous WebService to call.
     * @param sortByOption {@link MovieServiceSortBy} enum to choose the way to sort list from
     *                                               webservice.
     */
    @Deprecated
    public void downloadDiscoverMovieList(MovieServiceSortBy sortByOption) {
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResultMoviesTO> call = api.getDiscoverMovies(
                ProxyHelper.WEB_SERVICES_LICENSE
                , MovieServiceLanguage.ENGLISH_US.getValue()
                , sortByOption.getValue()
                , false
                , false
                , MovieServiceReleaseYear.YEAR_2018.getValue()
                , 1);
        Log.v(TAG, call.request().url().toString());
        call.enqueue(this);
    }

    /**
     * Download from webservice list of Popular {@link Movie}.
     * Enqueue Asynchronous WebService to call.
     */
    public void downloadPopularMovieList() {
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResultMoviesTO> call = api.getPopularMovies(
                ProxyHelper.WEB_SERVICES_LICENSE
                , MovieServiceLanguage.ENGLISH_US.getValue()
                , 1);
        Log.v(TAG, call.request().url().toString());
        call.enqueue(this);
    }

    /**
     * Download from webservice list of Poupular {@link Movie}.
     * Enqueue Asynchronous WebService to call.
     */
    public void downloadTopRatedMovieList() {
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResultMoviesTO> call = api.getTopRatedMovies(
                ProxyHelper.WEB_SERVICES_LICENSE
                , MovieServiceLanguage.ENGLISH_US.getValue()
                , 1);
        Log.v(TAG, call.request().url().toString());
        call.enqueue(this);
    }



    @Override
    public LiveData<List<Movie>> getPopularMovies() {
        Log.v(TAG, "getPopularMovies: ======");
        if (moviesMutable == null) {
            Log.v(TAG, "getPopularMovies: NULL ======");
            moviesMutable = new MutableLiveData<List<Movie>>();
            moviesMutable.setValue(new ArrayList<Movie>());
            //downloadDiscoverMovieList(MovieServiceSortBy.POPULARITY_DESC);
            //moviesMutable.setValue(movies);
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

    @Override
    public void onResponse(Call<PageResultMoviesTO> call, Response<PageResultMoviesTO> response) {
        if(response.isSuccessful()) {
            PageResultMoviesTO pageWithMoviesList = response.body();
            Log.v(TAG, pageWithMoviesList.toString());

            updatePopularMovies(MovieTO.toListModel(pageWithMoviesList.getMovies()));

            //this.adapter.putMovies(this.movies);
            Log.v(TAG, this.moviesMutable.toString());

        } else {
            Log.e(TAG, response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<PageResultMoviesTO> call, Throwable t) {
        Log.e(TAG,call.toString() + t.getMessage());
    }
}
