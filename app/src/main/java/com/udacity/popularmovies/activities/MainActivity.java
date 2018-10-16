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
import com.udacity.popularmovies.models.PageResultMovies;
import com.udacity.popularmovies.net.contracts.MovieServiceContract;
import com.udacity.popularmovies.presenters.MainPresenter;
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
 *
 * @author Erick Prieto
 * @since 2018
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Identifier to serialize {@link MainPresenter#movies}
     */
    public static final String ID_SERIAL_MOVIES_LIST = "moviesList";

    private MainPresenter presenter;

    //To assign Views
    private RecyclerView getMoviesRecyclerView() {
        return (RecyclerView) findViewById(R.id.movie_RecyclerView); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.presenter = new MainPresenter();

        if (savedInstanceState != null) {
            this.presenter.setMovies(
                    new ArrayList(savedInstanceState.getParcelableArrayList(ID_SERIAL_MOVIES_LIST))
            );
        }

        if(this.presenter.getMovies().size() == 0) {
            this.presenter.downloadPopularMovieList();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_sortby_popular:
                this.presenter.downloadPopularMovieList();
                return true;
            case R.id.menuitem_sortby_rated:
                this.presenter.downloadTopRatedMovieList();
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
        if (this.presenter.getMovies() != null) {
            outState.putParcelableArrayList(ID_SERIAL_MOVIES_LIST, new ArrayList<Movie>(this.presenter.getMovies()));
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

        this.presenter.setAdapter(new MoviesAdapter(this.presenter.getMovies()));
        getMoviesRecyclerView().setAdapter(this.presenter.getAdapter());
    }

    /**
     * Launch {@link DetailActivity} with extra data contained {@link Movie} details.
     * @param details <code>Movie</code> to send data into <code>Intent</code>
     */
    public void startDetailActivity(Movie details) {
        Log.v(TAG, "Movie sent: " + details.toString());
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ID_EXTRA_MOVIE_DETAIL, details);
        this.startActivity(intent);
    }

}
