package com.elearning.bindtech.interfaces;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface DAO {
    String insert(DTO dtoObject, SQLiteDatabase dbObject);

    boolean update(DTO dtoObject, SQLiteDatabase dbObject);

    boolean delete(DTO dtoObject, SQLiteDatabase dbObject);

    List<DTO> getRecords(SQLiteDatabase dbObject);
}
