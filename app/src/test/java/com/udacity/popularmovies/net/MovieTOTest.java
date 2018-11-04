package com.udacity.popularmovies.net;

import com.udacity.popularmovies.net.contracts.TO.MovieTO;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for {@link MovieTO} model.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class MovieTOTest {

    private MovieTO given;
    private MovieTO expected;

    @Before
    public void setUp() throws Exception {

        given = new MovieTO();
        given.setId(1);
        given.setTitle("Venom");
        given.setPosterPath("/file.png");
        given.setOverview("I hope watch it..");
        given.setReleaseDate("2018-01-01");
        given.setVoteAverage(100.0);

        expected = new MovieTO();
        expected.setId(1);
        expected.setTitle("Venom");
        expected.setPosterPath("/file.png");
        expected.setOverview("I hope watch it..");
        expected.setReleaseDate("2018-01-01");
        expected.setVoteAverage(100.0);

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
