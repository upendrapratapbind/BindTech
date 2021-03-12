package com.elearning.bindtech.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.elearning.bindtech.models.Student;
import com.elearning.bindtech.room_database.StudentDatabase;

import java.util.List;

import retrofit.http.PUT;

public class StudentRepository {
    private final String DB_NAME = "studentdb";
    private final StudentDatabase studentDatabase;
    Context context;

    public StudentRepository(Context context) {

        this.context = context;
        studentDatabase = Room.databaseBuilder(context, StudentDatabase.class, DB_NAME).build();
        //    Toast.makeText(context,"Database is Created!!",Toast.LENGTH_SHORT).show();
    }

    //******************INSERT TASK START**************
    public void InsertTask(Student student) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                studentDatabase.studentDao().insertTask(student);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(context, student.student_name + " is Inserted", Toast.LENGTH_SHORT).show();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }
    //******************INSERT TASK END****************

    //*******************GET DATA TASK END******************
    public List<Student> getStudents() {
        List<Student> studentList = studentDatabase.studentDao().getAll();
        return studentList;

    }
    //*******************GET DATA TASK END******************


    //***************UPDATE OPERATION START**********************
    public void UpdateTask(final Student student) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                studentDatabase.studentDao().updateTask(student);
                return null;
            }
        }.execute();
    }

    //*******************UPDATE OPERATION END*******************
}
