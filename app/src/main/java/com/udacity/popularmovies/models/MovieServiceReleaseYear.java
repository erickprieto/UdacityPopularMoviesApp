package com.udacity.popularmovies.models;

/**
 * Wrap options of the API to search movies.
 * @see  <a href="https://developers.themoviedb.org/3/discover/movie-discover">API search year options</a>
 *
 * @author Erick Prieto
 * @since 2018
 */
public enum MovieServiceReleaseYear {

    YEAR_2016(2016),
    YEAR_2017(2017),
    YEAR_2018(2018);

    private int value;

    MovieServiceReleaseYear(int webServiceValue){
        this.value = webServiceValue;
    }

    public int getValue() {
        return value;
    }
}
