package com.example.airlineticketreservationsystem.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.airlineticketreservationsystem.DB.typeconverter.DateTypeConverter;
import com.example.airlineticketreservationsystem.User;

@Database(entities = {User.class}, version=1)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DBNAME ="db-airline";

    public static final String USER_TABLE = "user";

    public abstract UserDAO getUserDAO();
}
