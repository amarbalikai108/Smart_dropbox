package com.example.smartdropbox.network_component;

import retrofit2.Response;

public interface ApiResponseListener<T>
{
    void onFinalResponse(Response<T> response);
    void onFinalFailure();
    void onErrorHandler(Response<T> response);
    void onFailure(String strError);
}
