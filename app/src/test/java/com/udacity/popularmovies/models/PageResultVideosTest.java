package com.udacity.popularmovies.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PageResultVideosTest {
    private PageResultVideos given;
    private PageResultVideos expected;
    private Video video;

    @Before
    public void setUp() throws Exception {

        video = new Video();
        video.setId("2");

        given = new PageResultVideos();

        expected = new PageResultVideos();


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
