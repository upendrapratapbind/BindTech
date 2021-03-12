package com.elearning.bindtech.ui_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.elearning.bindtech.R;
import com.elearning.bindtech.models.Student;
import com.elearning.bindtech.repository.StudentRepository;

public class BookActivity extends AppCompatActivity {
    AppCompatEditText edt_rollno, edt_student_name, edt_contactno;
    AppCompatTextView edt_gender;
    RadioButton rbtn_male, rbtn_female;
    AppCompatButton btn_submit;
    String srollno, sstudent_name, scontactno, sgender = "Male";
    TextView screen_title;
    Button btn_student_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        screen_title = findViewById(R.id.title_tv);
        screen_title.setText(R.string.student_database);

        edt_rollno = findViewById(R.id.edt_rollno);
        edt_student_name = findViewById(R.id.edt_student_name);
        edt_contactno = findViewById(R.id.edt_contactno);
        btn_student_list = findViewById(R.id.btn_student_list);
        // edt_gender=findViewById(R.id.edt_gender);
        rbtn_male = findViewById(R.id.rbtn_male);
        rbtn_female = findViewById(R.id.rbtn_female);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            srollno = data.getString("rollno");
            sstudent_name = data.getString("studentname");
            scontactno = data.getString("contactno");
            sgender = data.getString("gender");
            edt_rollno.setText(Integer.parseInt(srollno));
            edt_student_name.setText(sstudent_name);
            edt_contactno.setText(Integer.parseInt(scontactno));
            if (sgender.trim().toLowerCase().equalsIgnoreCase("male")) {
                rbtn_male.setChecked(true);
            } else if (sgender.trim().toLowerCase().equalsIgnoreCase("female")) {
                rbtn_female.setChecked(true);
            }
        }

        btn_student_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, StudentListActivity.class);
                startActivity(intent);
            }
        });
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_rollno.getText().toString().isEmpty() || edt_student_name.getText().toString().isEmpty() || edt_contactno.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill Detail", Toast.LENGTH_SHORT).show();
                } else {
                    srollno = edt_rollno.getText().toString().trim();
                    sstudent_name = edt_student_name.getText().toString().trim();
                    scontactno = edt_contactno.getText().toString().trim();
                    if (rbtn_male.isChecked()) {
                        sgender = "Male";
                    } else if (rbtn_female.isChecked()) {
                        sgender = "Female";
                    }
                    //   sgender=edt_gender.getText().toString().trim();
                    StudentRepository studentRepository = new StudentRepository(getApplicationContext());
                    Student student = new Student(Integer.parseInt(srollno), sstudent_name, scontactno, sgender);
                    studentRepository.InsertTask(student);
                    edt_rollno.setText("");
                    edt_student_name.setText("");
                    edt_contactno.setText("");

                }
            }
        });
    }

}
