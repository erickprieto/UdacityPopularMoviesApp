package com.udacity.popularmovies.net.contracts.TO;

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
 * Endpoint example: https://api.themoviedb.org/3/movie/{movie_id}/videos?
 * api_key=API_KEY&language=en-US
 * <p>
 * JSON object {@link PageResultVideosTO} that contains
 * a List of {@link VideoTO}.
 * </p>
 * @author Erick Prieto
 * @since 2018
 */
public class PageResultVideosTO implements ModelPageTO, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("results")
    @Expose
    private List<VideoTO> videos = null;

    public final static Parcelable.Creator<PageResultVideosTO> CREATOR = new Creator<PageResultVideosTO>() {


        @SuppressWarnings({"unchecked"})
        public PageResultVideosTO createFromParcel(Parcel in) {
            return new PageResultVideosTO(in);
        }

        public PageResultVideosTO[] newArray(int size) {
            return (new PageResultVideosTO[size]);
        }

    }
            ;

    protected PageResultVideosTO(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.videos, (VideoTO.class.getClassLoader()));
    }

    public PageResultVideosTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideoTO> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoTO> videos) {
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
        if ((other instanceof PageResultVideosTO) == false) {
            return false;
        }
        PageResultVideosTO rhs = ((PageResultVideosTO) other);
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
