package com.udacity.popularmovies.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.adapters.MoviesAdapter;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.models.MovieServiceLanguage;
import com.udacity.popularmovies.models.MovieServiceReleaseYear;
import com.udacity.popularmovies.models.MovieServiceSortBy;
import com.udacity.popularmovies.models.PageResult;
import com.udacity.popularmovies.net.contracts.MovieServiceContract;
import com.udacity.popularmovies.utils.ProxyHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Show Posters of main Popular Movies from <code>https://api.themoviedb.org/3/</code>.
 * Activity gives an option to sort by popularity and votes.
 * click on a movies launch a {@link DetailActivity} with details of the movie.
 * @author Erick Prieto
 * @since 2018
 */
public class MainActivity extends AppCompatActivity implements Callback<PageResult> {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Identifier to serialize {@link MainActivity#movies}
     */
    public static final String ID_SERIAL_MOVIES_LIST = "moviesList";

    /**
     * List of {@link Movie} from WebService or <code>Bundle</code>.
     */
    private List<Movie> movies = new ArrayList<Movie>();

    /**
     * Adapter to fill {@link RecyclerView} with {@link Movie}.
     */
    private MoviesAdapter adapter;

    //To assign Views
    private RecyclerView getMoviesRecyclerView() {
        return (RecyclerView) findViewById(R.id.movie_RecyclerView); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            this.movies = savedInstanceState.getParcelableArrayList(ID_SERIAL_MOVIES_LIST);
        }

        if(this.movies.size() == 0) {
            //downloadDiscoverMovieList(MovieServiceSortBy.POPULARITY_DESC);
            downloadPopularMovieList();
        } else {
            assignMoviesViews();
        }

        getMoviesRecyclerView().setLayoutManager(getGridLayoutManager());
        assignMoviesViews();
    }

    /**
     * Define the columns in the {@link android.support.v7.widget.GridLayout}.
     * 2 Columns if {@link Configuration#ORIENTATION_PORTRAIT} or 3 columns if
     * {@link Configuration#ORIENTATION_PORTRAIT}.
     * @return
     */
    @NonNull
    private GridLayoutManager getGridLayoutManager() {

        GridLayoutManager grid;
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                grid = new GridLayoutManager(this, 3);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                grid = new GridLayoutManager(this, 2);
                break;
            default:
                grid = new GridLayoutManager(this, 2);
        }
        return grid;
    }

    /**
     * Download from webservice list of {@link Movie}.
     * Enqueue Asyncromus WebService to call.
     * @param sortByOption {@link MovieServiceSortBy} enum to choose the way to sort list from
     *                                               webservice.
     */
    @Deprecated
    private void downloadDiscoverMovieList(MovieServiceSortBy sortByOption) {
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResult> call = api.getDiscoverMovies(
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
    private void downloadPopularMovieList() {
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResult> call = api.getPopularMovies(
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
    private void downloadTopRatedMovieList() {
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResult> call = api.getTopRatedMovies(
                ProxyHelper.WEB_SERVICES_LICENSE
                , MovieServiceLanguage.ENGLISH_US.getValue()
                , 1);
        Log.v(TAG, call.request().url().toString());
        call.enqueue(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_sortby_popular:
                downloadPopularMovieList();
                return true;
            case R.id.menuitem_sortby_rated:
                downloadTopRatedMovieList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.movies != null) {
            outState.putParcelableArrayList(ID_SERIAL_MOVIES_LIST, new ArrayList<Movie>(this.movies));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Assign to {@link MainActivity#getMoviesRecyclerView()} an Adapter for {@link Movie}.
     */
    private void assignMoviesViews() {

        this.adapter = new MoviesAdapter(this.movies);
        getMoviesRecyclerView().setAdapter(this.adapter);
    }

    /**
     * Launch {@link DetailActivity} with extra data contained {@link Movie} details.
     * @param deatils <code>Movie</code> to send data into <code>Intent</code>
     */
    public void startDetailActivity(Movie deatils) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ID_EXTRA_MOVIE_DETAIL, deatils);
        this.startActivity(intent);
    }

    @Override
    public void onResponse(Call<PageResult> call, Response<PageResult> response) {
        if(response.isSuccessful()) {
            PageResult moviesList = response.body();
            this.movies = moviesList.getMovies();

            this.adapter.putMovies(this.movies);

            Log.v(TAG, moviesList.toString());
        } else {
            Log.e(TAG, response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<PageResult> call, Throwable t) {
        Log.e(TAG,call.toString() + t.getMessage());
    }

}
