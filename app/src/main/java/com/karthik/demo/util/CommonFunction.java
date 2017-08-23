package com.karthik.demo.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.greysonparrelli.permiso.Permiso;
import com.karthik.demo.BuildConfig;
import com.karthik.demo.MyApp;
import com.karthik.demo.R;
import com.karthik.demo.constants.MediaConstants;
import com.karthik.demo.constants.PermissionConstants;
import com.karthik.demo.listener.BitmapListener;
import com.karthik.demo.listener.ConfirmListener;
import com.karthik.demo.listener.OkListener;
import com.karthik.demo.listener.PermissionListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.karthik.demo.MyApp.getContext;

/**
 * Created by karthik on 16/12/16.
 */

public class CommonFunction {


    private static AlertDialog alertDialog = null;


    public static void replaceFragment(FragmentManager manager, int fragId, Fragment fragment, boolean addToBackStack) {
        try {
            String backStateName = fragment.getClass().getName();
            String fragmentTag = backStateName;
            boolean fragmentPopped = manager.popBackStackImmediate(fragmentTag, 0);
            if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) {
                FragmentTransaction ft = manager.beginTransaction();
                //ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.pop_in, R.animator.pop_out);
                //ft.setCustomAnimations(R.anim.card_flip_right_in, R.anim.card_flip_right_out, R.anim.card_flip_left_in, R.anim.card_flip_left_out);
                ft.replace(fragId, fragment, fragmentTag);
                if (addToBackStack) {
                    ft.addToBackStack(backStateName);
                }
                ft.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static ProgressDialog getProgressDialog(Context context, boolean isCancellable) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(isCancellable);
        return progressDialog;
    }

    public static void printDebug(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void printError(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void showToast(String message, int length) {
        //if (BuildConfig.DEBUG){
        Toast.makeText(getContext(), message, length).show();
        //}
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void hideKeyboard(Activity activity, View view) {
        // Check if no cityView has focus:
        view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Context context, View view) {
        InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(view, 0);
    }

    public static boolean isValidEmail(String email) {

        /*Pattern pattern;
        Matcher matcher;

        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);

        matcher = pattern.matcher(email);
        return matcher.matches();*/

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidLength(String str, int minLength) {
        boolean isValid = false;
        int currentLength = str.length();
        if (currentLength >= minLength) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static void getPackageHash(Context context) {

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                printError("hash key", Base64.encodeToString(md.digest(),
                        Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Name not found", e.getMessage(), e);

        } catch (NoSuchAlgorithmException e) {
            Log.d("Error", e.getMessage(), e);
        }
    }

    public static void getPermission(final PermissionListener permissionListener) {
        Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
            @Override
            public void onPermissionResult(Permiso.ResultSet resultSet) {
                if (resultSet.areAllPermissionsGranted()) {
                    try {
                        permissionListener.onPermission(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (resultSet.isPermissionPermanentlyDenied(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    try {
                        permissionListener.onPermission(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                Permiso.getInstance().showRationaleInDialog("Permission", "Please give permission to access stogare", "OK", callback);
            }
        }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static void openGallery(Activity activity) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            activity.startActivityForResult(Intent.createChooser(intent, "Choose"), MediaConstants.GALLERY_REQUEST);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            activity.startActivityForResult(Intent.createChooser(intent, "Choose"), MediaConstants.GALLERY_REQUEST);
        }
    }

    public static Uri openCamera(Activity activity, String folderName, String fileName) throws Exception {
        Uri cameraUri;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraUri = Uri.fromFile(createFileWithDate(folderName, fileName));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        activity.startActivityForResult(Intent.createChooser(intent, "Choose"), MediaConstants.CAMERA_REQUEST);
        return cameraUri;
    }

    public static void openDocuments(Activity activity) {

        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        activity.startActivityForResult(Intent.createChooser(intent, "Choose a file to send..."), MediaConstants.DOCUMENT_REQUEST);

    }

    public static void showAlert(Context context, String title, String message, final OkListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!title.isEmpty()) { // Set title if 'title' contains value.
            builder.setTitle(title);
        }
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                alertDialog.dismiss();
                listener.onOK();
            }
        });
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    // This method has 'negative' action (like cancel, no etc)at left & 'positive' (like ok , yes etc)action at right.
    public static void showNegativeConfirmDialog(Context context, String title, String message, String positiveText, String negativeText, final ConfirmListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                listener.onConfirm(false);
            }
        });
        builder.setNegativeButton(positiveText, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                listener.onConfirm(true);
            }
        });
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public static void showConfirmDialog(Context context, String title, String message, String positiveText, String negativeText, final ConfirmListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                listener.onConfirm(true);

            }
        });
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                listener.onConfirm(false);
            }
        });
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }


    public static void appPermissionSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.getPackageName()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, PermissionConstants.REQUEST_APP_INFO_PERMISSION);
    }

    public static File createFileWithDate(String folderName, String fileName) throws Exception {


        String path = Environment.getExternalStorageDirectory() + MediaConstants.APP_DIR + folderName + "/" + fileName + "_" + new Date().getTime() + ".jpeg";
        File file = new File(path);
        file.getParentFile().mkdirs();
        return file;
    }

    public static File createFileWithoutDate(String folderName, String fileName) throws Exception {


        String path = Environment.getExternalStorageDirectory() + MediaConstants.APP_DIR + folderName + "/" + fileName + ".jpeg";
        File file = new File(path);
        file.getParentFile().mkdirs();
        return file;
    }

    public static File createAlbumFile(String folderName, String fileName) throws Exception {
        String path = Environment.getExternalStorageDirectory() + MediaConstants.APP_DIR + folderName + "/" + fileName;
        File file = new File(path);
        file.getParentFile().mkdirs();
        return file;
    }

    public static byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static Uri getBitmapUri(Bitmap bmp) throws Exception {
        Uri bmpUri = null;
        File file = createFileWithDate("shared_post", "shared_post");
        FileOutputStream out = new FileOutputStream(file);
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
        out.close();
        bmpUri = Uri.fromFile(file);
        return bmpUri;
    }

    public static Bitmap getBitmapFromURI(Uri imageUri) throws Exception {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
        return bitmap;
    }

    public static void sharePost(Context context, String shareMessage, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        context.startActivity(Intent.createChooser(intent, "Share post"));
    }

    public static void sharePost(Activity activity, String shareMessage) {
        Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setText(shareMessage)
                .getIntent();

        if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(shareIntent);
        }
    }

    public static String GMTtoLocalTime(String gmtTime) throws Exception {
        Locale currentLocale = getContext().getResources().getConfiguration().locale;
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT' yyyy", currentLocale);
        inputFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy 'at' h:mm a");
        Date date = inputFormat.parse(gmtTime);
        String outputText = outputFormat.format(date);
        return outputText;
    }

    public String localDateTimeToCT(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy '-' mm '-' dd");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        return sdf.format(date.getTime());
    }

    public static String currentDate() {
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy 'at' h:mm a");
        String outputText = outputFormat.format(new Date());
        return outputText;
    }

    public static void protectSensitiveData(View view) {
        // Preventing sensitive view's from getting accessed by Accessibility API.
        ViewCompat.setImportantForAccessibility(view, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
    }

    public static Dialog getCustomProgress(Activity activity, String message, boolean isCancellable) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(activity,R.color.white)));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_working);
        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_loading);


        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.pr_loading);

        // fixes pre-Lollipop progressBar indeterminateDrawable tinting
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        }

        if (!message.isEmpty()) {
            tvMessage.setText(message);
            tvMessage.setVisibility(View.VISIBLE);

        } else {
            tvMessage.setVisibility(View.GONE);
        }

        dialog.setCancelable(isCancellable);
        return dialog;
    }


    public static void sendEmail(Activity activity, String email) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT, "body of email");
        try {
            activity.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            showToast("There are no email clients installed.", 0);
        }
    }

    public static void call(Activity activity, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        activity.startActivity(intent);
    }

    public static void getRoundedImage(String url, final ImageView imageView, int placeHolder) {
        if (url == null || url.isEmpty()) {
            imageView.setImageResource(placeHolder);
        } else {

            Picasso.with(getContext()).load(url).transform(new CircleTransform()).placeholder(placeHolder).into(imageView);
        }
    }

    public static void getBlurredImage(Context context, String url, final ImageView imageView, int placeHolder) {
        if (url.isEmpty()) {
            imageView.setImageResource(placeHolder);
        } else {

            Picasso.with(getContext()).load(url).transform(new BlurTransformation(context)).placeholder(placeHolder).into(imageView);
        }
    }


    public static void getImage(String url, final ImageView imageView, int placeHolder) {
        if (url == null || url.isEmpty()) {
            imageView.setImageResource(placeHolder);
        } else {
            Picasso.with(getContext()).load(url).placeholder(placeHolder).into(imageView);
        }
    }

    public static void getBitmap(String url, final BitmapListener listener) {
        if (url == null || url.isEmpty()) {
        } else {

            Picasso.with(MyApp.getContext()).load(url).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    listener.onBitmapReceived(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    listener.onBitmapReceived(null);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    listener.onBitmapReceived(null);
                }
            });
        }
    }

    public static void getImageFromURL(String url) {
        Picasso.with(MyApp.getContext()).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    public static void getResizedImage(String url, final ImageView imageView, int placeHolder, int width, int height) {
        if (url.isEmpty()) {
            imageView.setImageResource(placeHolder);
        } else {
            Picasso.with(getContext()).load(url).placeholder(placeHolder).resize(width, height).centerCrop().into(imageView);
        }
    }

    public static void getResizedBlurredImage(Context context, String url, final ImageView imageView, int placeHolder, int width, int height) {
        if (url.isEmpty()) {
            imageView.setImageResource(placeHolder);
        } else {
            Picasso.with(getContext()).load(url).transform(new BlurTransformation(context)).placeholder(placeHolder).resize(width, height).centerCrop().into(imageView);
        }
    }


    public static byte[] getBytesFromImageView(ImageView imageView) {
        byte[] imageInByte = null;
        try {
            Bitmap bitmap = getBitmapFromImageview(imageView);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 5, stream);
            imageInByte = stream.toByteArray();

        } catch (Exception e) {
        }
        return imageInByte;
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        byte[] imageInByte = null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 5, stream);
            imageInByte = stream.toByteArray();

        } catch (Exception e) {
        }
        return imageInByte;
    }

    public static Bitmap getBitmapFromBytes(byte bitmapdata[]) {
        return BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
    }

    public static Bitmap getBitmapFromImageview(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        return bitmap;
    }

    public static void startDownload(String fileName, String fileUrl) {
        DownloadManager mManager = (DownloadManager) MyApp.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request mRqRequest = new DownloadManager.Request(Uri.parse(fileUrl));
        mRqRequest.setDescription(fileName);
        //mRqRequest.setDestinationUri(Uri.parse("give your local path"));
        long idDownLoad = mManager.enqueue(mRqRequest);
    }
}
