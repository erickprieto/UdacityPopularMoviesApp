package com.udacity.popularmovies.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.adapters.MoviesAdapter;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.models.MovieServiceSortBy;
import com.udacity.popularmovies.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

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
     * Identifier to serialize {@link MainViewModel#getMovies()}
     */
    public static final String ID_SERIAL_MOVIES_LIST = "moviesList";


    private MainViewModel mainViewModel;

    /**
     * Adapter to fill {@link RecyclerView} with {@link Movie}.
     */
    private MoviesAdapter adapter;

    //To assign Views
    private RecyclerView getMoviesRecyclerView() {
        return (RecyclerView) findViewById(R.id.mainActivity_movieRecyclerView);
    }
    private SwipeRefreshLayout getSwipeRefreshLayout() {
        return (SwipeRefreshLayout) findViewById(R.id.mainActivity_swipeRefreshtLayout);
    }

    public MoviesAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MoviesAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        this.mainViewModel.movieRepo.getPopularMovies().observe(this, new Observer<List<Movie>>() {

            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter.putMovies(movies);
            }
        });


        getSwipeRefreshLayout().setRefreshing(true);
        getSwipeRefreshLayout().setColorSchemeResources(R.color.colorPrimary);
        getSwipeRefreshLayout().setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        getSwipeRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.movieRepo.downloadPopularMovieList();
                getSwipeRefreshLayout().setRefreshing(false);
            }
        });



        this.mainViewModel.movieRepo.downloadPopularMovieList();


        getMoviesRecyclerView().setLayoutManager(getGridLayoutManager());
        assignMoviesViews();
        getSwipeRefreshLayout().setRefreshing(false);
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
                this.mainViewModel.movieRepo.downloadPopularMovieList();
                return true;
            case R.id.menuitem_sortby_rated:
                this.mainViewModel.movieRepo.downloadTopRatedMovieList();
                return true;
            case R.id.menuitem_favorites:
                //TODO
                this.mainViewModel.movieRepo.downloadDiscoverMovieList(MovieServiceSortBy.VOTE_AVERAGE_ASC);
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
    protected void onPause() {
        super.onPause();
    }

    /**
     * Assign to {@link MainActivity#getMoviesRecyclerView()} an Adapter for {@link Movie}.
     */
    private void assignMoviesViews() {

        adapter = new MoviesAdapter(this.mainViewModel.getMovies());
        getMoviesRecyclerView().setAdapter(adapter);
    }

    /**
     * Launch {@link DetailActivity} with extra data contained {@link Movie} details.
     * @param details <code>MovieTO</code> to send data into <code>Intent</code>
     */
    public void startDetailActivity(Movie details) {
        Log.v(TAG, "MovieTO sent: " + details.toString());
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ID_EXTRA_MOVIE_DETAIL, details);
        this.startActivity(intent);
    }

}
