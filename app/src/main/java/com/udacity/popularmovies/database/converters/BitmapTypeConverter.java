package com.udacity.popularmovies.database.converters;

import android.arch.persistence.room.TypeConverter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class BitmapTypeConverter {

    @TypeConverter
    public Bitmap toBitmap(byte[] value) {
        if(value == null) { return null; }
        return BitmapFactory.decodeByteArray(value, 0, value.length);
    }

    @TypeConverter
    public byte[] toByteArray(Bitmap bitmap) {
        if(bitmap == null) { return null; }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }

}
