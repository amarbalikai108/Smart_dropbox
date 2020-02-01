package com.example.smartdropbox.home.ui.edit_Profile;

import android.view.View;

import com.example.smartdropbox.application.SmartDropbox;
import com.example.smartdropbox.network_component.ApiResponseListener;
import com.example.smartdropbox.registration.model.RegistrationModel;
import com.example.smartdropbox.registration.repositories.RegistrationRepo;
import com.example.smartdropbox.utils.SharedPreferencesImp;

import java.util.Objects;

import androidx.lifecycle.ViewModel;
import retrofit2.Response;

public class EditProfileViewModel extends ViewModel implements ApiResponseListener {


    ProfileViewListener viewListener = null;
    RegistrationRepo registrationRepo;
    SharedPreferencesImp sharedPreferencesImp;
    public RegistrationModel registrationModel = new RegistrationModel();
    //MutableLiveData<RegistrationModel> mutableRegistration;

    public void init(ProfileViewListener viewListener) {
        this.viewListener = viewListener;
        sharedPreferencesImp = SmartDropbox.getInstance().getAppSharePreference();
        registrationRepo = new RegistrationRepo(this);
        getLocalData();
        viewListener.showUserData(this);
    }


    private void getLocalData() {

        registrationModel.setEmpId(sharedPreferencesImp.getEmpId());
        registrationModel.setPhoneNumber(sharedPreferencesImp.getPhoneNo());
        registrationModel.setUserName(sharedPreferencesImp.getUserName());

    }

    public void onRegisterClick(View view) {
        if(viewListener.isDeviseOnline()) {
            registrationModel.setAppVersion(SmartDropbox.appVersionName);
            registrationModel.setSdkVersion(SmartDropbox.appSDKVerion);
            viewListener.showDialog();
            registrationRepo.registerUser(registrationModel);
        }
        else
        {
            viewListener.showMessage("internet is not available");
        }

    }

    @Override
    public void onFinalResponse(Response response) {
        RegistrationModel registrationModel = (RegistrationModel) response.body();
        sharedPreferencesImp.putEmpId(Objects.requireNonNull(registrationModel).getEmpId());
        sharedPreferencesImp.putUserClass(Objects.requireNonNull(registrationModel).getUserClass());
        sharedPreferencesImp.putUserName(Objects.requireNonNull(registrationModel).getUserName());
        sharedPreferencesImp.putPhoneNo(Objects.requireNonNull(registrationModel).getPhoneNumber());

        viewListener.hideDialog();
        viewListener.onSuccess("Success");
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