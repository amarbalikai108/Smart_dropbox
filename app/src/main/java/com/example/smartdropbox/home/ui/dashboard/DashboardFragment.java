package com.example.smartdropbox.home.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.smartdropbox.R;
import com.example.smartdropbox.utils.Constants;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static com.example.smartdropbox.utils.Constants.DASHBOARDPAS;
import static com.example.smartdropbox.utils.Constants.DASHBOARDURL;
import static com.example.smartdropbox.utils.Constants.DASHBOARDUSE;


public class DashboardFragment extends Fragment {

    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root= inflater.inflate(R.layout.fragment_dashboard, container, false);
       WebView webView = root.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        String url = DASHBOARDURL;
        webView.setWebViewClient(new WebViewClient () {

            public void onReceivedHttpAuthRequest(WebView view,
                                                  HttpAuthHandler handler, String host, String realm) {

                handler.proceed(DASHBOARDUSE, DASHBOARDPAS);
            }
        });

        webView.loadUrl(url);
        return root;
    }
}