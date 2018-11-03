package com.udacity.popularmovies.net.contracts;

import com.udacity.popularmovies.net.contracts.TO.PageResultVideosTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public interface VideoServiceContract {

    @GET("movie/{idMovie}/videos")
    Call<PageResultVideosTO> getVideos(@Path("idMovie") int idMovie
            , @Query("api_key") String apiKey
            , @Query("language") String movieServiceLanguage
    );
}
