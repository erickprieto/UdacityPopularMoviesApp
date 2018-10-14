package com.udacity.popularmovies.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Model of Response data obtained from endpoint:
 * <code>https://api.themoviedb.org/3/discover/movie</code>
 * Endpoint example: https://api.themoviedb.org/3/discover/movie
 *   ?api_key=API_KEY&language=en-US&sort_by=popularity.desc
 *   &include_adult=false&include_video=false&page=1
 * <p>
 * JSON object {@link PageResult} that contains
 * a List of {@link com.udacity.popularmovies.models.Movie}.
 * </p>
 * @author Erick Prieto
 * @since 2018
 */
public class PageResult implements Parcelable
{

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    /**
     * List of Movies sorted by {@link MovieServiceSortBy}, sent on the request.
     */
    @SerializedName("results")
    @Expose
    private List<Movie> movies = null;

    public final static Parcelable.Creator<PageResult> CREATOR = new Creator<PageResult>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PageResult createFromParcel(Parcel in) {
            return new PageResult(in);
        }

        public PageResult[] newArray(int size) {
            return (new PageResult[size]);
        }

    }
            ;

    protected PageResult(Parcel in) {
        this.page = (Integer) in.readValue((Integer.class.getClassLoader()));
        this.totalResults = (Integer) in.readValue((Integer.class.getClassLoader()));
        this.totalPages = (Integer) in.readValue((Integer.class.getClassLoader()));
        in.readList(this.movies, Movie.class.getClassLoader());
    }

    /**
     * Default constructor.
     */
    public PageResult() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Obtain a List of Movies sorted by {@link MovieServiceSortBy}, sent on the request.
     * @return
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Establish a List of Movies sorted by {@link MovieServiceSortBy}, sent on the request.
     * @param movies
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("page", page)
                .append("totalResults", totalResults)
                .append("totalPages", totalPages)
                .append("movies", movies)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(totalResults)
                .append(page)
                .append(totalPages)
                .append(movies)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PageResult) == false) {
            return false;
        }
        PageResult rhs = ((PageResult) other);
        return new EqualsBuilder()
                .append(totalResults, rhs.totalResults)
                .append(page, rhs.page)
                .append(totalPages, rhs.totalPages)
                .append(movies, rhs.movies)
                .isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
        dest.writeList(movies);
    }

    public int describeContents() {
        return 0;
    }

}