package com.example.smartdropbox.network_component;

import com.example.smartdropbox.home.ui.home.HomeModel;
import com.example.smartdropbox.registration.model.RegistrationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiHelper extends RetryableCallback implements IApiHelper {
    GetApiService service;
    String baseUrl="";
    ApiResponseListener listener;

    public ApiHelper(ApiResponseListener listener) {
        this.listener= listener;
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        this.baseUrl=baseUrl;
        service=RetrofitInstance.getRetrofitInstance(baseUrl).create(GetApiService.class);
    }

    @Override
    public void onRegistration(RegistrationModel registrationModel) {
        Call call=service.registrationUser(registrationModel);
        call.enqueue(this);
    }

    @Override
    public void addDropBoxInfo(HomeModel homeModel) {
        Call call=service.addDropBox(homeModel);
        call.enqueue(this);
    }

    @Override
    void onFinalFailure(Call call, Throwable t) {
        call.cancel();
        listener.onFinalFailure();
    }

    @Override
    void onFinalResponse(Call call, Response response) {
        if(response.isSuccessful())
        {
            listener.onFinalResponse(response);
        }
        else
        {
            listener.onErrorHandler(response);
        }
    }

}
