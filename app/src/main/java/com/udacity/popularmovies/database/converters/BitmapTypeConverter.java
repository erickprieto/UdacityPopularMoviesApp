package com.udacity.popularmovies.database.converters;

import android.arch.persistence.room.TypeConverter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class BitmapTypeConverter {

    private static final int DEFAULT_OFFSET = 0;

    /**
     * (small size) 0 - 100 (max quality)
     */
    private static final int JPEG_QUALITY = 100;

    @TypeConverter
    public Bitmap toBitmap(byte[] value) {
        if(value == null) { return null; }
        return BitmapFactory.decodeByteArray(value, DEFAULT_OFFSET, value.length);
    }

    @TypeConverter
    public byte[] toByteArray(Bitmap bitmap) {
        if(bitmap == null) { return null; }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, stream);
        return stream.toByteArray();
    }

}
