package com.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.activities.DetailActivity;
import com.udacity.popularmovies.adapters.viewholders.ReviewViewHolder;
import com.udacity.popularmovies.models.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = ReviewsAdapter.class.getSimpleName();

    private List<Review> reviewList;

    private Context context;

    public ReviewsAdapter(List<Review> reviews, Context context) {
        this.reviewList = reviews;
        this.context = context;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View reviewItemView = inflater.inflate(R.layout.activitydetail_reviewitem, parent, false);

        ReviewViewHolder viewHolder = new ReviewViewHolder(reviewItemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        final String TAG_M = "onBindViewHolder() ";
        final Review review = this.reviewList.get(position);
        holder.getAuthorReviewTextView().setText(review.getAuthorUserName());
        holder.getTitleReviewTextView().setText(review.getContentReview());

        establishViewsListeners(review, holder);

        Log.v(TAG, TAG_M + review.getAuthorUserName() + " - " + review.getUrl());
    }

    private void establishViewsListeners(final Review review, ReviewViewHolder holder) {

        holder.getAuthorReviewTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchExternalWebLink(review.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        int count;

        if(this.reviewList != null && !this.reviewList.isEmpty()) {
            count = this.reviewList.size();
        }
        else {
            count = 0;
        }
        return count;
    }

    /**
     * Update {@link ReviewsAdapter#reviewList} without rewrite the variable.
     * After of to update, launch event to notify {@link RecyclerView.Adapter#notifyDataSetChanged()}
     * of changes.
     * @param reviews New list<code>ReviewTO</code> to update.
     */
    public void putReviews(final List<Review> reviews) {
        if (reviews == null) { return; }
        this.reviewList.clear();
        this.reviewList.addAll(reviews);
        notifyDataSetChanged();
    }

    private void launchExternalWebLink(String url) {
        ((DetailActivity) context).startWebView(url);
    }

}
