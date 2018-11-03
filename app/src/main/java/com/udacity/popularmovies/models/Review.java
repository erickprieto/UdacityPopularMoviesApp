package com.udacity.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class Review implements Parcelable {

    public static final Parcelable.Creator<Review> CREATOR = new Creator<Review>() {

        @SuppressWarnings({"unchecked"})
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return (new Review[size]);
        }

    };

    private String authorUserName;

    private String contentReview;

    private String id;

    private String url;

    protected Review(Parcel in) {
        this.authorUserName = ((String) in.readValue((String.class.getClassLoader())));
        this.contentReview = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Review() {
    }

    public Review(String authorUserName
            , String contentReview
            , String id
            , String url) {
        this.authorUserName = authorUserName;
        this.contentReview = contentReview;
        this.id = id;
        this.url = url;
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
        int max = (contentReview.length() > 20) ? 20 : contentReview.length();
        return new ToStringBuilder(this)
                .append("authorUserName", authorUserName)
                .append("contentReview", contentReview.substring(0, max) + "...")
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
        if ((other instanceof Review) == false) {
            return false;
        }
        Review rhs = ((Review) other);
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

}