package com.udacity.popularmovies.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit Test for {@link Movie} model, that works as model and transfer object TO.
 * @author Erick Prieto
 * @since 2018
 */
public class MovieTest {

    private Movie given;
    private Movie expected;

    @Before
    public void setUp() throws Exception {

        given = new Movie();
        given.setId(1);
        given.setTitle("Venom");
        given.setAdult(false);
        given.setPosterPath("/file.png");
        given.setOverview("I hope watch it..");
        given.setPopularity(23.2);
        given.setReleaseDate("2018-01-01");
        given.setVideo(false);
        given.setVoteAverage(100.0);
        expected = new Movie();
        expected.setId(1);
        expected.setTitle("Venom");
        expected.setAdult(false);
        expected.setPosterPath("/file.png");
        expected.setOverview("I hope watch it..");
        expected.setPopularity(23.2);
        expected.setReleaseDate("2018-01-01");
        expected.setVideo(false);
        expected.setVoteAverage(100.0);


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
