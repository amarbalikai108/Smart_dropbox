package com.example.smartdropbox.registration.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartdropbox.R;
import com.example.smartdropbox.application.BaseActivity;
import com.example.smartdropbox.databinding.ActivityRegistationScreenBinding;

import com.example.smartdropbox.home.view.HomeScreen;
import com.example.smartdropbox.registration.viewModel.RegistrationViewModel;
import com.example.smartdropbox.utils.Util;

public class RegistrationScreen extends BaseActivity implements RegistrationViewListener {
    private RegistrationViewModel registrationViewModel;
    private Util util;
    private ActivityRegistationScreenBinding dataBindingUtil;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util(this);
        dataBindingUtil = DataBindingUtil.setContentView(this, R.layout.activity_registation_screen);
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        dataBindingUtil.setViewModel(registrationViewModel);
        registrationViewModel.init(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        util.hideKeyboard();
    }

    @Override
    public void showDialog() {
        showProgressDialog();
    }

    @Override
    public void hideDialog() {
        hideProgressDialog();
    }

    @Override
    public void onSuccess(String msg) {
        if (msg.equals("Success")) {
            showToast(getString(R.string.user_save_successfully));
            startActivity(new Intent(this, HomeScreen.class));
            finish();

        }
    }

    @Override
    public void onFailure(String msg) {

    }


    @Override
    public void emptyFields(int position) {
        switch (position) {
            case 1:
                dataBindingUtil.etName.requestFocus();
                showToast(getResources().getString(R.string.please_enter_user_name));
                break;
            case 2:
                dataBindingUtil.etPhoneNo.requestFocus();
                showToast(getResources().getString(R.string.please_enter_phone_number));
                break;
            case 3:
                dataBindingUtil.etEmpID.requestFocus();
                showToast(getResources().getString(R.string.please_enter_emp_id));
                break;

        }
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public boolean isDeviseOnline() {
        return util.isOnline();
    }
}
