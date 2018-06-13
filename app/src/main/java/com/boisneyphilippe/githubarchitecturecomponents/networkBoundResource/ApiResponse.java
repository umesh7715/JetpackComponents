package com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import java.io.IOException;

import retrofit2.Response;

public class ApiResponse<T> {

    public final int code;
    @Nullable
    public final LiveData<T> body;
    @Nullable
    public final String errorMessage;

    public ApiResponse(Throwable error) {
        code = -1;
        body = null;
        if (error instanceof IOException) {
            errorMessage = "No network error";
        } else {
            errorMessage = error.getMessage();
        }
    }

    public ApiResponse(Response<LiveData<T>> response) {
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }
}