package com.udacity.popularmovies.presenters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.udacity.popularmovies.activities.MainActivity;
import com.udacity.popularmovies.adapters.MoviesAdapter;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.models.MovieServiceLanguage;
import com.udacity.popularmovies.models.MovieServiceReleaseYear;
import com.udacity.popularmovies.models.MovieServiceSortBy;
import com.udacity.popularmovies.models.PageResultMovies;
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
public class MainPresenter extends Presenter implements Callback<PageResultMovies> {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = MainPresenter.class.getSimpleName();

    /**
     * List of {@link Movie} from WebService or <code>Bundle</code>.
     */
    private List<Movie> movies = new ArrayList<Movie>();

    /**
     * Adapter to fill {@link RecyclerView} with {@link Movie}.
     */
    private MoviesAdapter adapter;

    public MainPresenter() {
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
     * Enqueue Asyncromus WebService to call.
     * @param sortByOption {@link MovieServiceSortBy} enum to choose the way to sort list from
     *                                               webservice.
     */
    @Deprecated
    public void downloadDiscoverMovieList(MovieServiceSortBy sortByOption) {
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResultMovies> call = api.getDiscoverMovies(
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
     * Enqueue Asyncromus WebService to call.
     */
    public void downloadPopularMovieList() {
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResultMovies> call = api.getPopularMovies(
                ProxyHelper.WEB_SERVICES_LICENSE
                , MovieServiceLanguage.ENGLISH_US.getValue()
                , 1);
        Log.v(TAG, call.request().url().toString());
        call.enqueue(this);
    }

    /**
     * Download from webservice list of Poupular {@link Movie}.
     * Enqueue Asyncromus WebService to call.
     */
    public void downloadTopRatedMovieList() {
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResultMovies> call = api.getTopRatedMovies(
                ProxyHelper.WEB_SERVICES_LICENSE
                , MovieServiceLanguage.ENGLISH_US.getValue()
                , 1);
        Log.v(TAG, call.request().url().toString());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<PageResultMovies> call, Response<PageResultMovies> response) {
        if(response.isSuccessful()) {
            PageResultMovies moviesList = response.body();
            this.movies = moviesList.getMovies();

            this.adapter.putMovies(this.movies);

            Log.v(TAG, moviesList.toString());
        } else {
            Log.e(TAG, response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<PageResultMovies> call, Throwable t) {
        Log.e(TAG,call.toString() + t.getMessage());
    }
}
