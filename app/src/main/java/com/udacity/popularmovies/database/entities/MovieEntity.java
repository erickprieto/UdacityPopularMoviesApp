package com.udacity.popularmovies.database.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.udacity.popularmovies.database.converters.BitmapTypeConverter;
import com.udacity.popularmovies.database.converters.PseudoBooleanTypeConverter;
import com.udacity.popularmovies.database.converters.DateTypeConverter;
import com.udacity.popularmovies.models.Movie;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity(tableName = "movie")
public class MovieEntity implements Parcelable {

    @Ignore
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

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT)
    private String title;

    @ColumnInfo(name = "overview", typeAffinity = ColumnInfo.TEXT)
    private String overview;

    @ColumnInfo(name = "releaseDate", typeAffinity = ColumnInfo.INTEGER)
    @TypeConverters({DateTypeConverter.class})
    private Date releaseDate;

    @ColumnInfo(name = "rating", typeAffinity = ColumnInfo.REAL)
    private double rating;

    @ColumnInfo(name = "poster_file", typeAffinity = ColumnInfo.TEXT)
    private String posterFileName;

    @ColumnInfo(name = "poster_jpg", typeAffinity = ColumnInfo.BLOB)
    @TypeConverters({BitmapTypeConverter.class})
    private Bitmap poster;

    @ColumnInfo(name = "popular", typeAffinity = ColumnInfo.INTEGER)
    @TypeConverters({PseudoBooleanTypeConverter.class})
    private int popular;

    @ColumnInfo(name = "created", typeAffinity = ColumnInfo.INTEGER)
    @TypeConverters({DateTypeConverter.class})
    private Date created;

    @Ignore
    public MovieEntity() { }


    public MovieEntity(int id
            , String title
            , String overview
            , Date releaseDate
            , double rating
            , String posterFileName
            , Bitmap poster
            , int popular
            , Date created) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.posterFileName = posterFileName;
        this.poster = poster;
        this.popular = popular;
        this.created = created;
    }

    @Ignore
    protected MovieEntity( Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.releaseDate = (Date) in.readValue(Date.class.getClassLoader());
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPosterFileName() {
        return posterFileName;
    }

    public void setPosterFileName(String posterFileName) {
        this.posterFileName = posterFileName;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    @NonNull
    public int getPopular() {
        return popular;
    }

    public void setPopular(@NonNull int popular) {
        this.popular = popular;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Ignore
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("title", this.title)
                .append("releaseDate", this.releaseDate)
                .append("posterFileName", this.posterFileName)
                .append("overview", this.overview)
                .append("popular", this.popular)
                .append("created", this.created)
                .toString();
    }

    @Ignore
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.title)
                .append(this.releaseDate)
                .append(this.posterFileName)
                .append(this.overview)
                .append(this.popular)
                .append(this.created)
                .toHashCode();
    }

    @Ignore
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
                .append(this.id, rhs.id)
                .append(this.title, rhs.title)
                .append(this.releaseDate, rhs.releaseDate)
                .append(this.overview, rhs.overview)
                .append(this.posterFileName, rhs.posterFileName)
                .append(this.popular, rhs.popular)
                .append(this.created, rhs.created)
                .isEquals();
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeValue(releaseDate);
    }

    @Ignore
    public Movie toModel() {
        SimpleDateFormat sdf = new SimpleDateFormat(Movie.DATE_FORMAT);

        return new Movie(this.id
                , this.title
                , this.overview
                , sdf.format(this.releaseDate)
                , this.posterFileName
                , this.rating);
    }

    @Ignore
    public static final List<Movie> toListModel(List<MovieEntity> entities) {
        if(entities == null) { return null; }
        List<Movie> result = new ArrayList<Movie>();
        for (MovieEntity entity : entities) {
            result.add(entity.toModel());
        }
        return result;
    }

    public static final MovieEntity fromModel(Movie model, boolean popular) {
        SimpleDateFormat sdf = new SimpleDateFormat(Movie.DATE_FORMAT);

        return new MovieEntity(model.getId()
                , model.getTitle()
                , model.getOverview()
                , sdf.parse(model.getReleaseDate(),new ParsePosition(0))
                , model.getVoteAverage()
                , model.getPosterPath()
                , null
                , popular == true ? 1 : 0
                , new Date(System.currentTimeMillis()));
    }

}
