package com.karthik.demo.constants;

/**
 * Created by karthik on 28/1/17.
 */

public class MediaConstants {
    public static final int GALLERY_REQUEST = 1;
    public static final int CAMERA_REQUEST = 2;
    public static final int DOCUMENT_REQUEST = 3;
    public static final int PHOTO_CROP = 4;

    // Image compress %
    public static final int COMPRESSION_AMOUNT = 50; // keeping 50% of the quality (we compress image when picked from Gallery or Camera to send to server)
    public static final int NO_COMPRESSION=100; // keeping 100% of the quality (we send compressed image to server and that compressed image returned from server)


    //App directory
    public static final String APP_DIR = "/DemoApp/"; // folder name where we keep profile pic & post pic.
    public static final String PROFILE_PHOTO = "ProfilePhoto"; // name of profile pic stored in app private folder.
    public static final String POST_PHOTO = "PostPhoto"; // name of post pic stored in app private folder.
    public static final String SENT_PHOTO = "SentPhoto"; // name of post pic stored in app private folder.
    public static final String ALBUM_PHOTO = "AlbumPhoto"; // name of post pic stored in app private folder.
    public static final String FEATURED_PHOTO = "FeaturedPhoto"; // name of post pic stored in app private folder.
    public static final String ALBUM_EDITED_PHOTO = "AlbumEditedPhoto"; // name of post pic stored in app private folder.
    public static final String SHARE_PHOTO = "SharePhoto"; // name of share pic stored in app private folder.
    public static final String TEMP_PHOTO = "TempPhoto"; // name of share pic stored in app private folder.

}
