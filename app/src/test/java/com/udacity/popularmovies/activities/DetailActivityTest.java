package com.udacity.popularmovies.activities;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

public class DetailActivityTest {

    private Object methodTest;
    private Double givenValue;
    private String expectedValue;

    @Before
    public void setUp() throws Exception {
        Method method = DetailActivity.class.getDeclaredMethod("formatVoteAverage", Double.class);
        method.setAccessible(true);
        givenValue = 2.0;
        methodTest = method.invoke(DetailActivity.class.newInstance(), givenValue);
        expectedValue = "2.0/10";

    }

    @Test
    public void formatVoteAverageTest_ShouldReturnTrue() {
        assertTrue("formatVoteAverage", expectedValue.equals((String) methodTest));
    }
}
