package com.elearning.bindtech.ui_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elearning.bindtech.R;
import com.elearning.bindtech.models.RetrofitClient;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class CoursesActivity extends AppCompatActivity {
    private EditText editTextTo;
    private EditText editTextFrom;
    private EditText editTextSubject;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        editTextTo = findViewById(R.id.editTextTo);
        editTextFrom = findViewById(R.id.editTextFrom);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextMessage = findViewById(R.id.editTextMessage);

        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        String to = editTextTo.getText().toString().trim();
        String from = editTextFrom.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(to).matches()) {
            editTextTo.setError("Valid Recipient required");
            editTextTo.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(from).matches()) {
            editTextFrom.setError("Valid Sender required");
            editTextFrom.requestFocus();
            return;
        }

        if (subject.isEmpty()) {
            editTextSubject.setError("Subject required");
            editTextSubject.requestFocus();
            return;
        }

        if (message.isEmpty()) {
            editTextMessage.setError("Message required");
            editTextMessage.requestFocus();
            return;
        }

        RetrofitClient.getInstance()
                .getApi()
                .sendEmail(from, to, subject, message)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == HTTP_OK) {
                            try {
                                JSONObject obj = new JSONObject(response.body().string());
                                Toast.makeText(CoursesActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(CoursesActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

}
