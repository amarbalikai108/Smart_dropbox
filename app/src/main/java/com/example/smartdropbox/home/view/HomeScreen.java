package com.example.smartdropbox.home.view;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.smartdropbox.application.BaseActivity;
import com.example.smartdropbox.home.adapter.DrawerAdapter;
import com.example.smartdropbox.home.model.DrawerModel;
import com.example.smartdropbox.R;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.smartdropbox.application.SmartDropbox;
import com.example.smartdropbox.home.ui.ScannerFragment;
import com.example.smartdropbox.home.ui.dashboard.DashboardFragment;
import com.example.smartdropbox.home.ui.edit_Profile.EditProfileFragment;
import com.example.smartdropbox.home.ui.home.HomeFragment;
import com.example.smartdropbox.utils.SharedPreferencesImp;
import com.example.smartdropbox.utils.Util;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.smartdropbox.utils.Constants.PDFFILELOCATION;

public class HomeScreen extends BaseActivity {
    private TextView appVersion, tvUserName;
    private int oldMenuPositin = 0;
    private int REQUEST_CALL_PERMISSION = 100;
    private int newMenuPosition = 0;
    private AppBarConfiguration mAppBarConfiguration;
    private String scannedBoxId = "";
    private ImageView imgCall;
    private static final int REQUEST_STORAGE_PERMISSION = 202;
    private Util util;
    private int menuItemSelected = 0;
    private DrawerAdapter recyclerAdapter;

    public String getScannedBoxId() {
        return scannedBoxId;
    }

    public void setScannedBoxId(String scannedBoxId) {
        this.scannedBoxId = scannedBoxId;
    }

    public String getScannedDeviceId() {
        return scannedDeviceId;
    }

    public void setScannedDeviceId(String scannedDeviceId) {
        this.scannedDeviceId = scannedDeviceId;
    }

    ArrayList<DrawerModel> nav_item = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private String scannedDeviceId = "";
    private RecyclerView recyclerView;
    SharedPreferencesImp sharedPreferencesImp;

    @SuppressLint("CutPasteId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        imgCall = findViewById(R.id.imgCall);
        setSupportActionBar(toolbar);
        sharedPreferencesImp = SmartDropbox.getInstance().getAppSharePreference();
        //toolbar.setVisibility(View.INVISIBLE);
        //toolbar.getBackground().setAlpha(0);

       /* ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));*/
        tvUserName = findViewById(R.id.tvUserName);
        appVersion = findViewById(R.id.appVersion);
        appVersion.setText(" " + SmartDropbox.appVersionName);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.list);
        updateDrawerName();

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    callConfirmationDialog();

                }
            }
        });
        nav_item.add(new DrawerModel(getResources().getString(R.string.menu_home)));
        nav_item.get(0).setIsSelected(true);
        nav_item.add(new DrawerModel(getResources().getString(R.string.menuEditProfile)));
        nav_item.add(new DrawerModel(getResources().getString(R.string.installation_manual)));
        nav_item.add(new DrawerModel(getResources().getString(R.string.dashboard)));

        //NavigationView navigationView = findViewById(R.id.nav_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new DrawerAdapter(this, nav_item, new DrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (nav_item.get(position).getMenuName().equals(getResources().getString(R.string.installation_manual))) {
                    selectItem(position);

                }
                else
                {
                    newMenuPosition = position;
                    selectItem(position);
                    nav_item.get(oldMenuPositin).setIsSelected(false);
                    recyclerAdapter.notifyItemChanged(oldMenuPositin);
                    nav_item.get(newMenuPosition).setIsSelected(true);
                    oldMenuPositin = newMenuPosition;

                    recyclerAdapter.notifyItemChanged(newMenuPosition);

                }
            }
        });
        recyclerView.setAdapter(recyclerAdapter);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.app_color));
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        util = new Util(this);
        util.hideKeyboard();
        HomeFragment scannerFrag = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, scannerFrag).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void changeFragment() {
        ScannerFragment scannerFrag = new ScannerFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, scannerFrag).addToBackStack("scanner").commit();

    }

    public void selectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 0:

                fragment = new HomeFragment();
                showFragment(fragment);
                break;
            case 1:
                fragment = new EditProfileFragment();
                showFragment(fragment);
                break;
            case 2:
                if (!checkStoragePermissions()) {
                    requestStoragePermissions();
                }
                else
                {
                    copyRaw();
                    CopyReadAssets();
                }
                break;
            case 3:
                fragment = new DashboardFragment();
                showFragment(fragment);
                break;
            default:
                break;
        }
    }
    @SuppressLint("WorldReadableFiles")
    private void CopyReadAssets()
    {


        File file1 = new File(PDFFILELOCATION);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(
             FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file1), "application/pdf");
                /*Uri.parse("file://" + getFilesDir() + "/certificate.pdf"),
                "application/pdf");*/

        startActivity(intent);
    }

    void copyRaw() {
        final int mSongs =R.raw.certificate;
        //for (int i = 0; i < mSongs.length; i++) {
            try {
                final File newFile = new File(Environment.getExternalStorageDirectory() + "/UPS");
                if(!newFile.exists()) {
                    newFile.mkdir();
                }
                String path = Environment.getExternalStorageDirectory() + "/UPS";
                File dir = new File(path);
                if (dir.mkdirs() || dir.isDirectory()) {
                    String str_song_name = "Certificate" + ".pdf";
                    CopyRAWtoSDCard(mSongs, path + File.separator + str_song_name);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
       // }
    }


    private void CopyRAWtoSDCard(int id, String path) throws IOException {
        InputStream in = getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }

    private void showFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, Objects.requireNonNull(fragment)).commit();
        mDrawerLayout.closeDrawers();
    }

    private void requestStoragePermissions() {
        ActivityCompat.requestPermissions(
                Objects.requireNonNull(this),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_STORAGE_PERMISSION
        );
    }

    private boolean checkStoragePermissions() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                Objects.requireNonNull(this),
                new String[]{Manifest.permission.CALL_PHONE},
                REQUEST_CALL_PERMISSION
        );
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public void updateDrawerName() {
        tvUserName.setText(sharedPreferencesImp.getUserName());
    }

    void callConfirmationDialog() //confirmation
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent(Intent.ACTION_CALL);

                        intent.setData(Uri.parse("tel:9561255943"));
                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to call?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode!=REQUEST_CALL_PERMISSION)
        {
            showToast(Objects.requireNonNull(this).getResources().getString(R.string.permission_denied));
        }*/

        /*
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    showToast(Objects.requireNonNull(getActivity()).getResources().getString(R.string.permission_denied));
                }

        }*/
    //}



}
