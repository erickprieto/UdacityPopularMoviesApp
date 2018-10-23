package com.udacity.popularmovies.models;

import com.udacity.popularmovies.net.contracts.TO.PageResultVideosTO;
import com.udacity.popularmovies.net.contracts.TO.VideoTO;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PageResultVideosTest {
    private PageResultVideosTO given;
    private PageResultVideosTO expected;
    private VideoTO video;

    @Before
    public void setUp() throws Exception {

        video = new VideoTO();
        video.setId("2");

        given = new PageResultVideosTO();

        expected = new PageResultVideosTO();


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
