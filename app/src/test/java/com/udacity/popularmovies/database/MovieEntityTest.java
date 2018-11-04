package com.udacity.popularmovies.database;

import com.udacity.popularmovies.database.entities.MovieEntity;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for {@link com.udacity.popularmovies.database.entities.MovieEntity} model.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class MovieEntityTest {

    private MovieEntity given;
    private MovieEntity expected;

    @Before
    public void setUp() throws Exception {

        given = new MovieEntity();
        given.setId(1);
        given.setTitle("Venom");
        given.setPosterFileName("/file.png");
        given.setOverview("I hope watch it..");
        given.setReleaseDate(Date.valueOf("2018-01-01"));
        given.setRating(100.0);

        expected = new MovieEntity();
        expected.setId(1);
        expected.setTitle("Venom");
        expected.setPosterFileName("/file.png");
        expected.setOverview("I hope watch it..");
        expected.setReleaseDate(Date.valueOf("2018-01-01"));
        expected.setRating(100.0);

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
