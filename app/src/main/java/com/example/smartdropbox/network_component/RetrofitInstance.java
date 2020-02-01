package com.example.smartdropbox.network_component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitInstance
{


    public static Retrofit getRetrofitInstance(String baseUrl)
    {
        Retrofit retrofit;
        Gson gson=new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-mm-dd, HH:mm:ss")
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Content-Type", "application/json").build();
                return chain.proceed(request);
            }
        });
         retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(baseUrl).client(httpClient.build()).build();
        return retrofit;

    }


}
