package com.udacity.popularmovies.net;

import android.util.Log;

import com.udacity.popularmovies.net.contracts.TO.ModelPageTO;
import com.udacity.popularmovies.net.contracts.TO.ModelTO;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackRetrofitPopularMovies<S extends ModelPageTO, T extends ModelTO> implements Callback<S> {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = CallbackRetrofitPopularMovies.class.getSimpleName();

    private String tag;
    private String tagM;
    private boolean isPopularMovie = false;

    private S modelPageTO;
    private T modelTO;


    private CallbackRetrofitPopularMovies(String tag, String tagM, boolean isPopularMovie) {

        this.tag = tag;
        this.tagM = tagM;

        //processDownloadedMovies(MovieTO.toListModel());
    }


    public abstract void onResponseValid(S modelPageTO, boolean isPopularMovie);

    @Override
    public void onResponse(Call<S> call, Response<S> response) {

        try {
            if(response.isSuccessful()) {
                S pageWithMoviesList = response.body();
                Log.v(tag, tagM + pageWithMoviesList.toString());
                //onResponseValid(pageWithMoviesList.getMovies(), isPopularMovie );


            } else {
                Log.e(tag,tagM + response.errorBody().toString());
            }
        }catch (Exception ex) {
            Log.e(tag,tagM + call.toString() + ex.getMessage());
        }
    }


    @Override
    public void onFailure(Call<S> call, Throwable t) {
        Log.e(tag,tagM + call.toString() + t.getMessage());
    }

}
