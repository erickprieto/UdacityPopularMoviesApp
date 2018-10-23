package com.udacity.popularmovies.net.contracts;

import com.udacity.popularmovies.net.contracts.TO.PageResultReviewsTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewServiceContract {

    @GET("movie/{idMovie}/reviews")
    Call<PageResultReviewsTO> getReviews(@Query("api_key") String apiKey
            , @Path("idMovie") int idMovie
            , @Query("language") String movieServiceLanguage
    );
}
