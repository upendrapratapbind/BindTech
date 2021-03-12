package com.elearning.bindtech.room_database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.elearning.bindtech.interfaces.StudentDao;
import com.elearning.bindtech.models.Student;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();
}
