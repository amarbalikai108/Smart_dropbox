package com.example.smartdropbox.home.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartdropbox.R;
import com.example.smartdropbox.application.BaseFragment;
import com.example.smartdropbox.databinding.FragmentHomeBinding;
import com.example.smartdropbox.home.view.HomeScreen;
import com.example.smartdropbox.registration.model.RegistrationModel;
import com.example.smartdropbox.utils.Util;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import info.androidhive.barcode.BarcodeReader;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends BaseFragment implements HomeFragmentViewListener {
    private int scannerId = 0;
    private Util util;
    private AppBroadcastReceiver myReceiver;
    private FusedLocationProviderClient mFusedLocationClient;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding homeDataBindingUtil;
    private static final int REQUEST_LOCATION_PERMISSION = 201;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int CAMERA_REQUEST = 101;

    private static final String TAG = HomeFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater,
                container, savedInstanceState);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        homeDataBindingUtil = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = homeDataBindingUtil.getRoot();
        util = new Util(getActivity());
        homeDataBindingUtil.setViewModel(homeViewModel);
        homeViewModel.init(this);
        util.hideKeyboard();
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation(0);

        }
        homeDataBindingUtil.etDropBoxId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    try {
                        if (homeDataBindingUtil.etDropBoxId.getText().toString().length() > 4)
                            homeDataBindingUtil.etName.setText("Box_" + homeDataBindingUtil.etDropBoxId.getText().toString().substring(homeDataBindingUtil.etDropBoxId.length() - 4, homeDataBindingUtil.etDropBoxId.length()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        return root;
    }


    @Override
    public void onDropBoxScannerClick() {
        scannerId = 1;
        if (!checkCameraPermission()) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new
                    String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            util.hideKeyboard();
            callScannerFragment();
        }
    }

    @Override
    public void onDeviceIdClick() {
        scannerId = 2;
        if (!checkCameraPermission()) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new
                    String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            util.hideKeyboard();
            callScannerFragment();
        }
    }

    @Override
    public void onAddressClick() {

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation(1);

        }
    }

    private void showScannedResult(String scannedResult) {
        if (scannerId == 1)
            homeDataBindingUtil.etDropBoxId.setText(scannedResult);
        else if (scannerId == 2)
            homeDataBindingUtil.etDeviceId.setText(scannedResult);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    util.hideKeyboard();
                    callScannerFragment();
                } else {
                    showToast(Objects.requireNonNull(getActivity()).getResources().getString(R.string.permission_denied));
                }
            case REQUEST_LOCATION_PERMISSION:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDialog();

                } else {
                    showToast(Objects.requireNonNull(getActivity()).getResources().getString(R.string.permission_denied));
                }
        }
    }

    private void callScannerFragment() {
        ((HomeScreen) Objects.requireNonNull(getActivity())).changeFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

        }
    }

    private boolean checkCameraPermission() {
        String permission = android.Manifest.permission.CAMERA;
        int res = Objects.requireNonNull(getContext()).checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
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
        }
    }

    @Override
    public void onFailure(String msg) {

    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                Objects.requireNonNull(getActivity()),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION_PERMISSION
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(final int flag) {

        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {

                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {

                                    try {
                                        hideDialog();
                                        if (flag == 1) {
                                            homeDataBindingUtil.etAddress.setText(getFullAddress(location.getLatitude(), location.getLongitude()));
                                        }
                                        homeViewModel.setLatLong(location.getLatitude(), location.getLongitude());

                                    } catch (IOException e) {

                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                );
            } else {
                showToast(Objects.requireNonNull(getActivity()).getResources().getString(R.string.turn_on_location));

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();


            try {
                hideDialog();
                homeDataBindingUtil.etAddress.setText(getFullAddress(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                homeViewModel.setLatLong(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    };


    private String getFullAddress(double latitude, double longitude) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName() == null ? "" : addresses.get(0).getFeatureName(); // Only if available else return NULL

        return knownName + ", " + city + ", " + state + ", " + country + ", " + postalCode;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void successAlert(HomeModel homeModel) {
        final AlertDialog dialog;
        TextView tvBoxID, tvCass, tvLatitude, tvLongitude, tvAddress, tvUserName;
        Button btnRegister;
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        alertLayout.setBackground(getActivity().getDrawable(R.drawable.rounded_dilaog));

        tvBoxID = alertLayout.findViewById(R.id.tvBoxID);
        tvCass = alertLayout.findViewById(R.id.tvCass);
        tvLatitude = alertLayout.findViewById(R.id.tvLatitude);
        tvLongitude = alertLayout.findViewById(R.id.tvLongitude);
        tvAddress = alertLayout.findViewById(R.id.tvAddress);
        tvUserName = alertLayout.findViewById(R.id.tvUserName);
        btnRegister = alertLayout.findViewById(R.id.btnRegister);
        tvBoxID.setText(getActivity().getResources().getString(R.string.box_id)+" " + homeModel.getBoxId());
        tvCass.setText(getActivity().getResources().getString(R.string.user_class)+" " + homeModel.getUserClass());
        tvLatitude.setText(getActivity().getResources().getString(R.string.lat)+" "+ homeModel.getLatitude() + "");
        tvLongitude.setText(getActivity().getResources().getString(R.string.lng)+" "+ homeModel.getLongitude() + "");
        tvAddress.setText(getActivity().getResources().getString(R.string.address)+" " + homeModel.getAddress());
        tvUserName.setText(getActivity().getResources().getString(R.string.name)+" "+ homeModel.getName());
        alert.setView(alertLayout);
        alert.setCancelable(false);
        dialog = alert.create();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                clearText();
            }
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    @Override
    public void emptyFields(int position) {
        switch (position) {
            case 1:
                showToast(Objects.requireNonNull(getActivity()).getResources().getString(R.string.please_enter_box_id));
                homeDataBindingUtil.etDropBoxId.requestFocus();
                break;
            case 2:
                showToast(Objects.requireNonNull(getActivity()).getResources().getString(R.string.please_enter_device_id));
                homeDataBindingUtil.etDeviceId.requestFocus();
                break;
            case 3:
                showToast(Objects.requireNonNull(getActivity()).getResources().getString(R.string.please_enter_name));
                homeDataBindingUtil.etName.requestFocus();
                break;
            case 4:
                showToast(Objects.requireNonNull(getActivity()).getResources().getString(R.string.please_enter_address));
                homeDataBindingUtil.etAddress.requestFocus();
                break;
        }
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public boolean isDeviseOnline() {
        return util.isOnline();
    }

    private void clearText() {
        homeDataBindingUtil.etDeviceId.setText("");
        homeDataBindingUtil.etDropBoxId.setText("");
        homeDataBindingUtil.etAddress.setText("");
        homeDataBindingUtil.etName.setText("");

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getActivity()).unregisterReceiver(myReceiver);
    }

    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("process_scanner_result");
        myReceiver = new AppBroadcastReceiver();
        Objects.requireNonNull(getActivity()).registerReceiver(myReceiver, filter);

    }

    public class AppBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showScannedResult(intent.getStringExtra("scanData") != null ? intent.getStringExtra("scanData") : "");
        }
    }

}
