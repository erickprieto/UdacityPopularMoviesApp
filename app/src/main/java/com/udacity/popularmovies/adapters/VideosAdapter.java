package com.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.activities.DetailActivity;
import com.udacity.popularmovies.adapters.viewholders.VideoViewHolder;
import com.udacity.popularmovies.models.Video;

import java.util.List;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class VideosAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = VideosAdapter.class.getSimpleName();

    private List<Video> videoList;

    private Context context;

    public VideosAdapter(List<Video> videos, Context context) {
        this.videoList = videos;
        this.context = context;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View videoItemView = inflater.inflate(R.layout.activitydetail_videoitem, parent, false);

        VideoViewHolder viewHolder = new VideoViewHolder(videoItemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        final String TAG_M = "onBindViewHolder() ";
        final Video video = this.videoList.get(position);

        holder.getTitleVideoTextEdit().setText(video.getName());
        establishViewsListeners(video, holder);

        Log.v(TAG, TAG_M + video.getName());
    }

    /**
     * Establish action for <c>onClick</c> listeners.
     * @param video <c>Video</c> item selected.
     * @param holder <c>code</c> holder that contains view.
     */
    private void establishViewsListeners(final Video video, VideoViewHolder holder) {

        holder.getIconPlayVideoImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchExternalWebLink(new String []{ video.buildWebYoutubeUrl(), video.buildAppYoutubeUrl() });
            }
        });

        holder.getTitleVideoTextEdit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchExternalWebLink(new String []{ video.buildWebYoutubeUrl(), video.buildAppYoutubeUrl() });
            }
        });

        holder.getIconShareVideoImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchShareWebLink(video);
            }
        });
    }

    @Override
    public int getItemCount() {
        int count;

        if(this.videoList != null && !this.videoList.isEmpty()) {
            count = this.videoList.size();
        }
        else {
            count = 0;
        }
        return count;
    }

    /**
     * Update {@link VideosAdapter#videoList} without rewrite the variable.
     * After of to update, launch event to notify {@link RecyclerView.Adapter#notifyDataSetChanged()}
     * of changes.
     * @param videos New list<code>VideoTO</code> to update.
     */
    public void putVideos(final List<Video> videos) {
        if (videos == null) { return; }
        this.videoList.clear();
        this.videoList.addAll(videos);
        notifyDataSetChanged();
    }

    /**
     * Solicite to {@link DetailActivity} to launch a share action of selected video.
     * @param urls <c>String[]</c> http link and Android App protocols to launch.
     *             first element contains http.
     */
    private void launchExternalWebLink(String[] urls) {
        ((DetailActivity) context).startYoutube(urls);
    }

    /**
     * Solicite to {@link DetailActivity} to launch a share action of selected video.
     * @param video <c>Video</c> info to share.
     */
    private void launchShareWebLink(Video video) {
        ((DetailActivity) context).shareYouTubeVideoTrailer(video);
    }
}
