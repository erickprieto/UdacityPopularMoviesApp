package com.udacity.popularmovies.activities;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;
import com.udacity.popularmovies.PopularMoviesApplication;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.adapters.MoviesAdapter;
import com.udacity.popularmovies.events.FetchNewMovieListErrorEvent;
import com.udacity.popularmovies.events.FetchededNewMovieListEvent;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.models.Review;
import com.udacity.popularmovies.models.Video;
import com.udacity.popularmovies.services.PopularMoviesRepositoryService;
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
     * Identifier to serialize {@link MainViewModel#movies}
     */
    public static final String ID_SERIAL_MOVIES_LIST = "moviesList";


    private MainViewModel mainViewModel;

    /**
     * Adapter to fill {@link RecyclerView} with {@link Movie}.
     */
    private MoviesAdapter adapter;

    /**
     * Index of current Screen.
     * 1 Popular
     * 2 Top Rated
     * 3 Favorites
     */
    private byte screen = 1;

    /**
     * Intent to bind with {@link PopularMoviesRepositoryService}.
     */
    private Intent intentService;

    /**
     * Reference to {@link PopularMoviesRepositoryService}.
     */
    private PopularMoviesRepositoryService pmService;

    /**
     * indicate if {@link PopularMoviesRepositoryService} is connected.
     */
    private boolean connectedService;

    /**
     * Handle the connection with {@link PopularMoviesRepositoryService}.
     */
    private PopularMoviesServiceConnection conn = new PopularMoviesServiceConnection();



    //To assign Views
    private RecyclerView getMoviesRecyclerView() {
        return (RecyclerView) findViewById(R.id.mainActivity_movieRecyclerView);
    }
    private SwipeRefreshLayout getSwipeRefreshLayout() {
        return (SwipeRefreshLayout) findViewById(R.id.mainActivity_swipeRefreshtLayout);
    }


    /**
     * Obtain Adapter
     * @return <c>MoviesAdapter</c>
     */
    public MoviesAdapter getAdapter() {
        return adapter;
    }

    /**
     * EStablish Adapter to show {@link Movie}
     * @param adapter adapter will be establish.
     */
    public void setAdapter(MoviesAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentService = new Intent(this, PopularMoviesRepositoryService.class);

        if(!connectedService) {
            bindService(intentService, conn, BIND_AUTO_CREATE);
        }

        setContentView(R.layout.activity_main);

        this.mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        tofillUI();
    }

    /**
     * Fill UI of this <c>Activity</c>.
     */
    private void tofillUI() {
        getSwipeRefreshLayout().setRefreshing(true);
        getSwipeRefreshLayout().setColorSchemeResources(R.color.colorPrimary);
        getSwipeRefreshLayout().setProgressBackgroundColorSchemeResource(R.color.colorAccent);

        getSwipeRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final String TAG_M = "onRefresh() ";
                Log.v(TAG, TAG_M);
                if (screen == 1) {
                    pmService.fetchPopularMovieList();
                } else if (screen == 2) {
                    pmService.fetchTopRatedMovieList();
                } else if (screen == 3) {
                    adapter.putMovies(mainViewModel.getMoviesFavoritesMutable().getValue());
                    getSwipeRefreshLayout().setRefreshing(false);
                }
            }
        });

        getMoviesRecyclerView().setLayoutManager(getGridLayoutManager());
        assignAdapterViews();
    }

    /**
     * Define the columns in the {@link android.support.v7.widget.GridLayout}.
     * 2 Columns if {@link Configuration#ORIENTATION_PORTRAIT} or 3 columns if
     * {@link Configuration#ORIENTATION_PORTRAIT}.
     * @return <c>GridLayoutManager</c> configurated for vertical or horizontal position.
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
                getSwipeRefreshLayout().setRefreshing(true);
                adapter.putMovies(mainViewModel.getMoviesPopularMutable().getValue());
                getMoviesRecyclerView().scrollToPosition(0);
                getSwipeRefreshLayout().setRefreshing(false);
                this.screen = 1;
                return true;
            case R.id.menuitem_sortby_rated:
                getSwipeRefreshLayout().setRefreshing(true);
                adapter.putMovies(mainViewModel.getMoviesTopRatedMutable().getValue());
                getMoviesRecyclerView().scrollToPosition(0);
                getSwipeRefreshLayout().setRefreshing(false);
                this.screen = 2;
                return true;
            case R.id.menuitem_favorites:
                getSwipeRefreshLayout().setRefreshing(true);
                adapter.putMovies(mainViewModel.getMoviesFavoritesMutable().getValue());
                getMoviesRecyclerView().scrollToPosition(0);
                getSwipeRefreshLayout().setRefreshing(false);
                this.screen = 3;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        PopularMoviesApplication.getEventBus().register(this);
        if (this.screen == 3) {
            adapter.putMovies(mainViewModel.getMoviesFavoritesMutable().getValue());
        }
    }

    @Override
    protected void onPause() {
        try{
            if (connectedService && pmService != null) {
                unbindService(conn);
            }
            } catch (IllegalArgumentException iae) {
                Log.e(TAG, iae.getMessage());
        }
        PopularMoviesApplication.getEventBus().register(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * Assign to {@link MainActivity#getMoviesRecyclerView()} an Adapter for {@link Movie}.
     */
    private void assignAdapterViews() {
        final String TAG_M = "assignAdapterViews() ";
        Log.v(TAG, TAG_M);
        adapter = new MoviesAdapter(new ArrayList<Movie>(), MainActivity.this);
        getMoviesRecyclerView().setAdapter(adapter);
    }

    /**
     * Launch {@link DetailActivity} with extra data contained {@link Movie} details.
     * @param details <code>MovieTO</code> to send data into <code>Intent</code>
     */
    public void startDetailActivity(Movie details) {
        final String TAG_M = "startDetailActivity() ";
        Log.v(TAG, TAG_M + "Movie sent: " + details.toString());

        if(details.getVideos() == null) {
            details.setVideos(new ArrayList<Video>()); }
        if (details.getReviews() == null) {
            details.setReviews(new ArrayList<Review>()); }

        pmService.downloadReviewList(details.getId());
        pmService.downloadVideoList(details.getId());

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ID_EXTRA_MOVIE_DETAIL, details);
        this.startActivity(intent);
    }

    @Subscribe
    public void downloadedMovieList(FetchededNewMovieListEvent event) {
        final String TAG_M = "downloadedMovieList() ";
        Log.v(TAG, TAG_M + "=======================================================");
        getSwipeRefreshLayout().setRefreshing(false);
    }

    /**
     * Receive this Error event if an error happened when fetch new data from
     * webervices. Notify to {@link SwipeRefreshLayout} that job is done.
     * @param event empty object.
     */
    @Subscribe
    public void fetchError(FetchNewMovieListErrorEvent event) {
        final String TAG_M = "fetchError() ";
        Log.v(TAG, TAG_M + "=======================================================");
        getSwipeRefreshLayout().setRefreshing(false);
    }


    /**
     * Handle connection to {@link PopularMoviesRepositoryService}.
     * Establish listeners to database objects holds on {@link MainViewModel}.
     */
    public class PopularMoviesServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final String TAG_M = "onServiceConnected() ";
            Log.v(TAG, TAG_M + "++++++++++++++++++++++++++++++++++++++++++++++++");
            pmService= ((PopularMoviesRepositoryService.PopularMoviesRepositoryServiceBinder) service).getService();
            connectedService = true;

            mainViewModel.setMoviesPopularMutable((MutableLiveData<List<Movie>>) pmService.getPopularMovies());
            mainViewModel.setMoviesTopRatedMutable((MutableLiveData<List<Movie>>) pmService.getRatedMovies());
            mainViewModel.setMoviesFavoritesMutable((MutableLiveData<List<Movie>>) pmService.getFavoriteMovies());

            adapter.putMovies(mainViewModel.getMoviesPopularMutable().getValue());
            getSwipeRefreshLayout().setRefreshing(false);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            final String TAG_M = "onServiceDisconnected() ";
            Log.v(TAG, TAG_M + "++++++++++++++++++++++++++++++++++++++++++++++");
            pmService = null;
            connectedService = false;
        }
    }

}
