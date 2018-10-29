package com.udacity.popularmovies.events;


import com.udacity.popularmovies.models.Movie;

import java.util.List;

public class DownloadMovieListEvent {

    private boolean popularMovie;
    private List<Movie> modelos;

    public DownloadMovieListEvent(List<Movie> modelos, boolean popularMovie) {
        this.modelos = modelos;
        this.popularMovie = popularMovie;
    }

    public List<Movie> getModelos() {
        return modelos;
    }

    public void setModelos(List<Movie> modelos) {
        this.modelos = modelos;
    }

    public boolean isPopularMovie() {
        return popularMovie;
    }

    public void setPopularMovie(boolean popularMovie) {
        this.popularMovie = popularMovie;
    }
}
