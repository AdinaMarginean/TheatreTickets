package com.example.adina.theatretickets.Utils;

import android.arch.persistence.room.TypeConverter;

import java.sql.Date;

public class Converters {
    @TypeConverter
    public static Date toDate(Long value){
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
