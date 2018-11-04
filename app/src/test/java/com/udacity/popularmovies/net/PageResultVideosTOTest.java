package com.udacity.popularmovies.net;

import com.udacity.popularmovies.net.contracts.TO.PageResultVideosTO;
import com.udacity.popularmovies.net.contracts.TO.VideoTO;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for {@link PageResultVideosTO} model.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class PageResultVideosTOTest {
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
        assertFalse("equalsNullValidator_ShouldReturnFalse the same", expected.equals(null));
    }
}
