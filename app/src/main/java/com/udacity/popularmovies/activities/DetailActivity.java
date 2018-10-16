package com.udacity.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.Intent;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.models.MovieServiceLanguage;
import com.udacity.popularmovies.models.PageResultReviews;
import com.udacity.popularmovies.models.PageResultVideos;
import com.udacity.popularmovies.models.Video;
import com.udacity.popularmovies.net.contracts.VideoServiceContract;
import com.udacity.popularmovies.utils.ProxyHelper;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Show Details of {@link Movie} selected on MainActivity.
 * Mandatory is to receive {@link Movie} on extra data {@link DetailActivity#ID_EXTRA_MOVIE_DETAIL}
 * attached on the {@link Intent}.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class DetailActivity extends AppCompatActivity implements Callback<PageResultVideos> {


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
     * To format Vote Average to display rate of movie.
     */
    public static final String VOTE_AVERAGE_FORMAT = "%s/10";

    /**
     * Hold Details of Movie to show.
     */
    Movie movieDetails;

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
    private RatingBar getStarBar() {
        return (RatingBar)findViewById(R.id.detailActivity_voteRatingBar); }
    private Button getFavoriteButton() {
        return (Button) findViewById(R.id.detailActivity_favoriteButton); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getIntent().hasExtra(ID_EXTRA_MOVIE_DETAIL)) {
            Log.wtf(TAG, "No data received.");
            finish();
        }

        setContentView(R.layout.activity_detail);

        this.movieDetails = getIntent().getExtras().getParcelable(ID_EXTRA_MOVIE_DETAIL);
        Log.v(TAG, movieDetails.toString());

        toFillUI();
    }

    /**
     * Download from webservice list of Video {@link Video}.
     * Enqueue Asyncromus WebService to call.
     */
    private void downloadVideoList() {
        VideoServiceContract api = ProxyHelper.getProxy(VideoServiceContract.class);

        Call<PageResultVideos> call = api.getVideos(
                ProxyHelper.WEB_SERVICES_LICENSE
                , 1
                , MovieServiceLanguage.ENGLISH_US.getValue()
                );
        Log.v(TAG, call.request().url().toString());
        call.enqueue(this);
    }

    /**
     * To fill {@link View} controls with data from {@link DetailActivity#movieDetails}.
     */
    private void toFillUI() {

        getTitleTextView().setText(movieDetails.getTitle());

        Picasso.with(this)
                .load(ProxyHelper.buildCompletePosterUrl(movieDetails.getPosterPath()))
                .into(getPosterImageView());

        getOverviewTextView().setText(movieDetails.getOverview());
        getYearTextView().setText(getYearFromReleaseDate(movieDetails.getReleaseDate()));
        getDurationTextView().setText(movieDetails.getOriginalLanguage());
        getRateTextView().setText(formatVoteAverage(movieDetails.getVoteAverage()));
        getStarBar().setNumStars(1);
        getStarBar().setRating(movieDetails.getVoteAverage().floatValue());
        getStarBar().setVisibility(View.GONE);
        //Hide Button to Vote.
        getFavoriteButton().setVisibility(View.GONE);
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
     * Extract Year of release Movie {@link Movie#getReleaseDate()} date.
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


    @Override
    public void onResponse(Call<PageResultVideos> call, Response<PageResultVideos> response) {

    }

    @Override
    public void onFailure(Call<PageResultVideos> call, Throwable t) {

    }

}
