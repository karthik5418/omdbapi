package com.karthik.demo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by karthik on 9/2/17.
 */

public class ImageUtils {

    public static Bitmap decodeSampledBimapFromFile(File file, int reqWidth, int reqHeigth) {

        // First decode with inJustDecodeBounds=true to check only dimensions.
        // If inJustDecodeBounds=false , then it will create unnecessary bitmap object which we don't want
        // We are only checking dimensions , we don't wont bitmap object.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);


        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeigth);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getPath(), options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeigth) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;


        if (height > reqHeigth || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeigth && (halfWidth >= reqWidth)) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
