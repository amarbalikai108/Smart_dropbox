package com.example.smartdropbox.home.ui.home;

import android.app.Activity;
import android.view.View;

import com.example.smartdropbox.R;
import com.example.smartdropbox.application.SmartDropbox;
import com.example.smartdropbox.home.view.HomeScreen;
import com.example.smartdropbox.network_component.ApiResponseListener;
import com.example.smartdropbox.registration.model.RegistrationModel;
import com.example.smartdropbox.registration.view.RegistrationScreen;
import com.example.smartdropbox.utils.SharedPreferencesImp;
import com.example.smartdropbox.utils.Util;

import java.util.Objects;

import androidx.lifecycle.ViewModel;
import retrofit2.Response;

public class HomeViewModel extends ViewModel implements ApiResponseListener {

    HomeFragmentViewListener viewListener = null;
    HomeRepo homeRepo;
    SharedPreferencesImp sharedPreferencesImp;
    public HomeModel homeModel = new HomeModel();

    public HomeViewModel() {
    }

    public void init(HomeFragmentViewListener viewListener) {
        this.viewListener = viewListener;
        sharedPreferencesImp = SmartDropbox.getInstance().getAppSharePreference();
        homeRepo = new HomeRepo(this);
    }

     void setLatLong(Double latitude, Double longitude) {
        homeModel.setLatitude(latitude);
        homeModel.setLongitude(longitude);
    }

    public void onDropBoxScannerClick(View view) {
        viewListener.onDropBoxScannerClick();
    }

    public void onDeviceIdClick(View view) {
        viewListener.onDeviceIdClick();
    }

    public void onAddressClick(View view) {
        viewListener.onAddressClick();
    }

    public void onActivateClick(View view) {
        if(viewListener.isDeviseOnline()) {
            if (homeModel.getBoxId().equals("")) {
                viewListener.emptyFields(1);
            } else if (homeModel.getDeviceId().equals("")) {
                viewListener.emptyFields(2);
            } else if (homeModel.getName().equals("")) {
                viewListener.emptyFields(3);
            } else if (homeModel.getAddress().equals("")) {
                viewListener.emptyFields(4);
            } else {
                viewListener.showDialog();
                homeRepo.addDropBox(homeModel);
            }
        } else
        {
            viewListener.showMessage("internet is not available");
        }

    }

    @Override
    public void onFinalResponse(Response response) {
        HomeModel homeModel = (HomeModel) response.body();
        viewListener.hideDialog();
        // viewListener.onSuccess("Success");
        viewListener.successAlert(homeModel);
    }

    @Override
    public void onFinalFailure() {
        viewListener.hideDialog();
    }

    @Override
    public void onErrorHandler(Response response) {
        viewListener.hideDialog();
    }

    @Override
    public void onFailure(String strError) {
        viewListener.hideDialog();
    }
}