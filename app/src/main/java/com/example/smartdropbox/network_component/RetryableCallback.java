package com.example.smartdropbox.network_component;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract class RetryableCallback<T> implements Callback {
    private int totalRetries = 3;
    private Call<T> call = null;
    private int retryCount = 0;

    @Override
    public void onResponse(Call call, Response response) {
        try {
            onFinalResponse(call, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(Call call, Throwable t) {
        this.call = call;
        if (retryCount++ < totalRetries) {
            retry();
        } else
            onFinalFailure(call, t);
    }
     void onFinalResponse(Call<T> call,  Response<T> response) {
    }

     void onFinalFailure(Call<T> call,  Throwable t) {}

    private void retry() {
        call.clone().enqueue(this);

    }
}
