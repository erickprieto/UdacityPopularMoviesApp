package com.udacity.popularmovies.database.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


@Entity(tableName = "movie")
public class MovieEntity implements Parcelable {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "releaseDate")
    private String releaseDate;

    @ColumnInfo(name = "rating")
    private String rating;

    //@ColumnInfo(name = "poster")
    @Ignore
    private Bitmap poster;

    public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel in) {
            return new MovieEntity(in);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };

    public MovieEntity() { }

    public MovieEntity(int id, String title, String overview, String releaseDate, String rating, Bitmap poster) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.poster = poster;
    }


    protected MovieEntity(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        rating = in.readString();
        poster = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    //get
    //set

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(rating);
        dest.writeParcelable(poster, flags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("releaseDate", releaseDate)
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
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MovieEntity) == false) {
            return false;
        }
        MovieEntity rhs = ((MovieEntity) other);
        return new EqualsBuilder()
                .append(id, rhs.id)
                .append(title, rhs.title)
                .append(releaseDate, rhs.releaseDate)
                .append(overview, rhs.overview)
                .isEquals();
    }
}
