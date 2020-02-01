package com.example.smartdropbox.home.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import info.androidhive.barcode.BarcodeReader;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartdropbox.R;
import com.example.smartdropbox.application.BaseFragment;
import com.example.smartdropbox.home.ui.home.HomeFragment;
import com.example.smartdropbox.home.view.HomeScreen;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class ScannerFragment extends BaseFragment implements BarcodeReader.BarcodeReaderListener {

    private BarcodeReader barcodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_scanner, container, false);
        barcodeReader = (BarcodeReader) getChildFragmentManager().findFragmentById(R.id.barcode_fragment);
        barcodeReader.setListener(this);
        return root;
    }



    @Override
    public void onScanned(final Barcode barcode) {
        barcodeReader.playBeep();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent();
                intent.putExtra("scanData",barcode.displayValue);
                intent.setAction("process_scanner_result");
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack("scanner", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        showToast("Please show single barcode/QrCode");
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {
        Log.e(TAG, "onScanError: " + errorMessage);
    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getActivity(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }
}
