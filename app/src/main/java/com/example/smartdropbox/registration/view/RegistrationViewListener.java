package com.example.smartdropbox.registration.view;

import com.example.smartdropbox.utils.ViewListener;

public interface RegistrationViewListener extends ViewListener
{
    void emptyFields(int position);
    void showMessage(String msg);
    boolean isDeviseOnline();
}
