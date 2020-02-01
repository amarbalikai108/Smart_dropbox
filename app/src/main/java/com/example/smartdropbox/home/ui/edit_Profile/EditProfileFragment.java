package com.example.smartdropbox.home.ui.edit_Profile;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartdropbox.R;
import com.example.smartdropbox.application.BaseFragment;
import com.example.smartdropbox.databinding.FragmentEditProfileBinding;
import com.example.smartdropbox.home.view.HomeScreen;
import com.example.smartdropbox.utils.Util;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


public class EditProfileFragment extends BaseFragment implements ProfileViewListener {
    FragmentEditProfileBinding EditProfileDataBinding;
    private EditProfileViewModel editProfileViewModel;
    Util util;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,
                container, savedInstanceState);
        editProfileViewModel =
                ViewModelProviders.of(this).get(EditProfileViewModel.class);
        util = new Util(getActivity());
        EditProfileDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false);
        View root = EditProfileDataBinding.getRoot();
        EditProfileDataBinding.setViewModel(editProfileViewModel);
        editProfileViewModel.init(this);
        return root;
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
            ((HomeScreen) Objects.requireNonNull(getActivity())).updateDrawerName();
        }
    }

    @Override
    public void onFailure(String msg) {

    }


    @Override
    public void showUserData(EditProfileViewModel sharedPreferencesImp) {
        EditProfileDataBinding.setViewModel(sharedPreferencesImp);
    }

    @Override
    public boolean isDeviseOnline() {
        return util.isOnline();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }


}