package com.udacity.popularmovies.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.adapters.ReviewsAdapter;
import com.udacity.popularmovies.models.Review;

/**
 * <code>ReviewViewHolder</code> to build with some <code>TextView</code>,
 * that will be fill with {@link Review#getContentReview()}.
 * Several <code>ReviewViewHolder</code> build a horizontal list of {@link Review}.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class ReviewViewHolder extends RecyclerView.ViewHolder {

    /**
     * Hold author ID name to show.
     */
    private TextView authorReviewTextView;

    /**
     * Hold full review to show.
     */
    private TextView titleReviewTextView;

    /**
     * Constructor build templates of layout to fill the adapter.
     * @param itemView {@link View} parent of elements to show on {@link ReviewsAdapter}.
     */
    public ReviewViewHolder(View itemView) {
        super(itemView);
        authorReviewTextView = (TextView) itemView.findViewById(R.id.reviewViewHolder_reviewAuthorTextView);
        titleReviewTextView = (TextView) itemView.findViewById(R.id.reviewViewHolder_reviewTitleTextView);

    }

    public TextView getTitleReviewTextView() {
        return titleReviewTextView;
    }

    public void setTitleReviewTextView(TextView titleReviewTextView) {
        this.titleReviewTextView = titleReviewTextView;
    }

    public TextView getAuthorReviewTextView() {
        return authorReviewTextView;
    }

    public void setAuthorReviewTextView(TextView authorReviewTextView) {
        this.authorReviewTextView = authorReviewTextView;
    }
}
