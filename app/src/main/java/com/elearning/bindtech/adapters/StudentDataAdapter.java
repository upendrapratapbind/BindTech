package com.elearning.bindtech.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.recyclerview.widget.RecyclerView;

import com.elearning.bindtech.R;
import com.elearning.bindtech.models.Student;
import com.elearning.bindtech.ui_activities.BookActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentDataAdapter extends RecyclerView.Adapter<StudentDataAdapter.MyViewHolder> {
    private final ArrayList<Student> studentArrayList;
    private final Context context;

    public StudentDataAdapter(ArrayList<Student> studentArrayList, Context context) {
        this.studentArrayList = studentArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_student, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = studentArrayList.get(position);
        holder.tv_rollno.setText(Integer.toString(student.getRollno()));
        holder.tv_student_name.setText(student.getStudent_name());
        holder.tv_contactno.setText(student.getContactno());
        if (student.getGender().equalsIgnoreCase("male")) {
            holder.img_gender.setImageResource(R.drawable.bluemale);
        } else if (student.getGender().equalsIgnoreCase("female")) {
            holder.img_gender.setImageResource(R.drawable.pinkfemale);
        }
        holder.btn_title.setText(student.getStudent_name().substring(0, 1));
        Random random = new Random();
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        holder.btn_title.setBackgroundColor(Color.rgb(red, green, blue));
        holder.img_phonecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + student.getContactno()));
                context.startActivity(intent);
            }
        });
        holder.ll_card_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rollno = student.getRollno();
                String studentname = student.getStudent_name();
                String contactno = student.getContactno();
                String gender = student.getGender();
                // Toast.makeText(context,rollno+"\n"+studentname+"\n"+contactno+"\n"+gender,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra("rollno", rollno);
                intent.putExtra("studentname", studentname);
                intent.putExtra("contactno", contactno);
                intent.putExtra("gender", gender);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_rollno, tv_student_name, tv_contactno;
        ImageView img_gender, img_phonecall;
        Button btn_title;
        LinearLayout ll_card_student;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_rollno = itemView.findViewById(R.id.tv_rollno);
            tv_student_name = itemView.findViewById(R.id.tv_student_name);
            tv_contactno = itemView.findViewById(R.id.tv_contactno);
            img_gender = itemView.findViewById(R.id.img_gender);
            img_phonecall = itemView.findViewById(R.id.img_call);
            btn_title = itemView.findViewById(R.id.btn_title);
            ll_card_student = itemView.findViewById(R.id.ll_card_student);

        }
    }
}
