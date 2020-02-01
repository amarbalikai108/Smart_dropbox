package com.example.smartdropbox.network_component;

import com.example.smartdropbox.home.ui.home.HomeModel;
import com.example.smartdropbox.registration.model.RegistrationModel;

public interface IApiHelper
{
    void setBaseUrl(String baseUrl);
    void onRegistration(RegistrationModel registrationModel);
    void addDropBoxInfo(HomeModel homeModel);
}
