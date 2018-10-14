package com.udacity.popularmovies.models;

/**
 * Wrap options of the API to search movies.
 * @see  <a href="https://developers.themoviedb.org/3/discover/movie-discover">API search value options</a>
 * @author Erick Prieto
 * @since 2018
 */
public enum MovieServiceLanguage {

    ENGLISH_US ("en-US");


    private final String value;

    MovieServiceLanguage(String webServiceValue){
        this.value = webServiceValue;
    }

    public String getValue() {
        return value;
    }

}
