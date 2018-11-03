package com.udacity.popularmovies.events;

public class SetFavoriteMovieEvent {

    private int movieId;
    private boolean movieFavorite = false;

    public SetFavoriteMovieEvent(int movieId, boolean movieFavorite) {
        this.movieId = movieId;
        this.movieFavorite = movieFavorite;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public boolean isMovieFavorite() {
        return movieFavorite;
    }

    public void setMovieFavorite(boolean movieFavorite) {
        this.movieFavorite = movieFavorite;
    }
}
