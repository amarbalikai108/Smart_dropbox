package com.example.smartdropbox.application;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment
{
    ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showToast(String msg)
    {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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

}
