package com.udacity.popularmovies.models;

import com.udacity.popularmovies.net.contracts.TO.PageResultReviewsTO;
import com.udacity.popularmovies.net.contracts.TO.ReviewTO;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PageResultReviewsTest {

    private PageResultReviewsTO given;
    private PageResultReviewsTO expected;
    private ReviewTO review;

    @Before
    public void setUp() throws Exception {

        review = new ReviewTO();
        review.setId("1");

        given = new PageResultReviewsTO();
        given.setPage(1);
        given.setTotalPages(10);
        given.setTotalResults(100);


        expected = new PageResultReviewsTO();
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
