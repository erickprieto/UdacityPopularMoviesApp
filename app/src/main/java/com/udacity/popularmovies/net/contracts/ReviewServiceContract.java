package com.udacity.popularmovies.net.contracts;

import com.udacity.popularmovies.net.contracts.TO.PageResultReviewsTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Contract to download a list of Movies from The MovieTO DataBase API.
 * <a href="https://api.themoviedb.org/3">https://api.themoviedb.org/3/</a>
 * <p>
 * These Endpoints download a JSON object {@link PageResultReviewsTO} that contains
 * a List of {@link com.udacity.popularmovies.net.contracts.TO.ReviewTO}.
 *
 * <p>See <a href="https://developers.themoviedb.org/3/getting-started/introduction">API</a></p>.
 *
 * @author Erick Prieto
 * @since 2018
 */
public interface ReviewServiceContract {

    /**
     * <p>
     * Get the user reviews for a movie.
     * </p>
     * @param idMovie unique movie identifier.
     * @param apiKey <code>String</code> Mandatory to receive data, this key is provided by tmdb.
     * @param movieServiceLanguage String <code>MovieServiceLenguage</code>
     *      *                             Specify a language to query translatable fields with.
     * @return <code>PageResultReviewsTO</code> details of response with inside a List of
     * {@link com.udacity.popularmovies.net.contracts.TO.ReviewTO}
     */
    @GET("movie/{idMovie}/reviews")
    Call<PageResultReviewsTO> getReviews(@Path("idMovie") int idMovie
            , @Query("api_key") String apiKey
            , @Query("language") String movieServiceLanguage
    );
}
