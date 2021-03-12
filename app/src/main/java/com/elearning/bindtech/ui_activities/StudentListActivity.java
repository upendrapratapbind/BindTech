package com.elearning.bindtech.ui_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.elearning.bindtech.R;
import com.elearning.bindtech.adapters.StudentDataAdapter;
import com.elearning.bindtech.models.Student;
import com.elearning.bindtech.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentListActivity extends AppCompatActivity {
    StudentDataAdapter studentDataAdapter;
    RecyclerView recyclerview_student_list;
    ArrayList<Student> studentArrayList, studentArrayList_Search;
    EditText edt_search;
    TextView screen_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        screen_title = findViewById(R.id.title_tv);
        screen_title.setText("Student List");
        recyclerview_student_list = findViewById(R.id.recyclerView_Student_List);
        edt_search = findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                Filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        recyclerview_student_list.setHasFixedSize(true);
        recyclerview_student_list.setLayoutManager(new LinearLayoutManager(StudentListActivity.this));
        new LoadDataTask().execute();

    }

    public void Filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Log.d("filter", charText + "");
        studentArrayList.clear();
        if (charText.length() == 0) {
            studentArrayList.addAll(studentArrayList_Search);
            Log.d("load data", "All");
        } else {
            Log.d("load data", "filter");
            for (Student student : studentArrayList_Search) {
                if (student.getStudent_name().toLowerCase(Locale.getDefault()).contains(charText)
                        || student.getContactno().toLowerCase(Locale.getDefault()).contains(charText)) {
                    studentArrayList.add(student);
                }

            }
            studentDataAdapter.notifyDataSetChanged();
        }
    }
    //************************FILTER METHOD START******************************

    public class LoadDataTask extends AsyncTask<Void, Void, Void> {

        StudentRepository studentRepository;
        List<Student> studentList;

        @Override
        protected void onPreExecute() {
            studentRepository = new StudentRepository(getApplicationContext());
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentList = studentRepository.getStudents();
            studentArrayList = new ArrayList<>();
            studentArrayList_Search = new ArrayList<>();
            for (int i = 0; i < studentList.size(); i++) {
                studentArrayList.add(studentList.get(i));
                studentArrayList_Search.add(studentList.get(i));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            studentDataAdapter = new StudentDataAdapter(studentArrayList, StudentListActivity.this);
            recyclerview_student_list.setAdapter(studentDataAdapter);
        }
    }
    //************************FILTER METHOD END*******************************
}