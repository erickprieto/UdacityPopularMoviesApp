package com.udacity.popularmovies.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.PopularMoviesApplication;
import com.udacity.popularmovies.adapters.ReviewsAdapter;
import com.udacity.popularmovies.adapters.VideosAdapter;
import com.udacity.popularmovies.events.DownloadedReviewListEvent;
import com.udacity.popularmovies.events.DownloadedVideoListEvent;
import com.udacity.popularmovies.events.EstablishedMovieFavoriteEvent;
import com.udacity.popularmovies.events.SetFavoriteMovieEvent;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.models.Review;
import com.udacity.popularmovies.models.Video;
import com.udacity.popularmovies.utils.ProxyHelper;
import com.udacity.popularmovies.utils.UrlValidator;
import com.udacity.popularmovies.viewmodels.DetailViewModel;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Show Details of {@link Movie} selected on MainActivity.
 * Mandatory is to receive {@link Movie} on extra data {@link DetailActivity#ID_EXTRA_MOVIE_DETAIL}
 * attached on the {@link Intent}.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class DetailActivity extends AppCompatActivity {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = DetailActivity.class.getSimpleName();

    /**
     * Identifier of extra data contained on {@link Intent} to show on this Activity.
     * Mandatory is to receive extra data on the {@link Intent}.
     */
    public static final String ID_EXTRA_MOVIE_DETAIL = "movieDetails";

    /**
     * Key to identify <c>Movie</c> contained on <c>Bundle</c>.
     */
    public static final String KEY_DETAILS_MOVIE = "DetailsMovie";

    /**
     * To format Vote Average to display rate of movie.
     */
    private static final String VOTE_AVERAGE_FORMAT = "%s/10";

    /**
     * Media type to define Share Content on <c>Intent</c>
     */
    private static final String SHARING_URL_TYPE = "text/plain";

    /**
     * This Activity
     */
    private Context context;

    /**
     * {@link DetailViewModel} to hold <c>Movie</c> in this <c>Activity</c>
     */
    private DetailViewModel detailViewModel;

    /**
     * Adapter to display <c>List Video</c>
     */
    private VideosAdapter videosAdapter;

    /**
     * Adapter to display <c>List Review</c>
     */
    private ReviewsAdapter reviewsAdapter;


    //To assign Views
    private TextView getTitleTextView() {
        return (TextView) findViewById(R.id.detailActivity_titleTextView); }
    private ImageView getPosterImageView() {
        return (ImageView) findViewById(R.id.detailActivity_posterImageView); }
    private TextView getOverviewTextView() {
        return (TextView) findViewById(R.id.detailActivity_overviewTextView); }
    private TextView getYearTextView() {
        return (TextView) findViewById(R.id.detailActivity_yearTextView); }
    private TextView getDurationTextView() {
        return (TextView) findViewById(R.id.detailActivity_durationTextView); }
    private TextView getRateTextView() {
        return (TextView) findViewById(R.id.detailActivity_voteAverageTextView); }
    private ToggleButton getFavoriteButton() {
        return (ToggleButton) findViewById(R.id.detailActivity_favoriteButton); }
    private RecyclerView getVideosRecyclerView() {
        return (RecyclerView) findViewById(R.id.detailActivity_videosRecyclerView); }
    private RecyclerView getReviewsRecyclerView() {
        return (RecyclerView) findViewById(R.id.detailActivity_reviewsRecyclerView); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        if (!getIntent().hasExtra(ID_EXTRA_MOVIE_DETAIL)) {
            Log.wtf(TAG, "No data received on Intent.");
            finish();
        }

        this.context = this;
        setContentView(R.layout.activity_detail);


        this.detailViewModel.setMovieDetails((Movie) getIntent().getExtras().getParcelable(ID_EXTRA_MOVIE_DETAIL));

        Log.v(TAG, this.detailViewModel.getMovieDetails().toString());

        fillViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        PopularMoviesApplication.getEventBus().register(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_DETAILS_MOVIE, this.detailViewModel.getMovieDetails());
    }

    @Override
    protected void onPause() {
        PopularMoviesApplication.getEventBus().unregister(this);
        super.onPause();
    }

    /**
     * To fill {@link View} controls with data from {@link DetailViewModel#movieDetails}.
     */
    private void fillViews() {
        final String TAG_M = "fillViews() ";
        getTitleTextView().setText(detailViewModel.getMovieDetails().getTitle());
        getOverviewTextView().setText(detailViewModel.getMovieDetails().getOverview());
        getYearTextView().setText(getYearFromReleaseDate(detailViewModel.getMovieDetails().getReleaseDate()));
        getDurationTextView().setText(R.string.DetailActivity_durationTextView);
        getRateTextView().setText(formatVoteAverage(detailViewModel.getMovieDetails().getVoteAverage()));
        establishFavoriteButton();
        getFavoriteButton().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toogleFavoriteButton();
            }
        });

        prepareAdaptersAndRecyclerViews();

        if(detailViewModel.getMovieDetails().getPosterImage() == null) {
            Log.v(TAG, TAG_M + "Poster from URL");
            Picasso.with(this)
                    .load(ProxyHelper.buildCompletePosterUrl(detailViewModel.getMovieDetails().getPosterPath()))
                    .error(R.drawable.ic_baseline_broken_image_24px)
                    .into(getPosterImageView());
        } else {
            Log.v(TAG, TAG_M + "Poster from Bitmap");
            getPosterImageView().setImageBitmap(detailViewModel.getMovieDetails().getPosterImage());
        }

    }

    /**
     * Establish the <c>Adapter</c> and initialize <c>RecyclerView</c>
     * to display <c>Video</c> and <c>Review</c>.
     */
    private void prepareAdaptersAndRecyclerViews() {
        final String TAG_M = "prepareAdaptersAndRecyclerViews() ";
        Log.v(TAG, TAG_M + this.detailViewModel.getMovieDetails().toString());

        //New List to avoid cross references on Movie Lists objects.
        videosAdapter  = new VideosAdapter(new ArrayList<Video>(), this.context);
        reviewsAdapter = new ReviewsAdapter(new ArrayList<Review>(), this.context);

        getVideosRecyclerView().setLayoutManager(new LinearLayoutManager(this.context, RecyclerView.VERTICAL, false));
        getReviewsRecyclerView().setLayoutManager(new LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false));
        getVideosRecyclerView().setAdapter(videosAdapter);
        getReviewsRecyclerView().setAdapter(reviewsAdapter);

        refreshVideosAdapter();
        refreshReviewsList();

    }

    /**
     * Update {@link DetailActivity#videosAdapter}.
     */
    private void refreshVideosAdapter() {
        final String TAG_M = "refreshVideosAdapter() ";
        Log.v(TAG, TAG_M + this.detailViewModel.getMovieDetails().getVideos().toString());
        videosAdapter.putVideos(this.detailViewModel.getMovieDetails().getVideos());
    }

    /**
     * Update {@link DetailActivity#reviewsAdapter}.
     */
    private void refreshReviewsList() {
        final String TAG_M = "refreshReviewsList() ";
        Log.v(TAG, TAG_M + this.detailViewModel.getMovieDetails().getReviews().toString());
        reviewsAdapter.putReviews(this.detailViewModel.getMovieDetails().getReviews());
    }

    /**
     * Send a request to {@link com.udacity.popularmovies.services.PopularMoviesRepositoryService}
     * to check as favorite {@link DetailViewModel#movieDetails}.
     */
    private void toogleFavoriteButton() {
        if(detailViewModel.getMovieDetails().isFavorite()) {
            detailViewModel.getMovieDetails().setFavorite(false);
            PopularMoviesApplication.getEventBus()
                    .post(new SetFavoriteMovieEvent(detailViewModel.getMovieDetails().getId()
                            , detailViewModel.getMovieDetails().isFavorite()));
        } else {
            detailViewModel.getMovieDetails().setFavorite(true);
            PopularMoviesApplication.getEventBus()
                    .post(new SetFavoriteMovieEvent(detailViewModel.getMovieDetails().getId()
                            , detailViewModel.getMovieDetails().isFavorite()));
        }

    }

    /**
     * Establish Favorite <c>Button</c> status.
     * @return <c>boolean</c> true if its favorite.
     */
    private boolean establishFavoriteButton() {

        if(!this.detailViewModel.getMovieDetails().isFavorite()) {
            getFavoriteButton().setChecked(false);
            return false;
        } else {
            getFavoriteButton().setChecked(true);
            return true;
        }

    }


    /**
     * To format Score of votes, using {@link DetailActivity#VOTE_AVERAGE_FORMAT}.
     * Example: 8.7/10
     * @param voteAverageMovie {@link Movie#getVoteAverage()} to format.
     * @return <code>String</code> formated like: X/10
     */
    private String formatVoteAverage(Double voteAverageMovie) {
        return String.format(VOTE_AVERAGE_FORMAT, voteAverageMovie);
    }

    /**
     * Extract Year of release MovieTO {@link Movie#getReleaseDate()} date.
     * @param releaseDate Date store on string format {@link Movie#DATE_FORMAT}.
     * @return String with year on format "yyyy" or null if {@see param} is null.
     */
    private String getYearFromReleaseDate(String releaseDate) {
        try {
            if(StringUtils.isEmpty(releaseDate)) { return StringUtils.EMPTY; }

            SimpleDateFormat sdf = new SimpleDateFormat(Movie.DATE_FORMAT);
            Date date = sdf.parse(releaseDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            return year + StringUtils.EMPTY;
        } catch (ParseException pex) {
            Log.e(TAG, pex.getMessage());
            return StringUtils.EMPTY;
        }

    }

    /**
     * Receive confirmation from {@link com.udacity.popularmovies.services.PopularMoviesRepositoryService}
     * about of request to check as favorite {@link DetailViewModel#movieDetails}
     * @param event Contains <c>boolean</c> with the status of favorite.
     */
    @Subscribe
    public void establishFavoriteMovie(EstablishedMovieFavoriteEvent event) {
        Log.v(TAG, "EstablishedMovieFavoriteEvent received");
        this.detailViewModel.getMovieDetails().setFavorite(event.isMovieFavorite());

        boolean a = establishFavoriteButton();

        Toast.makeText(context
                , a ? "Favorite Movie Added" : "Favorite Movie Removed"
                , Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * Receive an updated List <c>Video</c>
     * from {@link com.udacity.popularmovies.services.PopularMoviesRepositoryService}.
     * @param event Contains List <c>Video</c>
     */
    @Subscribe
    public void getVideoList(DownloadedVideoListEvent event) {
        Log.v(TAG, "DownloadedVideoListEvent received");
        this.detailViewModel.getMovieDetails().setVideos(event.getVideos());
        refreshVideosAdapter();
    }

    /**
     * Receive an updated List <c>Review</c>
     * from {@link com.udacity.popularmovies.services.PopularMoviesRepositoryService}.
     * @param event Contains List <c>Review</c>
     */
    @Subscribe
    public void getReviewList(DownloadedReviewListEvent event) {
        Log.v(TAG, "DownloadedReviewListEvent received");
        this.detailViewModel.getMovieDetails().setReviews(event.getReviews());
        refreshReviewsList();
    }

    /**
     * Request open <a href="http://www.youtube.com">Youtube</a> Video from {@param urls},
     * index 0 was builded from {@link Video#buildWebYoutubeUrl()}
     * and index 1 was builded from {@link Video#buildAppYoutubeUrl()}.
     * <p>
     *     They was builded using {@link Video#YOUTUBE_BASE_URL} and {@link Video#YOUTUBE_APP_URL}.
     * </p>
     * @param urls <c>String</c> urls
     */
    public void startYoutube(String[] urls) {
        startWebView(urls[0]);
    }

    /**
     * Request open a Webpage from {@param url}.
     * Uses {@link UrlValidator#validate(String)} only to check if protocol was define.
     * @param url <c>String</c> Address to open.
     */
    public void startWebView(String url){
        final String TAG_M = "startWebView() ";
        Log.v(TAG, TAG_M + url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlValidator.validate(url)));
        startActivity(webIntent);
    }

    /**
     * Request to Android show to user options to share a
     * <a href="http://www.youtube.com">Youtube</a> <c>Video</c> on social media connectors.
     * @param video Title and url ready to share.
     */
    public void shareYouTubeVideoTrailer(Video video) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(SHARING_URL_TYPE);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, video.getName());
        shareIntent.putExtra(Intent.EXTRA_TEXT, video.buildWebYoutubeUrl());
        startActivity(Intent.createChooser(shareIntent, video.getName()));
    }
}
