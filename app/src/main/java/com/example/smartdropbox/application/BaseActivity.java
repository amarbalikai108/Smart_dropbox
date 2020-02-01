package com.example.smartdropbox.application;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity
{
    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void showProgressDialog()
    {
        if(mProgressDialog!=null && !mProgressDialog.isShowing())
        {
            mProgressDialog.show();
        }
    }
    public void hideProgressDialog()
    {
        if(mProgressDialog!=null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }
    }
    public void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
