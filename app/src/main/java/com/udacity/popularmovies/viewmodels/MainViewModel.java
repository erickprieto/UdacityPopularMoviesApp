package com.udacity.popularmovies.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.udacity.popularmovies.adapters.MoviesAdapter;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.models.MovieServiceLanguage;
import com.udacity.popularmovies.models.MovieServiceReleaseYear;
import com.udacity.popularmovies.models.MovieServiceSortBy;
import com.udacity.popularmovies.net.contracts.TO.MovieTO;
import com.udacity.popularmovies.net.contracts.TO.PageResultMoviesTO;
import com.udacity.popularmovies.net.contracts.MovieServiceContract;
import com.udacity.popularmovies.utils.ProxyHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Erick Prieto
 * @since 2018
 */
public class MainViewModel extends ViewModel implements Callback<PageResultMoviesTO> {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = MainViewModel.class.getSimpleName();

    /**
     * List of {@link Movie} from WebService or <code>Bundle</code>.
     */
    private List<Movie> movies = new ArrayList<Movie>();

    private MutableLiveData<List<Movie>> moviesMutable;
    public LiveData<List<Movie>> getMoviesMutable() {
        if (moviesMutable == null) {
            moviesMutable = new MutableLiveData<List<Movie>>();
            downloadDiscoverMovieList(MovieServiceSortBy.POPULARITY_DESC);
            moviesMutable.setValue(movies);
        }
        return moviesMutable;
    }

    /**
     * Adapter to fill {@link RecyclerView} with {@link Movie}.
     */
    private MoviesAdapter adapter;

    public MainViewModel() {
        super();
    }

    public MoviesAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MoviesAdapter adapter) {
        this.adapter = adapter;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
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
    protected void onCleared() {
        super.onCleared();
        Log.v(TAG, "onCleared");
    }

    @Override
    public void onResponse(Call<PageResultMoviesTO> call, Response<PageResultMoviesTO> response) {
        if(response.isSuccessful()) {
            PageResultMoviesTO pageWithMoviesList = response.body();
            Log.v(TAG, pageWithMoviesList.toString());

            this.movies = MovieTO.toListModel(pageWithMoviesList.getMovies());

            this.adapter.putMovies(this.movies);
            Log.v(TAG, this.movies.toString());

        } else {
            Log.e(TAG, response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<PageResultMoviesTO> call, Throwable t) {
        Log.e(TAG,call.toString() + t.getMessage());
    }
}
