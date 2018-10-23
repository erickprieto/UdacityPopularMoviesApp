package com.udacity.popularmovies.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.activities.DetailActivity;
import com.udacity.popularmovies.adapters.viewholders.MovieViewHolder;
import com.udacity.popularmovies.models.Movie;
import com.udacity.popularmovies.utils.ProxyHelper;

import java.util.List;

/**
 * Adapter to fill <code>RecyclerView</code>
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

    /**
     * Default Constructor with Data to fill this adapter.
     * @param movies
     */
    public MoviesAdapter(List<Movie> movies) {
        this.moviesList = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View movieItemView = inflater.inflate(R.layout.activitymain_listitem, parent, false);

        MovieViewHolder viewHolder = new MovieViewHolder(movieItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        final Movie movie = moviesList.get(position);

        Picasso.with(holder.itemView.getContext())
                .load(ProxyHelper.buildCompletePosterUrl(movie.getPosterPath()))
                .error(android.support.v7.appcompat.R.drawable.notification_bg_low_pressed)
                .into(holder.getPosterImageView());

        final Context context = holder.getPosterImageView().getContext();

        holder.getPosterImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                Log.d(TAG, movie.toString());
                intent.putExtra(DetailActivity.ID_EXTRA_MOVIE_DETAIL, movie);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        int count ;

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
        this.moviesList.clear();
        this.moviesList.addAll(movies);
        notifyDataSetChanged();

    }

}
