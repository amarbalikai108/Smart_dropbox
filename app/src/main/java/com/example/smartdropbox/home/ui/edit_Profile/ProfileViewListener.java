package com.example.smartdropbox.home.ui.edit_Profile;

import com.example.smartdropbox.utils.ViewListener;

public interface ProfileViewListener extends ViewListener {
    void showUserData(EditProfileViewModel sharedPreferencesImp);
    boolean isDeviseOnline();
    void showMessage(String msg);
}
