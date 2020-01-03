package com.example.runningapp.database;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.sql.Time;

public class Converter {

        @TypeConverter
        public static Time toTime(Long dateLong){
            return dateLong == null ? null: new Time(dateLong);
        }

        @TypeConverter
        public static long fromTime(Time date){
            return date == null ? null :date.getTime();
        }

        @TypeConverter
        public long convertDateToLong(Date date) {
            return date.getTime();
        }

        @TypeConverter
        public Date convertLongToDate(long time) {
            return new Date(time);
        }
    }
