package com.elearning.bindtech.ui_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.elearning.bindtech.R;
import com.elearning.bindtech.interfaces.ApiInterface;
import com.elearning.bindtech.models.FoodData;
import com.elearning.bindtech.retrofit.RetrofitClient;

import java.util.List;

import retrofit.http.GET;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingActivity extends AppCompatActivity {

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }
    //today we are going to make food app like zomato and swiggy


    public void dataThroughApi() {
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<List<FoodData>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<FoodData>>() {
            @Override
            public void onResponse(Call<List<FoodData>> call, Response<List<FoodData>> response) {
                List<FoodData> foodData = response.body();
            }

            @Override
            public void onFailure(Call<List<FoodData>> call, Throwable throwable) {

                Toast.makeText(SettingActivity.this, "Server is not responding", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
