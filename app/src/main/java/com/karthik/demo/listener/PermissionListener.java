package com.karthik.demo.listener;

/**
 * Created by karthik on 20/6/16.
 */
public interface PermissionListener {

    public void onPermission(boolean isPermissionGranted) throws Exception; // false=not granted , true=granted.
}
