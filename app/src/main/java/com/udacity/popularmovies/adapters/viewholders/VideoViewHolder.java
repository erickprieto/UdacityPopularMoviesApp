package com.udacity.popularmovies.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.adapters.VideosAdapter;
import com.udacity.popularmovies.models.Video;

/**
 * <code>VideoViewHolder</code> to build with many <code>View</code>,
 * that will be fill with {@link Video#getName()}.
 * Several <code>VideoViewHolder</code> build a vertical list of {@link Video}.
 */
public class VideoViewHolder extends RecyclerView.ViewHolder {

    private ImageView iconPlayVideoImageView;
    private ImageView iconMovieVideoImageView;
    private TextView titleVideoTextEdit;
    private ImageView iconShareVideoImageView;

    /**
     * Constructor build templates of layout to fill the adapter.
     * @param itemView {@link View} parent of elements to show on {@link VideosAdapter}.
     */
    public VideoViewHolder(View itemView) {

        super(itemView);
        iconPlayVideoImageView = (ImageView) itemView.findViewById(R.id.videoViewHolder_playMovieIconImageView);
        iconMovieVideoImageView = (ImageView) itemView.findViewById(R.id.videoViewHolder_movieIconImageView);
        iconShareVideoImageView = (ImageView) itemView.findViewById(R.id.videoViewHolder_shareIconImageView);

        titleVideoTextEdit = (TextView) itemView.findViewById(R.id.videoViewHolder_videoTitleTextView);
    }

    public TextView getTitleVideoTextEdit() {

        return titleVideoTextEdit;
    }

    public void setTitleVideoTextEdit(TextView titleVideoTextEdit) {
        this.titleVideoTextEdit = titleVideoTextEdit;
    }

    public ImageView getIconPlayVideoImageView() {
        return iconPlayVideoImageView;
    }

    public void setIconPlayVideoImageView(ImageView iconPlayVideoImageView) {
        this.iconPlayVideoImageView = iconPlayVideoImageView;
    }

    public ImageView getIconMovieVideoImageView() {
        return iconMovieVideoImageView;
    }

    public void setIconMovieVideoImageView(ImageView iconMovieVideoImageView) {
        this.iconMovieVideoImageView = iconMovieVideoImageView;
    }

    public ImageView getIconShareVideoImageView() {
        return iconShareVideoImageView;
    }

    public void setIconShareVideoImageView(ImageView iconShareVideoImageView) {
        this.iconShareVideoImageView = iconShareVideoImageView;
    }
}
