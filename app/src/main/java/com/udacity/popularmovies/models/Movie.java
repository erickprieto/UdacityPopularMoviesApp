package com.udacity.popularmovies.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Data sent by the request, and contains a details of Movie to show in the <code>Activity</code>.
 * @author Erick Prieto
 * @since 2018
 */
public class Movie implements Parcelable {


    public final static Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {

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

    private List<Review> reviews;

    private List<Video> videos;

    /**
     * Establish a points of qualification between 0 and 10 for this movie.
     */
    private Double voteAverage;

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.voteAverage = in.readDouble();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
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
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(voteAverage);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("releaseDate", releaseDate)
                .append("voteAverage", voteAverage)
                .append("posterPath", posterPath)
                .append("overview", overview)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(title)
                .append(releaseDate)
                .append(overview)
                .append(posterPath)
                .append(voteAverage)
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
                .append(id, rhs.id)
                .append(title, rhs.title)
                .append(releaseDate, rhs.releaseDate)
                .append(overview, rhs.overview)
                .append(posterPath, rhs.posterPath)
                .append(voteAverage, rhs.voteAverage)
                .isEquals();
    }

}