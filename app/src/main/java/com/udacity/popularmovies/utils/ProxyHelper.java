package com.udacity.popularmovies.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udacity.popularmovies.PopularMoviesApplication;
import com.udacity.popularmovies.models.Movie;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>
 * Utility Class to download Webservice REST resources. This class provides KEY values to
 * read from configuration file, that file contains parameters to access to The Movie DataBase API.
 * </p>
 * <p>
 * example of The Movie DataBase endpoint.
 * https://api.themoviedb.org/3/discover/movie?api_key=dcbbd07d3034bb7d1b81ac56ded12b65
 * &language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1
 * </p>
 *
 * @author Erick Prieto
 * @since 2018
 */
public class ProxyHelper {

    /**
     * Base URL of WebServices.
     */
    private static final String KEY_WEB_SERVICES_URL_BASE = "web_services_url_base";

    /**
     * Time to wait before to launch a Connection TimeOut.
     */
    private static final String KEY_WEB_SERVICES_CONNECT_TIMEOUT = "web_services_timeout";

    /**
     * Time to wait before to launch a Read Response Connection TimeOut.
     */
    private static final String KEY_WEB_SERVICES_READ_TIMEOUT = "web_services_read_timeout";

    /**
     * Complete URL of {@link Movie} Model to download Image Poster of movie.
     */
    private static final String KEY_POSTER_URL_BASE = "web_services_image_url_base";

    /**
     * License Key to get information from webservice.
     */
    private static final String KEY_WEB_SERVICES = "web_services_key";

    /**
     * Base URL of WebServices.
     */
    private static final String WEB_SERVICES_PATH =
            PopularMoviesApplication.getAtributoConfiguracion(KEY_WEB_SERVICES_URL_BASE);

    /**
     * Time to wait before to launch a Connection TimeOut.
     */
    private static final Long WEB_SERVICES_CONNECT_TIMEOUT = Long.parseLong(
            PopularMoviesApplication.getAtributoConfiguracion(KEY_WEB_SERVICES_CONNECT_TIMEOUT));

    /**
     * Time to wait before to launch a Read Response Connection TimeOut.
     */
    private static final Long WEB_SERVICES_READ_TIMEOUT = Long.parseLong(
            PopularMoviesApplication.getAtributoConfiguracion(KEY_WEB_SERVICES_READ_TIMEOUT));

    /**
     * Complete URL of {@link Movie} Model to download Image Poster of movie.
     */
    private static final String WEB_SERVICES_POSTER_URL_BASE =
            PopularMoviesApplication.getAtributoConfiguracion(KEY_POSTER_URL_BASE);

    /**
     * License Key to get information from webservice The Movie DataBase API.
     */
    public static final String WEB_SERVICES_LICENSE =
            PopularMoviesApplication.getAtributoConfiguracion(KEY_WEB_SERVICES);

    /**
     * Only provide Utility methods.
     */
    private ProxyHelper(){}

    /**
     * Create a {@link Gson} Converter, to convert from JSON to
     * {@link com.udacity.popularmovies.models}.
     * @return <code>Gson</code> object to convert.
     */
    private static final Gson getConfiguracionGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    /**
     * Provide Retrofit configuration to consume WebService REST endpoint.
     * @param resourceContract Class of Contract of WebService Endpoints.
     * @param <T> Contract of WebService Endpoints.
     * @return <T> WebService Endpoint like a <code>model</code>
     */
    public static final <T> T getProxy(Class <T> resourceContract){
        Gson gson = getConfiguracionGson();

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(WEB_SERVICES_PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client( new OkHttpClient
                        .Builder()
                        .connectTimeout(WEB_SERVICES_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                        .readTimeout( WEB_SERVICES_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                        .build())
                .build();

        return restAdapter.create(resourceContract);
    }

    /**
     * To build a complete URL to download poster of movie.
     * It take base url from configuration file.
     * It's not validated like {@link java.net.URI} resource.
     * @param fileName {@link Movie#getPosterPath()} with only filename.
     * @return <code>String</code> with all path to download @param file.
     */
    public static final String buildCompletePosterUrl(String fileName) {
        if(StringUtils.isEmpty(fileName)) { return null; }
        return WEB_SERVICES_POSTER_URL_BASE + fileName;
    }
}
