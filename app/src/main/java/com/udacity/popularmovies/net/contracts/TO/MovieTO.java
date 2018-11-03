package com.udacity.popularmovies.net.contracts.TO;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.popularmovies.models.Movie;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Data sent by the request, and contains a details of MovieTO to show in the <code>Activity</code>.
 * @author Erick Prieto
 * @since 2018
 */
public class MovieTO implements ModelTO, Parcelable{

    /**
     * Date Format of {@link MovieTO#releaseDate}.
     */
    public static final String DATE_FORMAT = "yyyy-mm-dd";

    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    /**
     * Identifier of this movie.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("video")
    @Expose
    private Boolean video;

    /**
     * Establish a points of qualification between 0 and 10 for this movie.
     */
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    /**
     * Name of MovieTO.
     */
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * Establish a points of popularity of this movie.
     */
    @SerializedName("popularity")
    @Expose
    private Double popularity;

    /**
     * Contains only filename of image of movie (Poster).
     * This name needs a base url to download from API.
     */
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("adult")
    @Expose
    private Boolean adult;

    /**
     * ReviewTO of the movie.
     */
    @SerializedName("overview")
    @Expose
    private String overview;

    /**
     * Retrieve Date in {@link MovieTO#DATE_FORMAT} format.
     */
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    public final static Creator<MovieTO> CREATOR = new Creator<MovieTO>() {

        @Override
        public MovieTO createFromParcel(Parcel in) {
            return new MovieTO(in);
        }

        @Override
        public MovieTO[] newArray(int size) {
            return (new MovieTO[size]);
        }

    };

    protected MovieTO(Parcel in) {
        this.voteCount = in.readInt();
        this.id = in.readInt();
        this.video = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.voteAverage = in.readDouble();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.posterPath = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.genreIds = in.readArrayList(Integer.class.getClassLoader());
        this.backdropPath = in.readString();
        this.adult = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.overview = in.readString();
        this.releaseDate = in.readString();
    }

    /**
     * Default constructor.
     */
    public MovieTO() {
        }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
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

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * Retrieve Date in {@link MovieTO#DATE_FORMAT} format.
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Establish Date, must have {@link MovieTO#DATE_FORMAT} format.
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(voteCount);
        dest.writeInt(id);
        dest.writeValue(video);
        dest.writeDouble(voteAverage);
        dest.writeString(title);
        dest.writeDouble(popularity);
        dest.writeString(posterPath);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeList(genreIds);
        dest.writeString(backdropPath);
        dest.writeValue(adult);
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
                .append("originalTitle", originalTitle)
                .append("originalLanguage", originalLanguage)
                .append("voteCount", voteCount)
                .append("popularity", popularity)
                .append("adult", adult)
                .append("video", video)
                .append("genreIds", genreIds)
                .append("backdropPath", backdropPath)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(genreIds)
                .append(originalLanguage)
                .append(adult)
                .append(backdropPath)
                .append(voteCount)
                .append(id)
                .append(title)
                .append(releaseDate)
                .append(overview)
                .append(posterPath)
                .append(originalTitle)
                .append(voteAverage)
                .append(video)
                .append(popularity)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MovieTO) == false) {
            return false;
        }
        MovieTO rhs = ((MovieTO) other);
        return new EqualsBuilder()
                .append(genreIds, rhs.genreIds)
                .append(originalLanguage, rhs.originalLanguage)
                .append(adult, rhs.adult)
                .append(backdropPath, rhs.backdropPath)
                .append(voteCount, rhs.voteCount)
                .append(id, rhs.id)
                .append(title, rhs.title)
                .append(releaseDate, rhs.releaseDate)
                .append(overview, rhs.overview)
                .append(posterPath, rhs.posterPath)
                .append(originalTitle, rhs.originalTitle)
                .append(voteAverage, rhs.voteAverage)
                .append(video, rhs.video)
                .append(popularity, rhs.popularity)
                .isEquals();
    }

    public Movie toModel() {
        return new Movie(this.id, this.title, this.overview, this.releaseDate, this.posterPath, this.voteAverage);
    }

    public static List<Movie> toListModel(List<MovieTO> tos) {
         List<Movie> result = new ArrayList<>();
         for (MovieTO to : tos) {
            result.add(to.toModel());
         }
         return result;
    }

}