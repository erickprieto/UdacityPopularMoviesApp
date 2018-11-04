package com.udacity.popularmovies.database;

import com.udacity.popularmovies.database.entities.FavoriteEntity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for {@link com.udacity.popularmovies.database.entities.FavoriteEntity} model.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class FavoriteEntityTest {

    private FavoriteEntity given;
    private FavoriteEntity expected;

    @Before
    public void setUp() throws Exception {

        given = new FavoriteEntity();
        given.setId(1);
        given.setFavorite(1);


        expected = new FavoriteEntity();
        expected.setId(1);
        expected.setFavorite(1);

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
