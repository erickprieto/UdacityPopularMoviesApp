package com.udacity.popularmovies.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for {@link Review} model.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class ReviewTest {

    private Review given;
    private Review expected;

    @Before
    public void setUp() throws Exception {

        given = new Review();
        given.setId("1");

        expected = new Review();
        expected.setId("1");


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
