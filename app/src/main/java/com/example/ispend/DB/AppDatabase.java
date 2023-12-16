package com.example.ispend.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ispend.DB.typeConverters.DateTypeConverter;
import com.example.ispend.User;

@Database(entities = {User.class}, version = 2)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase{
    public static final String DB_NAME = "ISPEND_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";

    private static volatile AppDatabase instance;
    public static final Object LOCK = new Object();

    public abstract iSpendDAO getiSpendDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, AppDatabase.DB_NAME).build();
                }
            }
        }
        return instance;
    }
}
