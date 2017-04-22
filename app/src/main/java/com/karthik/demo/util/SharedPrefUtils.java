package com.karthik.demo.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by karthik on 25-05-2016.
 */
public class SharedPrefUtils {
    private Context context;
    private String SHARED_PREF_FILE_NAME = "MarriagerBussiness";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    // Permission
    public String PERMISSION_LOCATION_COUNT = "permission_location_count"; // keeps track of permission denied count.
    public String PERMISSION_STORAGE_COUNT = "permission_storage_count"; // keeps track of permission denied count.

    // User Details.
    private String USER_ID = "USER_ID";
    private String USER_TOKEN = "USER_TOKEN";
    private String USER_PHOTO = "USER_PHOTO";
    private String FIRST_NAME = "FIRST_NAME";
    private String LAST_NAME = "LAST_NAME";

    // App life cycle
    private String IS_FEED_ACTIVITY_LIVE = "IS_FEED_ACTIVITY_LIVE";
    private String IS_NOTIFICATION_ACTIVITY_LIVE = "IS_NOTIFICATION_ACTIVITY_LIVE";

    // Video download
    private String TOTAL_DOWNLOAD = "TOTAL_DOWNLOAD";
    private String REFERENCE_ID = "REFERENCE_ID";
    private String FILE_DOWNLOADED = "FILE_DOWNLOADED";

    // Album Name
    private String ALBUM_NAME = "ALBUM_NAME";

    // Vendor details
    private String VENDOR_ID = "VENDOR_ID";
    private String VENDOR_CAT_ID = "VENDOR_CAT_ID";
    private String VENDOR_CAT_NAME = "VENDOR_CAT_NAME";
    private String VENDOR_FIRST_NAME = "VENDOR_FIRST_NAME";
    private String VENDOR_LAST_NAME = "VENDOR_LAST_NAME";
    private String VENDOR_EMAIL = "VENDOR_EMAIL";
    private String VENDOR_COMPANY_NAME = "VENDOR_COMPANY_NAME";
    private String VENDOR_IS_VERIFIED = "VENDOR_IS_VERIFIED";
    private String VENDOR_PHOTO = "VENDOR_PHOTO";
    private String VENDOR_BRAND_NAME = "VENDOR_BRAND_NAME";

    // Notes
    private String NOTES = "NOTES";

    public SharedPrefUtils(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }



    public String getNotes() {
        return sharedPreferences.getString(NOTES, "");
    }

    public void setNotes(String notes) {
        editor.putString(NOTES, notes);
        editor.commit();
    }


    public String getUserId() {
        return sharedPreferences.getString(USER_ID, "-1");
    }

    public void setUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.commit();
    }


    public String getVendorId() {
        return sharedPreferences.getString(VENDOR_ID, "");
    }

    public void setVendorId(String vendorId) {
        editor.putString(VENDOR_ID, vendorId);
        editor.commit();
    }

    public String getVendorCatId() {
        return sharedPreferences.getString(VENDOR_CAT_ID, "-1");
    }

    public void setVendorCatId(String vendorCatId) {
        editor.putString(VENDOR_CAT_ID, vendorCatId);
        editor.commit();
    }

    public String getVendorCatName() {
        return sharedPreferences.getString(VENDOR_CAT_NAME, "");
    }

    public void setVendorCatName(String vendorCatName) {
        editor.putString(VENDOR_CAT_NAME, vendorCatName);
        editor.commit();
    }


    public String getVendorBrandName() {
        return sharedPreferences.getString(VENDOR_BRAND_NAME, "");
    }

    public void setVendorBrandName(String vendorBrandName) {
        editor.putString(VENDOR_BRAND_NAME, vendorBrandName);
        editor.commit();
    }

    public String getVendorFirstName() {
        return sharedPreferences.getString(VENDOR_FIRST_NAME, "");
    }

    public void setVendorFirstName(String vendorFirstName) {
        editor.putString(VENDOR_FIRST_NAME, vendorFirstName);
        editor.commit();
    }

    public String getVendorLastName() {
        return sharedPreferences.getString(VENDOR_LAST_NAME, "");
    }

    public void setVendorLastName(String vendorLastName) {
        editor.putString(VENDOR_LAST_NAME, vendorLastName);
        editor.commit();
    }

    public String getVendorEmail() {
        return sharedPreferences.getString(VENDOR_EMAIL, "");
    }

    public void setVendorEmail(String vendorEmail) {
        editor.putString(VENDOR_EMAIL, vendorEmail);
        editor.commit();
    }

    public String getVendorCompanyName() {
        return sharedPreferences.getString(VENDOR_COMPANY_NAME, "");
    }

    public void setVendorCompanyName(String vendorCompanyName) {
        editor.putString(VENDOR_COMPANY_NAME, vendorCompanyName);
        editor.commit();
    }

    public String getVendorIsVerified() {
        return sharedPreferences.getString(VENDOR_IS_VERIFIED, "0");
    }

    public void setVendorIsVerified(String vendorIsVerified) {
        editor.putString(VENDOR_IS_VERIFIED, vendorIsVerified);
        editor.commit();
    }


    public String getUserToken() {
        return sharedPreferences.getString(USER_TOKEN, "");
    }

    public void setUserToken(String userToken) {
        editor.putString(USER_TOKEN, userToken);
        editor.commit();
    }


    public String getVendorPhoto() {
        return sharedPreferences.getString(VENDOR_PHOTO, "");
    }

    public void setVendorPhoto(String vendor_photo) {
        editor.putString(VENDOR_PHOTO, vendor_photo);
        editor.commit();
    }


    public String getUserFirstName() {
        return sharedPreferences.getString(FIRST_NAME, "");
    }

    public void setUserFirstName(String first_name) {
        editor.putString(FIRST_NAME, first_name);
        editor.commit();
    }


    public String getUserLastName() {
        return sharedPreferences.getString(LAST_NAME, "");
    }

    public void setUserLastName(String last_name) {
        editor.putString(LAST_NAME, last_name);
        editor.commit();
    }

    public String getAlbumName() {
        return sharedPreferences.getString(ALBUM_NAME, "");
    }

    public void setAlbumName(String last_name) {
        editor.putString(ALBUM_NAME, last_name);
        editor.commit();
    }


    public boolean getIsNotificationActivityLive() {
        return sharedPreferences.getBoolean(IS_NOTIFICATION_ACTIVITY_LIVE, false);
    }

    public void setNotificationActivityLive(boolean is_live) {
        editor.putBoolean(IS_NOTIFICATION_ACTIVITY_LIVE, is_live);
        editor.commit();
    }

    public void deleteSharePrefKey(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void clearAllSharePref() {
        editor.clear();
        editor.commit();
    }


}
