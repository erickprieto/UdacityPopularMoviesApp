package com.udacity.popularmovies.events;

public class EstablishedMovieFavoriteEvent {

    private boolean movieFavorite = false;

    public EstablishedMovieFavoriteEvent(boolean movieFavorite) {
        this.movieFavorite = movieFavorite;
    }

    public boolean isMovieFavorite() {
        return movieFavorite;
    }

    public void setMovieFavorite(boolean movieFavorite) {
        this.movieFavorite = movieFavorite;
    }
}
