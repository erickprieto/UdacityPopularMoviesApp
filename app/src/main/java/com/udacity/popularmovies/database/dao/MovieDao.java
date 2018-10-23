package com.udacity.popularmovies.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.udacity.popularmovies.database.entities.MovieEntity;
import com.udacity.popularmovies.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    List<MovieEntity> getAll();

    @Query("SELECT * FROM movie WHERE id IN (:ids)")
    List<MovieEntity> loadAllByIds(int[] ids);

    @Query("SELECT * FROM movie WHERE title LIKE :first AND "
            + "overview LIKE :last LIMIT 1")
    MovieEntity findByName(String first, String last);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieEntity... movies);

    @Delete
    void delete(MovieEntity movie);
}
