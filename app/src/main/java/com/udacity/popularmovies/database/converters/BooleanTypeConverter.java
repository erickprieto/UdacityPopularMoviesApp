package com.udacity.popularmovies.database.converters;

import android.arch.persistence.room.TypeConverter;

public class BooleanTypeConverter {

    @TypeConverter
    public Boolean toBoolean(Integer value) {
        if (value == 1) {
            return true;
        } else {
            return false;
        }
    }

    @TypeConverter
    public Integer toInt(Boolean bool) {
        if (bool) {
            return 1;
        } else {
            return 0;
        }
    }
}
