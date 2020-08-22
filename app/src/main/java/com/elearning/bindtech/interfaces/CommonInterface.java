package com.elearning.bindtech.interfaces;

import retrofit.RetrofitError;

public interface CommonInterface {
    void onRequestSuccess(Object responseObj);

    void onRequestFailure(RetrofitError errorCode, String errorFrom);
}
