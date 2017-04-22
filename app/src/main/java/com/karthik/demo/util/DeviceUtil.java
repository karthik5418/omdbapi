package com.karthik.demo.util;

import android.os.Build;

/**
 * Created by karthik on 16/12/16.
 */

public class DeviceUtil {

    // OS , Make , Model
    public static final String OS = Build.VERSION.RELEASE;
    public static final String MAKE = Build.MANUFACTURER;
    public static final String MODEL = Build.MODEL;
}
