package com.udacity.popularmovies.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.activities.MainActivity;
import com.udacity.popularmovies.adapters.viewholders.MovieViewHolder;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.utils.ProxyHelper;

import java.util.List;

/**
 * Adapter to fill <code>RecyclerView</code>
 *
 * @author Erick Prieto
 * @since 2018
 */
public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = MoviesAdapter.class.getSimpleName();

    /**
     * Data that contained a List <code>MovieTO</code> to fill this Adapter.
     */
    private List<Movie> moviesList;

    private Context context;

    /**
     * Default Constructor with Data to fill this adapter.
     * @param movies
     * @param context
     */
    public MoviesAdapter(List<Movie> movies, Context context) {

        this.context = context;
        this.moviesList = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View movieItemView = inflater.inflate(R.layout.activitymain_movieitem, parent, false);

        MovieViewHolder viewHolder = new MovieViewHolder(movieItemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final String TAG_M = "onBindViewHolder() ";
        final Movie movie = moviesList.get(position);

        if (movie.getPosterImage() == null) {
            Log.v(TAG, TAG_M + "Poster from URL");
            Picasso.with(holder.itemView.getContext())
                    .load(ProxyHelper.buildCompletePosterUrl(movie.getPosterPath()))
                    .error(R.drawable.ic_baseline_broken_image_24px)
                    .into(holder.getPosterImageView());
        } else {
            Log.v(TAG, TAG_M + "Poster from Bitmap");
            holder.getPosterImageView().setImageBitmap(movie.getPosterImage());
        }

        holder.getPosterImageView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ((MainActivity)context).startDetailActivity(movie);
            }
        });


    }

    @Override
    public int getItemCount() {
        int count;

        if(this.moviesList != null && !this.moviesList.isEmpty()) {
            count = this.moviesList.size();
        }
        else {
            count = 0;
        }
        return count;
    }


    /**
     * Update {@link MoviesAdapter#moviesList} without rewrite the variable.
     * After of to update, launch event to notify {@link RecyclerView.Adapter#notifyDataSetChanged()}
     * of changes.
     * @param movies New list<code>MovieTO</code> to update.
     */
    public void putMovies(List<Movie> movies) {
        if (movies == null) { return; }
        this.moviesList.clear();
        this.moviesList.addAll(movies);
        notifyDataSetChanged();
    }

}
