package com.teky.tekesports.Network;

import com.teky.tekesports.Components.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lennyhicks on 11/29/16.
 * toornament-android.
 */

public class SessionRequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.header("X-Api-Key", Constants.API_KEY);

//        if (UserStore.getInstance().getToken() != null){
//            builder.header("Authorization", "Bearer " + UserStore.getInstance().getToken());
//        }
        request = builder.build();

        return chain.proceed(request);
    }
}