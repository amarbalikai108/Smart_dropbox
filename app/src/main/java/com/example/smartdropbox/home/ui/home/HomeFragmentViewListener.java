package com.example.smartdropbox.home.ui.home;

import com.example.smartdropbox.utils.ViewListener;

public interface HomeFragmentViewListener extends ViewListener {

    void onDropBoxScannerClick();

    void onDeviceIdClick();

    void onAddressClick();

    void successAlert(HomeModel homeModel);

    void emptyFields(int position);

    void showMessage(String msg);

    boolean isDeviseOnline();

}
