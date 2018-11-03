package com.udacity.popularmovies.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.udacity.popularmovies.database.entities.FavoriteEntity;

import java.util.List;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    LiveData<List<FavoriteEntity>> getAll();

    @Query("SELECT * FROM favorite WHERE id = :id LIMIT 1")
    FavoriteEntity isFavorite(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(FavoriteEntity[] favorites);

//NOT YET Room lib
//    @Query("INSERT INTO favorite VALUES (:ids)")
//    void insertAll(int[] ids);
//
//    @Query("INSERT INTO favorite VALUES (:id)")
//    void insert(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteEntity favorite);

    @Delete
    void delete(FavoriteEntity favorite);

    @Query("DELETE FROM favorite WHERE id = :ids")
    void delete(int[] ids);

    @Query("DELETE FROM favorite WHERE id = :id")
    void delete(int id);

}
