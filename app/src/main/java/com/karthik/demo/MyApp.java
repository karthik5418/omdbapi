package com.karthik.demo;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.karthik.demo.networking.RetrofitClient;
import com.karthik.demo.networking.RetrofitService;
import com.karthik.demo.util.SharedPrefUtils;

/**
 * Created by karthik on 14/4/17.
 */

public class MyApp extends Application {

    private static Context context;
    private static RetrofitService retrofitService;
    private static SharedPrefUtils prefUtils;

    // Display
    private DisplayMetrics metrics;
    private static float mHeight;
    private static float mWidth;


    public static Context getContext() {
        return context;
    }

    public static SharedPrefUtils getSharedPref() {
        return prefUtils;
    }

    public static RetrofitService getRetrofitService() {
        return retrofitService;
    }


    public static int getDeviceWidth() {
        return (int) mWidth;
    }

    public static int getDeviceHeight() {
        return (int) mHeight;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        prefUtils = new SharedPrefUtils(this);
        retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        // Device height, width
        metrics = context.getResources().getDisplayMetrics();
        mHeight = metrics.heightPixels;
        mWidth = metrics.widthPixels;
    }
}
