package com.udacity.popularmovies.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.udacity.popularmovies.database.entities.MovieEntity;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<MovieEntity>> getAll();

    @Query("SELECT * FROM movie WHERE id IN (:ids)")
    LiveData<List<MovieEntity>> loadAllByIds(int[] ids);

    @Query("SELECT * FROM movie WHERE title LIKE :title AND "
            + "overview LIKE :over LIMIT 1")
    LiveData<MovieEntity> findByName(String title, String over);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieEntity... movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntity movie);

    @Delete
    void delete(MovieEntity movie);
}
