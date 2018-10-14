package com.udacity.popularmovies.models;

/**
 * Wrap options of the API to search movies.
 * @see  <a href="https://developers.themoviedb.org/3/discover/movie-discover">API search sortby options</a>
 * @author Erick Prieto
 * @since 2018
 */
public enum MovieServiceSortBy {

    POPULARITY_ASC("popularity.asc"),
    POPULARITY_DESC ("popularity.desc"),
    VOTE_AVERAGE_ASC ("vote_average.asc"),
    VOTE_AVERAGE_DESC ("vote_average.desc"),
    VOTE_COUNT_ASC("vote_count.asc"),
    VOTE_COUNT_DESC("vote_count.desc");

    private String value;

    MovieServiceSortBy(String webServiceValue){
        this.value = webServiceValue;
    }

    public String getValue() {
        return this.value;
    }
}
