package com.udacity.popularmovies.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.graphics.Bitmap;

import com.udacity.popularmovies.database.entities.MovieEntity;

import java.util.List;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie ORDER by id ASC")
    LiveData<List<MovieEntity>> getAll();

    @Query("SELECT movie.id, title, overview, releaseDate" +
            ", rating, poster_file, poster_jpg" +
            ", popular, favorite.favorite, created FROM movie LEFT JOIN favorite ON movie.id = favorite.id WHERE popular = 1 ORDER bY movie.id ASC;")
    LiveData<List<MovieEntity>> getPopularMovies();

    @Query("SELECT movie.id, title, overview, releaseDate" +
            ", rating, poster_file, poster_jpg" +
            ", popular, favorite.favorite, created FROM movie LEFT JOIN favorite ON movie.id = favorite.id WHERE popular = 0 ORDER bY movie.id ASC;")
    LiveData<List<MovieEntity>> getTopRatedMovies();

    @Query("SELECT movie.id, title, overview, releaseDate" +
            ", rating, poster_file, poster_jpg" +
            ", popular, favorite.favorite, created FROM movie INNER JOIN favorite ON movie.id = favorite.id ORDER bY movie.id ASC;")
    LiveData<List<MovieEntity>> getFavoriteMovies();

    @Query("SELECT * FROM movie WHERE id IN (:ids)")
    LiveData<List<MovieEntity>> loadAllByIds(int[] ids);

    @Query("SELECT * FROM movie WHERE title LIKE :title AND "
            + "overview LIKE :over LIMIT 1")
    LiveData<MovieEntity> findByName(String title, String over);

    @Query("UPDATE movie SET poster_jpg = :poster WHERE id = :id")
    int updateBitmap(int id, byte[] poster);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieEntity... movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntity movie);

    @Delete
    void delete(MovieEntity movie);
}
