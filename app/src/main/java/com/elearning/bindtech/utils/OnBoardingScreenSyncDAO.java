package com.elearning.bindtech.utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;


import com.elearning.bindtech.DbHandler;
import com.elearning.bindtech.interfaces.DAO;
import com.elearning.bindtech.interfaces.DTO;

import java.util.List;

public class OnBoardingScreenSyncDAO implements DAO {
    private static OnBoardingScreenSyncDAO onBoardingScreensSyncDAO;
    private final String TAG = "OnBoardingSync";

    public static OnBoardingScreenSyncDAO getInstance() {
        if (onBoardingScreensSyncDAO == null) {
            onBoardingScreensSyncDAO = new OnBoardingScreenSyncDAO();
        }
        return onBoardingScreensSyncDAO;
    }


    @Override
    public String insert(DTO dtoObject, SQLiteDatabase dbObject) {
      /*  dbObject.delete(DBHandler.TABLE_FAQ_SYNC,null,null);
        ContentValues contentValues=new ContentValues();

        contentValues.put("lastUpdatedOn",)*/
        return null;
    }


    public String insertLastUpdatedOn(String lastUpdatedOn, SQLiteDatabase dbObject) {

        dbObject.delete(DbHandler.TABLE_ONBOARDING_SCREENS_SYNC, null, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("lastUpdatedOn", lastUpdatedOn);
        dbObject.insert(DbHandler.TABLE_ONBOARDING_SCREENS_SYNC, null, contentValues);


        return null;
    }

    public String getLastUpdatedOn(SQLiteDatabase dbObject) {

        String lastUpdatedTime = "";

      /*  Cursor cursor=dbObject.rawQuery("SELECT lastUpdatedOn from "+DbHandler.TABLE_ONBOARDING_SCREENS_SYNC,null);
        if(cursor!=null&&cursor.moveToFirst()) {
            lastUpdatedTime=cursor.getString(0);


        }
*/
        return lastUpdatedTime;
    }

    public boolean deleteTableData(SQLiteDatabase dbObject) {
        try {
        /*    dbObject.compileStatement("DELETE FROM " + DbHandler.TABLE_ONBOARDING_SCREENS_SYNC).execute();
            return true;*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbObject.close();
        }
        return false;
    }


    @Override
    public boolean update(DTO dtoObject, SQLiteDatabase dbObject) {
        return false;
    }

    @Override
    public boolean delete(DTO dtoObject, SQLiteDatabase dbObject) {
        return false;
    }

    @Override
    public List<DTO> getRecords(SQLiteDatabase dbObject) {
        return null;
    }
}
