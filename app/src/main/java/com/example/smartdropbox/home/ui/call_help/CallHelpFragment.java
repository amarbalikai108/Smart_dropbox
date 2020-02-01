package com.example.smartdropbox.home.ui.call_help;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdropbox.BuildConfig;
import com.example.smartdropbox.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.File;
import java.io.FileNotFoundException;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;



public class CallHelpFragment extends Fragment {
    private BarcodeDetector detector;
    private Uri imageUri;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int CAMERA_REQUEST = 101;
    private static final String TAG = "API123";
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";
    TextView textView;
    private CallHelpViewModel callHelpViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        callHelpViewModel =
                ViewModelProviders.of(this).get(CallHelpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_call_help, container, false);
        textView = root.findViewById(R.id.text_tools);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(getActivity(), new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }
        });

        return root;
    }

}