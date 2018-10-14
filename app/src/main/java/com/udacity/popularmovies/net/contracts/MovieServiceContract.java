package com.udacity.popularmovies.net.contracts;

import com.udacity.popularmovies.models.PageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Contract to download a list of Movies from The Movie DataBase API.
 * <p>
 * This Endpoint download a JSON object {@link PageResult} that contains
 * a List of {@link com.udacity.popularmovies.models.Movie}.
 * Example of endpoint: https://api.themoviedb.org/3/discover/movie
 * ?api_key=API_KEY&language=en-US&sort_by=popularity.desc
 * &include_adult=false&include_video=false&page=1
 * </p>
 * @author Erick Prieto
 * @since 2018
 */
public interface MovieServiceContract {

    @GET("discover/movie")
    Call<PageResult> getMovies(@Query("api_key") String apiKey
            , @Query("language") String movieServiceLanguage
            , @Query("sort_by") String movieServiceSortBy
            , @Query("include_adult") boolean adult
            , @Query("include_video") boolean video
            , @Query("primary_release_year") int movieServiceReleaseYear
            , @Query("page") int page
    );
}
