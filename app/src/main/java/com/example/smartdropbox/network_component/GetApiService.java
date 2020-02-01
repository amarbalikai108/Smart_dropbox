package com.example.smartdropbox.network_component;

import com.example.smartdropbox.home.ui.home.HomeModel;
import com.example.smartdropbox.registration.model.RegistrationModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GetApiService
{
    @Headers("Content-Type: application/json")
    @POST(ApiConstants.API_REGISTRATION)
    Call<RegistrationModel> registrationUser(@Body RegistrationModel registrationModel);

    @POST(ApiConstants.API_ADD_DROPBOX)
    Call<HomeModel> addDropBox(@Body HomeModel homeModel);
}
