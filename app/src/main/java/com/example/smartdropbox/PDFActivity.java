package com.example.smartdropbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

public class PDFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);


      String mFilePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/sample.pdf";
        File file = new File(mFilePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //File file=new File(mFilePath);
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
