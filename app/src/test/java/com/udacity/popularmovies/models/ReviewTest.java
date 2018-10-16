package com.udacity.popularmovies.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReviewTest {

    private Review given;
    private Review expected;

    @Before
    public void setUp() throws Exception {

        expected = new Review();
        given = new Review();
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
