package com.udacity.popularmovies.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UrlValidatorTest {


    private String expected;
    private String given;
    private String givenOK;
    @Before
    public void setUp() throws Exception {
        expected = "http://www.youtube.com/watch?v=ZJDMWVZta3M";
        given = "www.youtube.com/watch?v=ZJDMWVZta3M";
        givenOK = "http://www.youtube.com/watch?v=ZJDMWVZta3M";
    }
    @Test
    public void equalsValidator_ShouldReturnTrue() {
        assertTrue("equalsValidator_ShouldReturnTrue the same", expected.equals(UrlValidator.validate(given)));
    }

    @Test
    public void equalsNullValidator_ShouldReturnFalse() {

        assertFalse("equalsNullValidator_ShouldReturnFalse the same", expected.equals(UrlValidator.validate(null)));
    }

    @Test
    public void equalsEmptyValidator_ShouldReturnFalse() {

        assertFalse("equalsEmptyValidator_ShouldReturnFalse the same", expected.equals(UrlValidator.validate(StringUtils.EMPTY)));
    }

    @Test
    public void equalsSameValidator_ShouldReturnTrue() {

        assertTrue("equalsSameValidator_ShouldReturnTrue the same", expected.equals(UrlValidator.validate(givenOK)));
    }
}
