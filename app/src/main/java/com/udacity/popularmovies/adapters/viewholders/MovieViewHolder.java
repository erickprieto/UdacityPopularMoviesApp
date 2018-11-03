package com.udacity.popularmovies.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.adapters.MoviesAdapter;
import com.udacity.popularmovies.models.Movie;

/**
 * <code>MovieViewHolder</code> to build with <code>ImageView</code>,
 * that will be fill with {@link Movie#getPosterPath()}.
 * Several <code>MovieViewHolder</code> build a mosaic of {@link Movie}.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder{

    private ImageView posterImageView;

    /**
     * Constructor build templates of layout to fill the adapter.
     * @param itemView {@link View} of every element to show on {@link MoviesAdapter}.
     */
    public MovieViewHolder(View itemView) {
        super(itemView);
        this.posterImageView = (ImageView) itemView.findViewById(R.id.movieViewHolder_posterImageView);
    }

    public ImageView getPosterImageView() {
        return posterImageView;
    }

    public void setPosterImageView(ImageView posterImageView) {
        this.posterImageView = posterImageView;
    }
}