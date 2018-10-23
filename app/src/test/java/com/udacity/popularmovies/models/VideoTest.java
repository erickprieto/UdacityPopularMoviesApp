package com.udacity.popularmovies.models;

import com.udacity.popularmovies.net.contracts.TO.VideoTO;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VideoTest {
    private VideoTO given;
    private VideoTO expected;

    @Test
    public void equalsValidator_ShouldReturnTrue() {
        assertTrue("equalsValidator_ShouldReturnTrue the same", expected.equals(given));
    }

    @Test
    public void equalsNullValidator_ShouldReturnFalse() {

        assertFalse("equalsValidator_ShouldReturnTrue the same", expected.equals(null));
    }
}
