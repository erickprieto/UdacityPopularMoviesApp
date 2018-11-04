package com.udacity.popularmovies.net;

import com.udacity.popularmovies.net.contracts.TO.MovieTO;
import com.udacity.popularmovies.net.contracts.TO.PageResultMoviesTO;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for {@link PageResultMoviesTO} model.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class PageResultMoviesTOTest {

    private PageResultMoviesTO given;
    private PageResultMoviesTO expected;
    private MovieTO movie;

    @Before
    public void setUp() throws Exception {

        movie = new MovieTO();
        movie.setId(1);
        movie.setTitle("Venom");
        movie.setPosterPath("/file.png");
        movie.setOverview("I hope watch it..");
        movie.setReleaseDate("2018-01-01");
        movie.setVoteAverage(100.0);

        given = new PageResultMoviesTO();
        given.setPage(1);
        given.setTotalPages(10);
        given.setTotalResults(100);
        given.setMovies(new ArrayList<MovieTO>());
        given.getMovies().add(movie);

        expected = new PageResultMoviesTO();
        expected.setPage(1);
        expected.setTotalPages(10);
        expected.setTotalResults(100);
        expected.setMovies(new ArrayList<MovieTO>());
        expected.getMovies().add(movie);

    }

    @Test
    public void equalsValidator_ShouldReturnTrue() {
        assertTrue("equalsValidator_ShouldReturnTrue the same", expected.equals(given));
    }

    @Test
    public void equalsNullValidator_ShouldReturnFalse() {
        assertFalse("equalsNullValidator_ShouldReturnFalse the same", expected.equals(null));
    }
}
