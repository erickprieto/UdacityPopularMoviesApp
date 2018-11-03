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
 *
 * @author Erick Prieto
 * @since 2018
 */
public class PageResultReviewsTO implements ModelPageTO, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("results")
    @Expose
    private List<ReviewTO> reviews = null;

    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    public final static Parcelable.Creator<PageResultReviewsTO> CREATOR = new Creator<PageResultReviewsTO>() {


        @SuppressWarnings({"unchecked"})
        public PageResultReviewsTO createFromParcel(Parcel in) {
            return new PageResultReviewsTO(in);
        }

        public PageResultReviewsTO[] newArray(int size) {
            return (new PageResultReviewsTO[size]);
        }

    }
            ;

    protected PageResultReviewsTO(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.reviews, (ReviewTO.class.getClassLoader()));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public PageResultReviewsTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<ReviewTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewTO> reviews) {
        this.reviews = reviews;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("page", page)
                .append("reviews", reviews)
                .append("totalPages", totalPages)
                .append("totalResults", totalResults)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id).append(reviews)
                .append(totalResults)
                .append(page).append(totalPages)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PageResultReviewsTO) == false) {
            return false;
        }
        PageResultReviewsTO rhs = ((PageResultReviewsTO) other);
        return new EqualsBuilder().append(id, rhs.id)
                .append(reviews, rhs.reviews)
                .append(totalResults, rhs.totalResults)
                .append(page, rhs.page)
                .append(totalPages, rhs.totalPages)
                .isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(page);
        dest.writeList(reviews);
        dest.writeValue(totalPages);
        dest.writeValue(totalResults);
    }

    public int describeContents() {
        return 0;
    }

}
