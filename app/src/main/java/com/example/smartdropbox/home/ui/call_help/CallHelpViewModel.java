package com.example.smartdropbox.home.ui.call_help;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CallHelpViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CallHelpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}