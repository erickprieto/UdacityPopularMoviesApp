package com.udacity.popularmovies.net;

import com.udacity.popularmovies.net.contracts.TO.VideoTO;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for {@link VideoTO} model.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class VideoTOTest {
    private VideoTO given;
    private VideoTO expected;

    @Before
    public void setUp() throws Exception {
        given = new VideoTO();
        expected = new VideoTO();
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
