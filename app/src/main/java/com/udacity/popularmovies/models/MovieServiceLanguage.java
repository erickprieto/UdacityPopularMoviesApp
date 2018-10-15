package com.udacity.popularmovies.models;

/**
 * Wrap options of the API to search movies.
 * <p>
 *     Pass a ISO 639-1 value to display translated data for the fields that support it
 *     the fields that support it.
 *     <li>minLength: 2</li>
 *     <li>pattern: ([a-z]{2})-([A-Z]{2})</li>
 *     <li>default: en-US</li>
 * </p>
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
