package com.karthik.demo.constants;

import android.graphics.Bitmap;

/**
 * Created by karthik on 6/2/17.
 */

public class Constants {


    // Snackbar time out
    public static final int SNACK_BAR_TIME_OUT = 5000;


    // Feeds click types.
    public static final String ITEM_SHARE = "ITEM_SHARE";
    public static final String ITEM_LIKE = "ITEM_LIKE";
    public static final String ITEM_COMMENT = "ITEM_COMMENT";
    public static final String ITEM_DELETE = "ITEM_DELETE";

    // FeedDetails
    public static final String POST_ID = "POST_ID";

    // Broadcast
    public static final String LIKE_BR = "LIKE_BR";
    public static final String LIKE_COUNT = "LIKE_COUNT";
    public static final String IS_MY_LIKE = "IS_MY_LIKE";
    public static final String COMMENT_BR = "COMMENT_BR";
    public static final String COMMENT_LIST = "COMMENT_LIST";
    public static final String COMMENT_COUNT = "COMMENT_COUNT";

    // Notification
    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    public static final String TITLE = "TITLE";
    public static final String BODY = "BODY";

    // Activity life cycle
    public static final String IS_FEED_ACTIVITY_LIVE = "IS_FEED_ACTIVITY_LIVE";
    public static final String IS_NOTIFICATION_ACTIVITY_LIVE = "IS_NOTIFICATION_ACTIVITY_LIVE";

    // Bitmap
    public static final String IMAGE = "IMAGE";
    public static Bitmap IMAGE_BITMAP = null;


    // GooglePlayService
    public static final int REQUEST_GOOGLE_PLAY_SERVICE = 555;
    public static int PLAY_SERVICE_CODE = 0;
    public static final int REQUEST_LOCATION_ENABLED_CHECK = 666;
    public static String LOCATION = "location";

    // GooglePlaceApi key
    public static String GOOGLE_MAP_SERVER_KEY = "AIzaSyAV4U6jbdkXrpLp1GTf3CFIjZB7A7Q8rQ0";

    // Google Places Link
    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String OUT_JSON = "/json?";
    public static final String PLACES_API_ID = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
    public static final String PLACES_NEAR_BY = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    public static final String PLACES_RADIUS = "&radius=50000";
    public static final String PLACES_SENSOR = "&sensor=true";
    public static final String PLACES_LOCATION = "&location=";
    //public static final String PLACES_TYPES = "&types=art_gallery|bakery|bar|cafe|food|park|restaurant|shopping_mall|university|hospital";
    public static final String PLACES_TYPES = "&types=restaurant";
    public static final String PLACES_KEY = "&key=" + GOOGLE_MAP_SERVER_KEY;
    public static final String INPUT_TYPE = "&input=";
    public static final String RANK_BY = "&rankBy=distance";
    public static final String RESTRICT = "&strictbounds";
    public static final String GEOCODING = "http://maps.google.com/maps/api/geocode/json?sensor=false&address=";


}
