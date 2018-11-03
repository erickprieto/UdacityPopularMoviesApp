package com.udacity.popularmovies.net.contracts.TO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.popularmovies.models.Review;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class ReviewTO implements ModelTO, Parcelable
{

    @SerializedName("author")
    @Expose
    private String authorUserName;

    @SerializedName("content")
    @Expose
    private String contentReview;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("url")
    @Expose
    private String url;

    public final static Parcelable.Creator<ReviewTO> CREATOR = new Creator<ReviewTO>() {


        @SuppressWarnings({"unchecked"})
        public ReviewTO createFromParcel(Parcel in) {
            return new ReviewTO(in);
        }

        public ReviewTO[] newArray(int size) {
            return (new ReviewTO[size]);
        }

    }
            ;

    protected ReviewTO(Parcel in) {
        this.authorUserName = ((String) in.readValue((String.class.getClassLoader())));
        this.contentReview = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ReviewTO() {
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(String authorUserName) {
        this.authorUserName = authorUserName;
    }

    public String getContentReview() {
        return contentReview;
    }

    public void setContentReview(String contentReview) {
        this.contentReview = contentReview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("authorUserName", authorUserName)
                .append("contentReview", contentReview)
                .append("id", id)
                .append("url", url)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(contentReview)
                .append(authorUserName)
                .append(url)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ReviewTO) == false) {
            return false;
        }
        ReviewTO rhs = ((ReviewTO) other);
        return new EqualsBuilder().append(id, rhs.id)
                .append(contentReview, rhs.contentReview)
                .append(authorUserName, rhs.authorUserName)
                .append(url, rhs.url)
                .isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(authorUserName);
        dest.writeValue(contentReview);
        dest.writeValue(id);
        dest.writeValue(url);
    }

    public int describeContents() {
        return 0;
    }

    public Review toModel() {
        return new Review(this.authorUserName
                , this.contentReview
                , this.id
                , this.url);
    }

    public static List<Review> toListModel(List<ReviewTO> tos) {
        if(tos == null) { return null; }
        List<Review> result = new ArrayList<>();
        for (ReviewTO to : tos) {
            result.add(to.toModel());
        }
        return result;
    }
}