package com.udacity.popularmovies.database.converters;

import android.arch.persistence.room.TypeConverter;

public class PseudoBooleanTypeConverter {

    @TypeConverter
    public int toPseudoBoolean(int value) {
        return  value == 1 ? 1 : 0;
    }

}
