package com.udacity.popularmovies.net.contracts;

import com.udacity.popularmovies.net.contracts.TO.PageResultMoviesTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Contract to download a list of Movies from The MovieTO DataBase API.
 * <a href="https://api.themoviedb.org/3">https://api.themoviedb.org/3/</a>
 * <p>
 * These Endpoints download a JSON object {@link PageResultMoviesTO} that contains
 * a List of {@link com.udacity.popularmovies.models.Movie}.
 * Example of:
 * {@link MovieServiceContract#getDiscoverMovies(String, String, String, boolean, boolean, int, int)}
 * requests the endpoint: https://api.themoviedb.org/3/discover/movie
 * ?api_key=API_KEY&language=en-US&sort_by=popularity.desc
 * &include_adult=false&include_video=false&page=1
 * </p>
 * @author Erick Prieto
 * @since 2018
 */
public interface MovieServiceContract {

    /**
     * <p>Discover movies by different types of data like average rating,
     * number of votes, genres and certifications.
     * </p>
     * Requests the endpoint: https://api.themoviedb.org/3/discover/movie
     * ?api_key=API_KEY&language=en-US&sort_by=popularity.desc
     * &include_adult=false&include_video=false&page=1
     * @param apiKey <code>String</code> Mandatory to receive data, this key is provided by tmdb.
     * @param movieServiceLanguage String <code>MovieServiceLenguage</code>
     *                             Specify a language to query translatable fields with.
     * @param movieServiceSortBy String <code>MovieServiceSortBy</code>
     *                           Choose from one of the many available sort options.
     * @param adult <code>boolean</code> A filter and include or exclude adult movies.
     * @param video <code>boolean</code> A filter to include or exclude videos.
     * @param movieServiceReleaseYear <code>int</code> A filter to limit the results to a specific
     *                                primary release year.
     * @param page <code>int</code> Specify the page of results to query.
     *             <li>minimum: 1</li>
     *             <li>maximum: 1000</li>
     *             <li>default: 1</li>
     * @return <code>PageResultMoviesTO</code> details of response with inside a List of
     * {@link com.udacity.popularmovies.models.Movie}
     */
    @GET("discover/movie")
    Call<PageResultMoviesTO> getDiscoverMovies(@Query("api_key") String apiKey
            , @Query("language") String movieServiceLanguage
            , @Query("sort_by") String movieServiceSortBy
            , @Query("include_adult") boolean adult
            , @Query("include_video") boolean video
            , @Query("primary_release_year") int movieServiceReleaseYear
            , @Query("page") int page
    );

    /**
     * <p>Get a list of the current popular movies on TMDb. This list updates daily.</p>
     * Requests the endpoint: https://developers.themoviedb.org/3/movie/popular?api_key=API_KEY&language=en-US
     * @param apiKey <code>String</code> Mandatory to receive data, this key is provided by tmdb.
     * @param movieServiceLanguage String <code>MovieServiceLenguage</code>
     *                             Specify a language to query translatable fields with.
     * @param page <code>int</code> Specify the page of results to query.
     *             <li>minimum: 1</li>
     *             <li>maximum: 1000</li>
     *             <li>default: 1</li>
     * @return <code>PageResultMoviesTO</code> details of response with inside a List of
     * {@link com.udacity.popularmovies.models.Movie}
     */
    @GET("movie/popular")
    Call<PageResultMoviesTO> getPopularMovies(@Query("api_key") String apiKey
            , @Query("language") String movieServiceLanguage
            , @Query("page") int page
    );

    /**
     * <p>Get the top rated movies on TMDb.</p>
     * Requests the endpoint: https://developers.themoviedb.org/3/movie/top_rated?api_key=API_KEY&language=en-US
     * @param apiKey <code>String</code> Mandatory to receive data, this key is provided by tmdb.
     * @param movieServiceLanguage String <code>MovieServiceLenguage</code>
     *                             Specify a language to query translatable fields with.
     * @param page <code>int</code> Specify the page of results to query.
     *             <li>minimum: 1</li>
     *             <li>maximum: 1000</li>
     *             <li>default: 1</li>
     * @return <code>PageResultMoviesTO</code> details of response with inside a List of
     * {@link com.udacity.popularmovies.models.Movie}
     */
    @GET("movie/top_rated")
    Call<PageResultMoviesTO> getTopRatedMovies(@Query("api_key") String apiKey
            , @Query("language") String movieServiceLanguage
            , @Query("page") int page
    );

}
