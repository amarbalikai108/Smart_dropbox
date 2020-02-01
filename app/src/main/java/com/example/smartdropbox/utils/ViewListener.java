package com.example.smartdropbox.utils;

import com.example.smartdropbox.home.ui.home.HomeModel;
import com.example.smartdropbox.utils.SharedPreferencesImp;

public interface ViewListener
{
    void showDialog();
    void hideDialog();
    void onSuccess(String msg);
    void onFailure(String msg);

}
