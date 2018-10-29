package com.udacity.popularmovies.events;


import com.udacity.popularmovies.models.Movie;

import java.util.List;

public class DownloadDiscoveryMovieListEvent {

    private List<Movie> modelos;

    public DownloadDiscoveryMovieListEvent(List<Movie> modelos) {
        this.modelos = modelos;
    }

    public List<Movie> getModelos() {
        return modelos;
    }

    public void setModelos(List<Movie> modelos) {
        this.modelos = modelos;
    }
}
