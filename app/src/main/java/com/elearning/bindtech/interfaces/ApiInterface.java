package com.elearning.bindtech.interfaces;

import com.elearning.bindtech.models.FoodData;

import java.util.List;

import retrofit.http.GET;
import retrofit2.Call;

public interface ApiInterface {
    @GET("photos")
    Call<List<FoodData>> getAllData();

}
