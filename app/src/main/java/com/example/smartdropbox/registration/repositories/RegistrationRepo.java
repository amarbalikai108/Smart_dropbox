package com.example.smartdropbox.registration.repositories;

import android.util.Log;

import com.example.smartdropbox.network_component.ApiConstants;
import com.example.smartdropbox.network_component.ApiHelper;
import com.example.smartdropbox.network_component.ApiResponseListener;
import com.example.smartdropbox.registration.model.RegistrationModel;

import retrofit2.Response;

public class RegistrationRepo implements ApiResponseListener
{
    ApiHelper apiHelper;
    ApiResponseListener mApiResponseListener;

    public RegistrationRepo(ApiResponseListener mApiResponseListener) {
        this.apiHelper=new ApiHelper(this);
        this.mApiResponseListener=mApiResponseListener;
    }

    public void registerUser(RegistrationModel registrationModel)
    {
        apiHelper.setBaseUrl(ApiConstants.BASE_URL);
        apiHelper.onRegistration(registrationModel);
    }
    @Override
    public void onFinalResponse(Response response) {
        mApiResponseListener.onFinalResponse(response);
    }

    @Override
    public void onFinalFailure() {
        mApiResponseListener.onFinalFailure();
    }

    @Override
    public void onErrorHandler(Response response) {
        mApiResponseListener.onErrorHandler(response);
    }

    @Override
    public void onFailure(String strError) {
        mApiResponseListener.onFailure(strError);
    }
}
