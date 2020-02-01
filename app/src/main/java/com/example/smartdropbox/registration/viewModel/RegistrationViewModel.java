package com.example.smartdropbox.registration.viewModel;

import android.app.Activity;
import android.view.View;

import com.example.smartdropbox.R;
import com.example.smartdropbox.application.SmartDropbox;
import com.example.smartdropbox.network_component.ApiResponseListener;
import com.example.smartdropbox.registration.model.RegistrationModel;
import com.example.smartdropbox.registration.repositories.RegistrationRepo;
import com.example.smartdropbox.registration.view.RegistrationViewListener;
import com.example.smartdropbox.utils.SharedPreferencesImp;

import java.util.Objects;

import androidx.lifecycle.ViewModel;
import retrofit2.Response;

public class RegistrationViewModel extends ViewModel implements ApiResponseListener
{
    RegistrationViewListener viewListener=null;
    RegistrationRepo registrationRepo;
    SharedPreferencesImp sharedPreferencesImp;

    public RegistrationModel registrationModel=new RegistrationModel();
    public void init(RegistrationViewListener viewListener) {
        this.viewListener=viewListener;
        sharedPreferencesImp=SmartDropbox.getInstance().getAppSharePreference();
        registrationRepo=new RegistrationRepo(this);

    }
    public void onRegisterClick(View view)
    {
        if(viewListener.isDeviseOnline()) {
            if (registrationModel.getUserName().equals("")) {
                viewListener.emptyFields(1);
            } else if (registrationModel.getPhoneNumber().equals("")) {
                viewListener.emptyFields(2);
            } else if (registrationModel.getEmpId().equals("")) {
                viewListener.emptyFields(3);
            } else {
                registrationModel.setAppVersion(SmartDropbox.appVersionName);
                registrationModel.setSdkVersion(SmartDropbox.appSDKVerion);
                viewListener.showDialog();
                //Log.e("allData ",registrationModel.getEmpId()+"   "+registrationModel.getPhoneNumber());
                registrationRepo.registerUser(registrationModel);
            }
        }
        else
        {
            viewListener.showMessage(((Activity)viewListener).getResources().getString(R.string.internet_not_available));
        }


    }

    @Override
    public void onFinalResponse(Response response) {
        RegistrationModel registrationModel= (RegistrationModel) response.body();
        sharedPreferencesImp.putEmpId(Objects.requireNonNull(registrationModel).getEmpId());
        sharedPreferencesImp.putUserClass(Objects.requireNonNull(registrationModel).getUserClass());
        sharedPreferencesImp.putUserName(Objects.requireNonNull(registrationModel).getUserName());
        sharedPreferencesImp.putPhoneNo(Objects.requireNonNull(registrationModel).getPhoneNumber());
        //sharedPreferencesImp.putRoute(Objects.requireNonNull(registrationModel).getRoute());
        sharedPreferencesImp.putRegistered(true);
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
