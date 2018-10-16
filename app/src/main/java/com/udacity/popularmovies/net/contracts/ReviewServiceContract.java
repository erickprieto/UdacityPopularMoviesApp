package com.udacity.popularmovies.net.contracts;

import com.udacity.popularmovies.models.PageResultReviews;
import com.udacity.popularmovies.models.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewServiceContract {

    @GET("movie/{idMovie}/reviews")
    Call<PageResultReviews> getReviews(@Query("api_key") String apiKey
            , @Path("idMovie") int idMovie
            , @Query("language") String movieServiceLanguage
    );
}
