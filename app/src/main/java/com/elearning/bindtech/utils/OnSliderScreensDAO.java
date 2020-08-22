package com.elearning.bindtech.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elearning.bindtech.DbHandler;
import com.elearning.bindtech.interfaces.DAO;
import com.elearning.bindtech.interfaces.DTO;
import com.elearning.bindtech.models.OnSliderItem;

import java.util.ArrayList;
import java.util.List;

public class OnSliderScreensDAO implements DAO {
    private static OnSliderScreensDAO onBoardingScreensDAO;
    private final String TAG = "OBScreensLog";

    public static OnSliderScreensDAO getInstance() {
        if (onBoardingScreensDAO == null) {
            onBoardingScreensDAO = new OnSliderScreensDAO();
        }
        return onBoardingScreensDAO;
    }

    public String insert(OnSliderItem dtoObject, SQLiteDatabase dbObject) {
        try {
            OnSliderItem dto = dtoObject;
            ContentValues cValues = new ContentValues();
            cValues.put("heading", dto.getHeading()); //1
            cValues.put("description", dto.getDescription()); //3
            cValues.put("image_file_saved_path", dto.getImagePath()); //3
            long rowsEffected = dbObject.insert(DbHandler.TABLE_ONBOARDING_SCREENS, null, cValues);
            if (rowsEffected > 0)
                return "";
        } catch (SQLException e) {
            Log.i(TAG + "insert()", e.getMessage());
            return "";
        } finally {
            //   dbObject.close();
        }
        return "";
    }

    @Override
    public boolean update(DTO dtoObject, SQLiteDatabase dbObject) {
        try {

        } catch (SQLException e) {

        } finally {
            dbObject.close();
        }
        return false;
    }

    @Override
    public boolean delete(DTO dtoObject, SQLiteDatabase dbObject) {
           /* NotificationModel dto = (NotificationModel) dtoObject;
            try {
                dbObject.compileStatement("DELETE FROM "+DBHandler.TABLE_RETAILER_NOTIFICATION_LOG).execute();
                return true;
            } catch (Exception e) {
                Log.e(TAG + "delete()", e.getMessage());
            } finally {
                dbObject.close();
            }*/
        return false;
    }


    @Override
    public String insert(DTO dtoObject, SQLiteDatabase dbObject) {
        return null;
    }

    @Override
    public List<DTO> getRecords(SQLiteDatabase dbObject) {
        List<DTO> onBoardItemList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = dbObject.rawQuery("SELECT * FROM " + DbHandler.TABLE_ONBOARDING_SCREENS + " ", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    OnSliderItem dto = new OnSliderItem();
                    dto.setHeading(cursor.getString(cursor.getColumnIndex("heading")));
                    dto.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    dto.setImagePath(cursor.getString(cursor.getColumnIndex("image_file_saved_path")));


                    onBoardItemList.add(dto);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            // dbObject.close();
        }
        return onBoardItemList;
    }


    public boolean deleteTableData(SQLiteDatabase dbObject) {
        try {
            dbObject.compileStatement("DELETE FROM  " + DbHandler.TABLE_ONBOARDING_SCREENS).execute();
            return true;
        } catch (Exception e) {

        } finally {
            dbObject.close();
        }
        return false;
    }

    /**
     * It will return number of coupons available for upload, if there are no coupons available in local to sync it will return 0
     * @param dbObject
     * @return number of records available to sync
     */

}
