package com.udacity.popularmovies.utils;

import com.udacity.popularmovies.models.PageResultMovies;
import com.udacity.popularmovies.net.contracts.MovieServiceContract;

import retrofit2.Call;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for {@link ProxyHelper}.
 * @author Erick Prieto
 * @since 2018
 */
public class ProxyHelperTest {

    private String given;
    private String expected;
    private String endpoint;
    private MovieServiceContract api;
    private Call<PageResultMovies> call;

//    @Before
//    public void setUp() throws Exception {
//        given = "file.png";
//        expected = "http://image.tmdb.org/t/p/w185/file.png";
//
//        endpoint = "https://api.themoviedb.org/3/discover/movie?api_key=API_KEY&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
//
//        api = ProxyHelper.getProxy(MovieServiceContract.class);
//
//         call = api.getDiscoverMovies(
//                ProxyHelper.WEB_SERVICES_LICENSE
//                , MovieServiceLanguage.ENGLISH_US.getValue()
//                , MovieServiceSortBy.POPULARITY_DESC.getValue()
//                , false
//                , false
//                , MovieServiceReleaseYear.YEAR_2018.getValue()
//                , 1);
//    }
//
//    @Test
//    public void buildCompletePosterUrlValidator_ShouldReturnTrue() {
//        assertTrue("buildCompletePosterUrlValidator_ShouldReturnTrue the same url"
//                , expected.equals(ProxyHelper.buildCompletePosterUrl(given)));
//    }
//    @Test
//    public void getProxyValidator_ShouldReturnTrue() {
//        assertTrue("buildCompletePosterUrlValidator_ShouldReturnTrue the same url"
//                , endpoint.equals(call.request().url().toString()));
//    }
}
