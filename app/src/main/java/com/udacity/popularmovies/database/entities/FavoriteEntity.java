package com.udacity.popularmovies.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity(tableName = "favorite")
public class FavoriteEntity implements Parcelable {

    @Ignore
    public static final Creator<FavoriteEntity> CREATOR = new Creator<FavoriteEntity>() {
        @Override
        public FavoriteEntity createFromParcel(Parcel in) {
            return new FavoriteEntity(in);
        }

        @Override
        public FavoriteEntity[] newArray(int size) {
            return new FavoriteEntity[size];
        }
    };

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "favorite")
    private int favorite;

    @Ignore
    public FavoriteEntity() { }


    public FavoriteEntity(int id, int favorite) {
        this.id = id;
        this.favorite = favorite;
    }

    @Ignore
    protected FavoriteEntity( Parcel in) {
        this.id = in.readInt();
        this.favorite = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
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
        dest.writeInt(favorite);
    }


    @Ignore
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("favorite", this.favorite)
                .toString();
    }

    @Ignore
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.favorite)
                .toHashCode();
    }

    @Ignore
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FavoriteEntity) == false) {
            return false;
        }
        FavoriteEntity rhs = ((FavoriteEntity) other);
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.favorite, rhs.favorite)
                .isEquals();
    }

}
