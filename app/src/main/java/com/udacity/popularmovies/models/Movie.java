package com.udacity.popularmovies.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.udacity.popularmovies.database.converters.BitmapTypeConverter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Data sent by the request, and contains a details of Movie to show in the <code>Activity</code>.
 * @author Erick Prieto
 * @since 2018
 */
public class Movie implements Parcelable {


    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return (new Movie[size]);
        }

    };

    /**
     * Date Format of {@link Movie#releaseDate}.
     */
    public static final String DATE_FORMAT = "yyyy-mm-dd";

    /**
     * Identifier of this movie.
     */
    private Integer id;

    /**
     * Name of MovieTO.
     */
    private String title;

    /**
     * ReviewTO of the movie.
     */
    private String overview;

    /**
     * Retrieve Date in {@link Movie#DATE_FORMAT} format.
     */
    private String releaseDate;

    /**
     * Contains only filename of image of movie (Poster).
     * This name needs a base url to download from API.
     */
    private String posterPath;

    private Bitmap posterImage;

    private boolean favorite;

    private List<Review> reviews;

    private List<Video> videos;

    /**
     * Establish a points of qualification between 0 and 10 for this movie.
     */
    private Double voteAverage;

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readDouble();
        this.posterPath = in.readString();
        BitmapTypeConverter bc = new BitmapTypeConverter();
        this.posterImage = bc.toBitmap(in.createByteArray());
        this.overview = in.readString();
        final ArrayList<Video> tmpVideoList =
                in.readArrayList(Video.class.getClassLoader());
        this.videos = tmpVideoList;
        final ArrayList<Review> tmpReviewList =
                in.readArrayList(Review.class.getClassLoader());
        this.reviews = tmpReviewList;
        boolean[] fav = new boolean[1];
        in.readBooleanArray(fav);
        this.favorite = fav[0];
    }

    /**
     * Default constructor.
     */
    public Movie() { }

    public Movie(Integer id
            , String title, String overview
            , String releaseDate, String posterPath
            , Double voteAverage) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.overview = overview;


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Obtains only filename of image of movie (Poster).
     * This name needs a base url to download from API.
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Establish only filename of image of movie (Poster).
     * This name needs a base url to download from API.
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * Retrieve Date in {@link Movie#DATE_FORMAT} format.
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Establish Date, must have {@link Movie#DATE_FORMAT} format.
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Bitmap getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(Bitmap posterImage) {
        this.posterImage = posterImage;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
        dest.writeString(posterPath);
        BitmapTypeConverter bc = new BitmapTypeConverter();
        dest.writeByteArray(bc.toByteArray(posterImage));
        dest.writeString(overview);
        dest.writeArray(videos != null ? videos.toArray() : null);
        dest.writeArray(reviews != null ? reviews.toArray(): null);
        dest.writeBooleanArray(new boolean[] { this.favorite });

    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        int max = (overview.length() > 20) ? 20 : overview.length();
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("releaseDate", releaseDate)
                .append("voteAverage", voteAverage)
                .append("posterPath", posterPath)
                .append("posterImage", posterImage == null ? "null" : posterImage.toString())
                .append("overview", overview.substring(0, max) + "...")
                .append("videos", videos)
                .append("reviews", reviews)
                .append("favorite", favorite)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.title)
                .append(this.releaseDate)
                .append(this.voteAverage)
                .append(this.posterPath)
                .append(this.posterImage)
                .append(this.overview)
                .append(this.videos)
                .append(this.reviews)
                .append(this.favorite)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Movie) == false) {
            return false;
        }
        Movie rhs = ((Movie) other);
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.title, rhs.title)
                .append(this.releaseDate, rhs.releaseDate)
                .append(this.voteAverage, rhs.voteAverage)
                .append(this.posterPath, rhs.posterPath)
                .append(this.posterImage, rhs.posterImage)
                .append(this.overview, rhs.overview)
                .append(this.videos,rhs.videos)
                .append(this.reviews, rhs.reviews)
                .append(this.favorite, rhs.favorite)
                .isEquals();
    }

}