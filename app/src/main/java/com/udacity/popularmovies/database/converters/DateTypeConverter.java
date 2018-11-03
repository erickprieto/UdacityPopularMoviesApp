package com.udacity.popularmovies.database.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class  DateTypeConverter {

    @TypeConverter
    public Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long toTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}
