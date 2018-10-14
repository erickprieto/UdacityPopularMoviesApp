package com.udacity.popularmovies.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PageResultTest {

    private PageResult given;
    private PageResult expected;
    private Movie movie;

    @Before
    public void setUp() throws Exception {

        movie = new Movie();
        movie.setId(1);
        movie.setTitle("Venom");
        movie.setAdult(false);
        movie.setPosterPath("/file.png");
        movie.setOverview("I hope watch it..");
        movie.setPopularity(23.2);
        movie.setReleaseDate("2018-01-01");
        movie.setVideo(false);
        movie.setVoteAverage(100.0);

        given = new PageResult();
        given.setPage(1);
        given.setTotalPages(10);
        given.setTotalResults(100);
        given.setMovies(new ArrayList<Movie>());
        given.getMovies().add(movie);

        expected = new PageResult();
        expected.setPage(1);
        expected.setTotalPages(10);
        expected.setTotalResults(100);
        expected.setMovies(new ArrayList<Movie>());
        expected.getMovies().add(movie);

    }

    @Test
    public void equalsValidator_ShouldReturnTrue() {
        assertTrue("equalsValidator_ShouldReturnTrue the same", expected.equals(given));
    }

    @Test
    public void equalsNullValidator_ShouldReturnFalse() {
        assertFalse("equalsValidator_ShouldReturnTrue the same", expected.equals(null));
    }
}
