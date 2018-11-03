package com.udacity.popularmovies.services;

import android.app.Service;

import android.arch.lifecycle.LifecycleService;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.IBinder;

import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.udacity.popularmovies.PopularMoviesApplication;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.database.PopularMoviesDatabase;
import com.udacity.popularmovies.database.entities.FavoriteEntity;
import com.udacity.popularmovies.database.entities.MovieEntity;
import com.udacity.popularmovies.events.DownloadedMovieListEvent;
import com.udacity.popularmovies.events.DownloadedReviewListEvent;
import com.udacity.popularmovies.events.DownloadedVideoListEvent;
import com.udacity.popularmovies.events.EstablishedMovieFavoriteEvent;
import com.udacity.popularmovies.events.FetchNewMovieListErrorEvent;
import com.udacity.popularmovies.events.FetchededNewMovieListEvent;
import com.udacity.popularmovies.events.SetFavoriteMovieEvent;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.models.MovieServiceLanguage;
import com.udacity.popularmovies.models.MovieServiceReleaseYear;
import com.udacity.popularmovies.models.MovieServiceSortBy;
import com.udacity.popularmovies.models.Review;
import com.udacity.popularmovies.models.Video;
import com.udacity.popularmovies.net.contracts.MovieServiceContract;
import com.udacity.popularmovies.net.contracts.ReviewServiceContract;
import com.udacity.popularmovies.net.contracts.TO.MovieTO;
import com.udacity.popularmovies.net.contracts.TO.PageResultMoviesTO;
import com.udacity.popularmovies.net.contracts.TO.PageResultReviewsTO;
import com.udacity.popularmovies.net.contracts.TO.PageResultVideosTO;
import com.udacity.popularmovies.net.contracts.TO.ReviewTO;
import com.udacity.popularmovies.net.contracts.TO.VideoTO;
import com.udacity.popularmovies.net.contracts.VideoServiceContract;
import com.udacity.popularmovies.repositories.IMovieRepository;
import com.udacity.popularmovies.utils.AppExecutors;
import com.udacity.popularmovies.utils.ProxyHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class PopularMoviesRepositoryService extends LifecycleService implements IMovieRepository {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = PopularMoviesRepositoryService.class.getSimpleName();

    private static final int PAGE_NUMBER = 1;

    private final IBinder binder = new PopularMoviesRepositoryServiceBinder();

    private Executor threadDbTaskExecutor = AppExecutors.getDiskIO();
    private Executor threadNetworkTaskExecutor = AppExecutors.getNetworkIO();

    private PopularMoviesDatabase db;
    private Context context = this;

    private MovieEntity[] movieEntitiesArray;

    /**
     * List of {@link Movie} from WebService or <code>Bundle</code>.
     */
    private LiveData<List<Movie>> moviesPopularMutable;
    private LiveData<List<Movie>> moviesTopRatedMutable;
    private LiveData<List<Movie>> moviesFavoriteMutable;

    public PopularMoviesRepositoryService() {
        this.moviesPopularMutable  = new MutableLiveData<List<Movie>>();
        this.moviesTopRatedMutable = new MutableLiveData<List<Movie>>();
        this.moviesFavoriteMutable = new MutableLiveData<List<Movie>>();
        ((MutableLiveData<List<Movie>>) this.moviesPopularMutable).setValue(new ArrayList<Movie>());
        ((MutableLiveData<List<Movie>>) this.moviesTopRatedMutable).setValue(new ArrayList<Movie>());
        ((MutableLiveData<List<Movie>>) this.moviesFavoriteMutable).setValue(new ArrayList<Movie>());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final String TAG_M = "onStartCommand() ";
        Log.v(TAG, TAG_M);
        PopularMoviesApplication.getEventBus().register(this);
        openPopularMoviesDatabase();

        this.moviesPopularMutable.observe((PopularMoviesRepositoryService) context, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.v(TAG, "onChanged M Dao Popular Movies");
            }
        });

        this.moviesTopRatedMutable.observe((PopularMoviesRepositoryService) context, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.v(TAG, "onChanged M Dao Top Rated Movies");
            }
        });

        this.moviesFavoriteMutable.observe((PopularMoviesRepositoryService) context, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.v(TAG, "onChanged M Dao All Movies");
            }
        });


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        final String TAG_M = "onStartCommand() ";
        Log.v(TAG, TAG_M);

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        final String TAG_M = "onDestroy() ";
        PopularMoviesApplication.getEventBus().unregister(this);
        db.movieDao().getPopularMovies().removeObservers(this);
        db.movieDao().getTopRatedMovies().removeObservers(this);
        db.movieDao().getAll().removeObservers(this);
        Log.v(TAG, TAG_M);
        super.onDestroy();
    }




    private void openPopularMoviesDatabase() {


        threadDbTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {

                db = PopularMoviesDatabase.getDatabase(context);

                 db.movieDao().getPopularMovies().observe(((PopularMoviesRepositoryService)context)
                        , new Observer<List<MovieEntity>>() {

                            @Override
                            public void onChanged(@Nullable List<MovieEntity> entities) {
                                Log.v(TAG, "onChanged Dao Popular Movies");
                                ((MutableLiveData) moviesPopularMutable).setValue(MovieEntity.toListModel(entities));
                            }
                });

                db.movieDao().getTopRatedMovies().observe(((PopularMoviesRepositoryService)context)
                        , new Observer<List<MovieEntity>>() {
                            @Override
                            public void onChanged(@Nullable List<MovieEntity> entities) {
                                Log.v(TAG, "onChanged Dao Top Rated Movies");
                                ((MutableLiveData) moviesTopRatedMutable).setValue(MovieEntity.toListModel(entities));
                            }
                });

                db.movieDao().getFavoriteMovies().observe(((PopularMoviesRepositoryService)context)
                        , new Observer<List<MovieEntity>>() {
                            @Override
                            public void onChanged(@Nullable List<MovieEntity> entities) {
                                Log.v(TAG, "onChanged Dao Favorites Movies");
                                ((MutableLiveData) moviesFavoriteMutable).setValue(MovieEntity.toListModel(entities));
                            }
                        });
            }
        });

    }

    /**
     * Download from webservice list of {@link Movie}.
     * Enqueue Asynchronous WebService to call.
     * @param sortByOption {@link MovieServiceSortBy} enum to choose the way to sort list from
     *                                               webservice.
     */
    @Deprecated
    public void fetchDiscoverMovieList(MovieServiceSortBy sortByOption) {
        final String TAG_M = "fetchDiscoverMovieList() ";
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResultMoviesTO> call = api.getDiscoverMovies(
                ProxyHelper.WEB_SERVICES_LICENSE
                , MovieServiceLanguage.ENGLISH_US.getValue()
                , sortByOption.getValue()
                , false
                , false
                , MovieServiceReleaseYear.YEAR_2018.getValue()
                , PAGE_NUMBER);
        Log.v(TAG, TAG_M + call.request().url().toString());
        call.enqueue(new Callback<PageResultMoviesTO>() {
            @Override
            public void onResponse(Call<PageResultMoviesTO> call, Response<PageResultMoviesTO> response) {

                try {
                    if(response.isSuccessful()) {
                        PageResultMoviesTO pageWithMoviesList = response.body();
                        Log.v(TAG, TAG_M + pageWithMoviesList.toString());
                        processDownloadedMovies(MovieTO.toListModel(pageWithMoviesList.getMovies())
                                , false);
                    } else {
                        Log.e(TAG,TAG_M + response.errorBody().toString());
                    }
                }catch (Exception ex) {
                    Log.e(TAG,TAG_M + call.toString() + ex.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PageResultMoviesTO> call, Throwable t) {
                Log.e(TAG,TAG_M + call.toString() + t.getMessage());
                PopularMoviesApplication.getEventBus()
                        .post(new FetchNewMovieListErrorEvent());
            }
        });
    }

    /**
     * Download from webservice list of Popular {@link Movie}.
     * Enqueue Asynchronous WebService to call.
     */
    public void fetchPopularMovieList() {
        final String TAG_M = "fetchPopularMovieList() ";
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResultMoviesTO> call = api.getPopularMovies(
                ProxyHelper.WEB_SERVICES_LICENSE
                , MovieServiceLanguage.ENGLISH_US.getValue()
                , PAGE_NUMBER);
        Log.v(TAG,TAG_M + call.request().url().toString());
        call.enqueue(new Callback<PageResultMoviesTO>() {
            @Override
            public void onResponse(Call<PageResultMoviesTO> call, Response<PageResultMoviesTO> response) {

                if(response.isSuccessful()) {
                    PageResultMoviesTO pageWithMoviesList = response.body();
                    Log.v(TAG,TAG_M + pageWithMoviesList.toString());
                    processDownloadedMovies(MovieTO.toListModel(pageWithMoviesList.getMovies()),true);

                } else {
                    Log.e(TAG, TAG_M + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PageResultMoviesTO> call, Throwable t) {
                Log.e(TAG, TAG_M + call.toString() + t.getMessage());
                PopularMoviesApplication.getEventBus()
                        .post(new FetchNewMovieListErrorEvent());
            }
        });
    }

    /**
     * Download from webservice list of Popular {@link Movie}.
     * Enqueue Asynchronous WebService to call.
     */
    public void fetchTopRatedMovieList() {
        final String TAG_M = "downloadTopRatedMovieList() ";
        MovieServiceContract api = ProxyHelper.getProxy(MovieServiceContract.class);

        Call<PageResultMoviesTO> call = api.getTopRatedMovies(
                ProxyHelper.WEB_SERVICES_LICENSE
                , MovieServiceLanguage.ENGLISH_US.getValue()
                , PAGE_NUMBER);
        Log.v(TAG,TAG_M + call.request().url().toString());
        call.enqueue(new Callback<PageResultMoviesTO>() {
            @Override
            public void onResponse(Call<PageResultMoviesTO> call, Response<PageResultMoviesTO> response) {

                if(response.isSuccessful()) {
                    PageResultMoviesTO pageWithMoviesList = response.body();
                    Log.v(TAG,TAG_M + pageWithMoviesList.toString());

                    processDownloadedMovies(MovieTO.toListModel(pageWithMoviesList.getMovies()),false);

                } else {
                    Log.e(TAG,TAG_M + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PageResultMoviesTO> call, Throwable t) {
                Log.e(TAG,TAG_M + call.toString() + t.getMessage());
                PopularMoviesApplication.getEventBus()
                        .post(new FetchNewMovieListErrorEvent());
            }
        });
    }

    /**
     * Download from webservice list of VideoTO {@link VideoTO}.
     * Enqueue Asyncromus WebService to call.
     */
    public void downloadVideoList(int movieId) {
        final String TAG_M = "downloadVideoList() ";
        VideoServiceContract api = ProxyHelper.getProxy(VideoServiceContract.class);

        Call<PageResultVideosTO> call = api.getVideos(
                movieId
                , ProxyHelper.WEB_SERVICES_LICENSE
                , MovieServiceLanguage.ENGLISH_US.getValue()
        );
        Log.v(TAG, call.request().url().toString());
        call.enqueue(new Callback<PageResultVideosTO>() {
            @Override
            public void onResponse(Call<PageResultVideosTO> call, Response<PageResultVideosTO> response) {
                if(response.isSuccessful()) {
                    PageResultVideosTO page = response.body();
                    List<VideoTO> videos = page.getVideos();
                    List<Video> listaVideo = VideoTO.toListModel(videos);
                    Log.v(TAG,TAG_M + (listaVideo != null ? listaVideo.toString() : "null"));
                    PopularMoviesApplication.getEventBus().post(new DownloadedVideoListEvent(listaVideo));
                } else {
                    Log.e(TAG,TAG_M + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PageResultVideosTO> call, Throwable t) {
                Log.e(TAG,TAG_M + call.toString() + t.getMessage());
            }
        });
    }

    public void downloadReviewList(final int movieId) {
        final String TAG_M = "downloadReviewList() ";
        ReviewServiceContract api = ProxyHelper.getProxy(ReviewServiceContract.class);

        Call<PageResultReviewsTO> call = api.getReviews(
                movieId
                , ProxyHelper.WEB_SERVICES_LICENSE
                , MovieServiceLanguage.ENGLISH_US.getValue()
        );
        Log.v(TAG, call.request().url().toString());
        call.enqueue(new Callback<PageResultReviewsTO>() {
            @Override
            public void onResponse(Call<PageResultReviewsTO> call, Response<PageResultReviewsTO> response) {
                if(response.isSuccessful()) {
                    PageResultReviewsTO page = response.body();
                    List<ReviewTO> reviews = page.getReviews();
                    List<Review> listaReview = ReviewTO.toListModel(reviews);
                    Log.v(TAG,TAG_M + (listaReview != null ? listaReview.toString() : "null"));
                    PopularMoviesApplication.getEventBus().post(new DownloadedReviewListEvent(listaReview));
                } else {
                    Log.e(TAG, TAG_M + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PageResultReviewsTO> call, Throwable t) {
                Log.e(TAG,TAG_M + call.toString() + t.getMessage());
            }
        });
    }


    private void processDownloadedMovies(List<Movie> movies, boolean isPopularMovie) {
        final String TAG_M = "processDownloadedMovies() ";
        Log.v(TAG, TAG_M + "==============================================");
        final MovieEntity[] entities = new MovieEntity[movies.size()];
        final int[] ids = new int[entities.length];
        final String[] urls = new String[entities.length];
        for(int i = 0; i < entities.length; i++) {
            entities[i] = MovieEntity.fromModel(movies.get(i), isPopularMovie);
            Log.v(TAG, TAG_M + " =================== processed element: " + i + " = of = " + (entities.length-1));
            ids[i] = entities[i].getId();
            urls[i] = entities[i].getPosterFileName();
        }
        this.movieEntitiesArray = entities;
        processDownloadPosters(ids, urls);

    }

    private void processDownloadPosters(final int[] moviesId, String[] urls){
        final String TAG_M = "processDownloadPosters() ";
        try {
            for (int i = 0; i < moviesId.length; i++) {
                final int movieId = moviesId[i];
                final String url =  urls[i];
                final boolean lastPoster = (i == moviesId.length - 1);

                Picasso.with(context)
                        .load(ProxyHelper.buildCompletePosterUrl(url))
                        .error(R.drawable.ic_baseline_broken_image_24px)
                        .into(new Target() {

                            @Override
                            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                                Log.v(TAG, TAG_M + "onBitmapLoaded() "+ " movie: " + movieId + " url: " + url);

                                for (int j = 0; j < movieEntitiesArray.length; j++) {
                                    if (movieEntitiesArray[j].getId() == movieId) {
                                        movieEntitiesArray[j].setPoster(bitmap);
                                    }
                                }

                                if(lastPoster) {
                                    insertMoviesEntities();
                                }
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Log.e(TAG, TAG_M + "onBitmapFailed() " + " movie: " + movieId + " url: " + url);
                                if(lastPoster) {
                                    insertMoviesEntities();
                                }
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                Log.v(TAG, TAG_M + "onPrepareLoad() " + " movie: " + movieId + " url: " + url);
                            }
                        });

                Thread.sleep(50);
            }
        } catch (Exception ex) {
            Log.e(TAG, TAG_M + ex.getMessage());
        }
    }





    private void insertMoviesEntities() {
        final String TAG_M = "insertMoviesEntities() ";

        if (this.movieEntitiesArray == null) { return; }
        Log.v(TAG, TAG_M + "+++++++++++++++++++++++++++++++++++++++++++++++++ "
                + this.movieEntitiesArray.toString());
        threadDbTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.movieDao().insertAll(movieEntitiesArray);
                Log.v(TAG, TAG_M + "+++++++++++++++++++++++++++++++++++++++++++++++++ inserted ");
            }
        });
        PopularMoviesApplication.getEventBus().post(new FetchededNewMovieListEvent());

    }


    public void listFavoritesMovies() {
        final String TAG_M = "listFavoritesMovies() ";
        Log.v(TAG, TAG_M + "+++++++++++++++++++++++++++++++++++++++++++++++++");

        final List<Movie> movies = new ArrayList<Movie>();

        threadDbTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LiveData<List<FavoriteEntity>> favoritos = db.favoriteDao().getAll();
                if(favoritos.getValue() == null) { return;}
                int[] ids = new int[favoritos.getValue().size()];
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = favoritos.getValue().get(i).getId();
                }
                movies.addAll(MovieEntity.toListModel(db.movieDao().loadAllByIds(ids).getValue()));
            }
        });

       PopularMoviesApplication.getEventBus().post(
               new DownloadedMovieListEvent(movies, false));
    }

    private void setFavorite(final int idMovie) {
        final String TAG_M = "setFavorite() ";
        Log.v(TAG, TAG_M + "+++++++++++++++++++++++++++++++++++++++++++++++++");
        threadDbTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                FavoriteEntity fav = new FavoriteEntity(idMovie ,1);
                db.favoriteDao().insert(fav);
                Log.v(TAG, fav + "=======================================================");

            }
        });
        PopularMoviesApplication.getEventBus().post(new EstablishedMovieFavoriteEvent(true));
    }

    private void unsetFavorite(final int idMovie) {
        final String TAG_M = "unsetFavorite() ";
        Log.v(TAG, TAG_M + "+++++++++++++++++++++++++++++++++++++++++++++++++");
        threadDbTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                FavoriteEntity fav = new FavoriteEntity(idMovie, 1);
                db.favoriteDao().delete(fav);
                Log.v(TAG, fav + "=======================================================");

            }
        });
        PopularMoviesApplication.getEventBus().post(new EstablishedMovieFavoriteEvent(false));
    }


    @Subscribe
    public void establishFavorite(SetFavoriteMovieEvent event) {
        final String TAG_M = "establishFavorite() ";
        Log.v(TAG, TAG_M);
        if (event.isMovieFavorite()) {
            setFavorite(event.getMovieId());
        } else {
            unsetFavorite(event.getMovieId());
        }
    }




    @Override
    public LiveData<List<Movie>> getPopularMovies() {
        if(moviesPopularMutable.getValue().size() == 0) {
            fetchPopularMovieList();
        }

        return moviesPopularMutable;
    }

    @Override
    public LiveData<List<Movie>> getRatedMovies() {
        if(moviesTopRatedMutable.getValue().size() == 0) {
            fetchTopRatedMovieList();
        }
        return this.moviesTopRatedMutable;
    }

    @Override
    public LiveData<List<Movie>> getFavoriteMovies() {
        return this.moviesFavoriteMutable;
    }












    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return this.binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    public class PopularMoviesRepositoryServiceBinder extends Binder {

        public PopularMoviesRepositoryService getService() {
            return PopularMoviesRepositoryService.this;
        }
    }
}
