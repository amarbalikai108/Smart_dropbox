package com.example.smartdropbox.home.ui.installation_Manual;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InstallationManualModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InstallationManualModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}