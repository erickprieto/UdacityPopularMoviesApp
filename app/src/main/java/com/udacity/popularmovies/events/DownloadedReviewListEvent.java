package com.udacity.popularmovies.events;

import com.udacity.popularmovies.models.Review;

import java.util.List;

public class DownloadedReviewListEvent {

    private List<Review> reviews;

    public DownloadedReviewListEvent(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> videos) {
        this.reviews = videos;
    }
}
