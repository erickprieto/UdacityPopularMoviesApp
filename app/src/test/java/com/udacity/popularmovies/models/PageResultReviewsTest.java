package com.udacity.popularmovies.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PageResultReviewsTest {

    private PageResultReviews given;
    private PageResultReviews expected;
    private Review review;

    @Before
    public void setUp() throws Exception {

        review = new Review();
        review.setId("1");

        given = new PageResultReviews();
        given.setPage(1);
        given.setTotalPages(10);
        given.setTotalResults(100);


        expected = new PageResultReviews();
        expected.setPage(1);
        expected.setTotalPages(10);
        expected.setTotalResults(100);


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
