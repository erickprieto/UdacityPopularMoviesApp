package com.udacity.popularmovies.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.popularmovies.models.Video;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PageResultVideos implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("results")
    @Expose
    private List<Video> videos = null;

    public final static Parcelable.Creator<PageResultVideos> CREATOR = new Creator<PageResultVideos>() {


        @SuppressWarnings({"unchecked"})
        public PageResultVideos createFromParcel(Parcel in) {
            return new PageResultVideos(in);
        }

        public PageResultVideos[] newArray(int size) {
            return (new PageResultVideos[size]);
        }

    }
            ;

    protected PageResultVideos(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.videos, (com.udacity.popularmovies.models.Video.class.getClassLoader()));
    }

    public PageResultVideos() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("videos", this.videos)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.videos)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PageResultVideos) == false) {
            return false;
        }
        PageResultVideos rhs = ((PageResultVideos) other);
        return new EqualsBuilder()
                .append(id, rhs.id)
                .append(videos, rhs.videos)
                .isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(videos);
    }

    public int describeContents() {
        return 0;
    }

}
