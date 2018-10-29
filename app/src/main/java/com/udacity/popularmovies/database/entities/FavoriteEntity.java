package com.udacity.popularmovies.database.entities;

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

    @Ignore
    public FavoriteEntity() { }


    public FavoriteEntity(int id) {
        this.id = id;
    }

    @Ignore
    protected FavoriteEntity( Parcel in) {
        this.id = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    }


    @Ignore
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .toString();
    }

    @Ignore
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
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
                .isEquals();
    }

}
