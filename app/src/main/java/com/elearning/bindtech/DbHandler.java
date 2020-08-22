package com.elearning.bindtech;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHandler extends SQLiteOpenHelper {
    public static final String TABLE_ONBOARDING_SCREENS_SYNC = "table";
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;
    public static String TABLE_ONBOARDING_SCREENS = "onboarding_screens_data";
    private static Context context;
    private static DbHandler dbHandler;

    /**
     * Constructor
     *
     * @param context :Interface to global information about an application
     *                environment.
     */
    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database operations", "Database is created");
    }

    public static SQLiteDatabase getReadableDb(Context context) {
        return getInstance(context).getReadableDatabase();
    }

    public static SQLiteDatabase getWritableDb(Context context) {
        return getInstance(context).getWritableDatabase();
    }

    public static DbHandler getInstance(Context context2) {
        if (dbHandler == null) {
            context = context2;
            dbHandler = new DbHandler(context2);
        }
        return dbHandler;
    }

    public SQLiteDatabase getDBObject(int isWritable) {
        return (isWritable == 1) ? this.getWritableDatabase() : this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
