package com.udacity.popularmovies.services;

import android.app.Service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;

import android.os.Binder;
import android.os.IBinder;

import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.PopularMoviesApplication;
import com.udacity.popularmovies.database.PopularMoviesDatabase;
import com.udacity.popularmovies.database.entities.FavoriteEntity;
import com.udacity.popularmovies.database.entities.MovieEntity;
import com.udacity.popularmovies.events.DownloadDiscoveryMovieListEvent;
import com.udacity.popularmovies.events.DownloadMovieListEvent;
import com.udacity.popularmovies.events.ImageDownloadedEvent;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.models.MovieServiceLanguage;
import com.udacity.popularmovies.models.MovieServiceReleaseYear;
import com.udacity.popularmovies.models.MovieServiceSortBy;
import com.udacity.popularmovies.net.contracts.MovieServiceContract;
import com.udacity.popularmovies.net.contracts.TO.MovieTO;
import com.udacity.popularmovies.net.contracts.TO.PageResultMoviesTO;
import com.udacity.popularmovies.utils.AppExecutors;
import com.udacity.popularmovies.utils.MemoryTarget;
import com.udacity.popularmovies.utils.ProxyHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopularMoviesDatabaseService extends Service {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = PopularMoviesDatabaseService.class.getSimpleName();

    private final IBinder binder = new PopularMoviesDatabaseServiceBinder();

    private Executor threadDbTaskExecutor = AppExecutors.getDiskIO();
    private Executor threadNetworkTaskExecutor = AppExecutors.getNetworkIO();

    private PopularMoviesDatabase db;
    private Context context = this;


    @Override
    public void onCreate() {
        super.onCreate();
        PopularMoviesApplication.getEventBus().register(this);
        Log.v(TAG, "onCreate ===========================================================");
        open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PopularMoviesApplication.getEventBus().unregister(this);
        Log.v(TAG, "onDestroy ===========================================================");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "Starting Service connection");

        return Service.START_STICKY;
    }

    private void open() {
        threadDbTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {

                db = Room
                        .databaseBuilder(context
                                , PopularMoviesDatabase.class
                                , PopularMoviesDatabase.NAME_POPULAR_MOVIES_DATABASE)
                        .build();

                Object a = db.movieDao().getAll();

                Log.v(TAG, a != null ? a.toString() : "NULL ================================");

            }
        });
    }

    public void imageDownload(int movieId, String url, Context ctx){
        Picasso.with(ctx)
                .load(ProxyHelper.buildCompletePosterUrl(url))
                .error(android.support.v7.appcompat.R.drawable.notification_bg_low_pressed)
                .into(new MemoryTarget(movieId, url, ctx));
    }

    @Subscribe
    public void imageSave(ImageDownloadedEvent event) {
        Log.v(TAG, "" + event.getImageId() + event.getUrl() + event.getPoster().toString());
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
        call.enqueue(new Callback<PageResultMoviesTO>() {
            @Override
            public void onResponse(Call<PageResultMoviesTO> call, Response<PageResultMoviesTO> response) {

                if(response.isSuccessful()) {
                    PageResultMoviesTO pageWithMoviesList = response.body();
                    Log.v(TAG, pageWithMoviesList.toString());
                    PopularMoviesApplication.getEventBus().post(
                            new DownloadDiscoveryMovieListEvent(
                                    MovieTO.toListModel(pageWithMoviesList.getMovies())));
                } else {
                    Log.e(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PageResultMoviesTO> call, Throwable t) {
                Log.e(TAG,call.toString() + t.getMessage());
            }
        });
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
        call.enqueue(new Callback<PageResultMoviesTO>() {
            @Override
            public void onResponse(Call<PageResultMoviesTO> call, Response<PageResultMoviesTO> response) {

                if(response.isSuccessful()) {
                    PageResultMoviesTO pageWithMoviesList = response.body();
                    Log.v(TAG, pageWithMoviesList.toString());
                    PopularMoviesApplication.getEventBus().post(
                            new DownloadMovieListEvent(
                                    MovieTO.toListModel(pageWithMoviesList.getMovies()),true));
                } else {
                    Log.e(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PageResultMoviesTO> call, Throwable t) {
                Log.e(TAG,call.toString() + t.getMessage());
            }
        });
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
        call.enqueue(new Callback<PageResultMoviesTO>() {
            @Override
            public void onResponse(Call<PageResultMoviesTO> call, Response<PageResultMoviesTO> response) {

                if(response.isSuccessful()) {
                    PageResultMoviesTO pageWithMoviesList = response.body();
                    Log.v(TAG, pageWithMoviesList.toString());
                    PopularMoviesApplication.getEventBus().post(
                            new DownloadMovieListEvent(
                                    MovieTO.toListModel(pageWithMoviesList.getMovies()),false));
                } else {
                    Log.e(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PageResultMoviesTO> call, Throwable t) {
                Log.e(TAG,call.toString() + t.getMessage());
            }
        });
    }

    public void listFavoritesMovies() {
        final LiveData<List<Movie>> movies = new MutableLiveData<List<Movie>>();
        threadDbTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LiveData<List<FavoriteEntity>> favoritos = db.favoriteDao().getAll();
                if(favoritos.getValue() == null) { return;}
                int[] ids = new int[favoritos.getValue().size()];
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = favoritos.getValue().get(i).getId();
                }
                ((MutableLiveData<List<Movie>>) movies)
                        .postValue(MovieEntity.toListModel(db.movieDao().loadAllByIds(ids).getValue()));

            }
        });
    }


    @Subscribe
    public void insertDownloadedMovies(final DownloadMovieListEvent event) {
        Log.v(TAG, "Service evento recibido ==============================================");
        final MovieEntity[] entities = new MovieEntity[event.getModelos().size()];
        threadDbTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {

                for(int i = 0; i < entities.length; i++) {
                    entities[i] = MovieEntity.fromModel(event.getModelos().get(i), event.isPopularMovie());
                }
                db.movieDao().insertAll(entities);

                Log.v(TAG, db.movieDao().getAll().toString() + "=======================================================");

            }
        });
    }

    public void isFavorite(final int idMovie) {
        threadDbTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LiveData<FavoriteEntity> result = db.favoriteDao().isFavorite(idMovie);
                Log.v(TAG, db.favoriteDao().getAll().toString() + "=======================================================");

            }
        });
    }

    public void setFavorite(final int idMovie) {
        threadDbTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                FavoriteEntity fav = new FavoriteEntity(idMovie);
                db.favoriteDao().insert(fav);
                Log.v(TAG, db.favoriteDao().getAll().toString() + "=======================================================");

            }
        });
    }

    public void unsetFavorite(final int idMovie) {
        threadDbTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                FavoriteEntity fav = new FavoriteEntity(idMovie);
                db.favoriteDao().delete(fav);
                Log.v(TAG, db.favoriteDao().getAll().toString() + "=======================================================");

            }
        });
    }








    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class PopularMoviesDatabaseServiceBinder extends Binder {

        public PopularMoviesDatabaseService getService() {
            return PopularMoviesDatabaseService.this;
        }
    }
}
