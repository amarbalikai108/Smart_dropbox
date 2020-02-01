package com.example.smartdropbox.home.ui.home;

import com.example.smartdropbox.network_component.ApiConstants;
import com.example.smartdropbox.network_component.ApiHelper;
import com.example.smartdropbox.network_component.ApiResponseListener;
import com.example.smartdropbox.registration.model.RegistrationModel;

import retrofit2.Response;

public class HomeRepo implements ApiResponseListener {
    ApiHelper apiHelper;
    ApiResponseListener mApiResponseListener;
    public HomeRepo(ApiResponseListener mApiResponseListener) {
        this.apiHelper=new ApiHelper(this);
        this.mApiResponseListener=mApiResponseListener;
    }
    public void addDropBox(HomeModel HomeModel)
    {
        apiHelper.setBaseUrl(ApiConstants.BASE_URL);
        apiHelper.addDropBoxInfo(HomeModel);
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
