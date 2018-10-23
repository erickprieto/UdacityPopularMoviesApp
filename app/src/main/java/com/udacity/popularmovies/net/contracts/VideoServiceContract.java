package com.udacity.popularmovies.net.contracts;

import com.udacity.popularmovies.net.contracts.TO.PageResultVideosTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VideoServiceContract {

    @GET("movie/{idMovie}/videos")
    Call<PageResultVideosTO> getVideos(@Query("api_key") String apiKey
            , @Path("idMovie") int idMovie
            , @Query("language") String movieServiceLanguage
    );
}
