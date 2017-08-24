package com.karthik.demo.app.location;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;
import com.greysonparrelli.permiso.Permiso;
import com.karthik.demo.MyApp;
import com.karthik.demo.R;
import com.karthik.demo.app.location.model.Result;
import com.karthik.demo.constants.Constants;
import com.karthik.demo.listener.OnTryAgainListener;
import com.karthik.demo.listener.PermissionListener;
import com.karthik.demo.util.CommonFunction;
import com.karthik.demo.util.ErrorUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by karthik on 23/8/17.
 */

public class LocationActivity extends AppCompatActivity implements Location_MVP.View, View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, OnTryAgainListener {

    private static final String TAG = "LocationActivity";


    // View
    private RecyclerView rvNearByPlaces;
    private View inLoader;
    private View inError;
    private TextView tvTitle;
    private RelativeLayout rlRoot;
    private Spinner spinnerType;


    // Google Client

    private GoogleApiClient apiClient;
    private GoogleApiAvailability apiAvailability;
    private LocationSettingsRequest.Builder settingsRequest;
    private LocationRequest locationRequest;
    private PendingResult<LocationSettingsResult> pendingResult;
    private int locationPermission;


    private double mLatitude = 0, mLongitude = 0;
    private Location mLocation;
    Status status;
    private Message message;

    private LocationPresenter mPresenter;

    private LocationAdapter adapter;
    private List<Result> list;
    private Result model;

    private boolean isSpinnerTouched = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        findViews();

        init();

    }

    private void findViews() {
        inLoader = findViewById(R.id.in_loader);
        inError = findViewById(R.id.in_error);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("Location");
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        spinnerType = (Spinner) findViewById(R.id.sp_type);


        rvNearByPlaces = (RecyclerView) findViewById(R.id.rv_places);
        rvNearByPlaces.setLayoutManager(new LinearLayoutManager(this));
    }

    private void init() {

        mPresenter = new LocationPresenter(this);


        // Creating adapter for spinner
        List<String> type = new ArrayList<>(3);
        type.add("Sort by : Distance");
        type.add("Sort by : Rating");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(dataAdapter);

        spinnerType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched = true;
                return false;
            }
        });


        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (isSpinnerTouched) {
                    if (position == 0) {

                        Collections.sort(list, new Comparator<Result>() {
                            @Override
                            public int compare(Result o1, Result o2) {
                                Double distance1 = Double.valueOf(o1.getDistance());
                                Double distance2 = Double.valueOf(o2.getDistance());
                                if (distance1.compareTo(distance2) < 0) {
                                    return -1;
                                } else if (distance1.compareTo(distance2) > 0) {
                                    return 1;
                                } else {
                                    return 0;
                                }

                            }
                        });

                    } else {


                        Collections.sort(list, new Comparator<Result>() {
                            @Override
                            public int compare(Result o1, Result o2) {
                                Double rating1 = Double.valueOf(o1.getRating());
                                Double rating2 = Double.valueOf(o2.getRating());
                                if (rating1.compareTo(rating2) < 0) {
                                    return -1;
                                } else if (rating1.compareTo(rating2) > 0) {
                                    return 1;
                                } else {
                                    return 0;
                                }

                            }
                        });

                    }

                    isSpinnerTouched = false;
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initGoogleMapDataApi();
        checkGooglePlayApiAvailability();

    }


    private void initGoogleMapDataApi() {
        apiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        // setting parameters for location

       /*  PRIORITY_NO_POWER (passively listens for location updates from other clients)
        PRIORITY_LOW_POWER (~10km "city" accuracy)
        PRIORITY_BALANCED_POWER_ACCURACY (~100m "block" accuracy)
        PRIORITY_HIGH_ACCURACY (accurate as possible at the expense of battery life) */

        locationRequest = new LocationRequest();
        locationRequest.setFastestInterval(0);
        locationRequest.setInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);

        // building SettingApi for device state check (whether we have all necessary setting enabled in device)
        settingsRequest = new LocationSettingsRequest.Builder();
        settingsRequest.addLocationRequest(locationRequest);
        settingsRequest.setAlwaysShow(true); // This will hide 'Never' option from system dialog.
        settingsRequest.setNeedBle(
                true);//If the client is using BLE (Bluetooth Low Energy)scans to derive location, it can request that BLE be enabled by calling setNeedBle(boolean)
    }


    private void checkGooglePlayApiAvailability() {
        apiAvailability = GoogleApiAvailability.getInstance();
        Constants.PLAY_SERVICE_CODE = apiAvailability.isGooglePlayServicesAvailable(this);
        if (Constants.PLAY_SERVICE_CODE == ConnectionResult.SUCCESS) {
            apiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        } else {
            apiAvailability.showErrorDialogFragment(this, Constants.PLAY_SERVICE_CODE,
                    Constants.REQUEST_GOOGLE_PLAY_SERVICE);
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        mLocation = location;
        mLatitude = mLocation.getLatitude();
        mLongitude = mLocation.getLongitude();
        //commonFunction.showToast("OnLocationChange called", Toast.LENGTH_LONG);
        stopLocationUpdates(); // when done with mLocation , just stop the location updates
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.LOCATION, location);
        message = handler.obtainMessage();
        message.setData(bundle);
        handler.sendMessage(message);


    }


    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (message != null) {
                mLocation = msg.getData().getParcelable(Constants.LOCATION);
                mPresenter.loadNearByPlaces(mLatitude, mLongitude);
            }
        }
    };


    private void initLocationSettingCheck() {
        // check the device state , whether we have location setting enabled.
        pendingResult =
                LocationServices.SettingsApi.checkLocationSettings(apiClient, settingsRequest.build());
        pendingResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                status = result.getStatus();
                final LocationSettingsStates settingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here.
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.

                        CommonFunction.printError(TAG, "RESOLUTION_REQUIRED");

                        try {


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        ErrorUtil.showNetworkError(rlRoot, inError, LocationActivity.this);
                        break;
                }
            }
        });
    }


    private void startLocationUpdates() {

        CommonFunction.getPermission(new PermissionListener() {
            @Override
            public void onPermission(boolean isPermissionGranted) throws Exception {

                if (isPermissionGranted) {

                    LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locationRequest, LocationActivity.this);

                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // forward the results of this method to Permiso
        Permiso.getInstance().onRequestPermissionResult(requestCode, permissions, grantResults);
    }


    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, LocationActivity.this);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void showProgress() {
        ErrorUtil.showInsetLoader(rlRoot, inLoader);
    }

    @Override
    public void hideProgress() {
        ErrorUtil.hideInsetLoader(rlRoot, inLoader);
    }

    @Override
    public void showNetworkError() {

        ErrorUtil.showNetworkError(rlRoot, inError, this);
    }

    @Override
    public void showTimeOutError() {

        ErrorUtil.showNetworkError(rlRoot, inError, this);
    }

    @Override
    public void showServerError() {

        ErrorUtil.showServerError(rlRoot, inError, this);
    }

    @Override
    public void showFailure(Object object) {

        ErrorUtil.showServerError(rlRoot, inError, this);
    }

    @Override
    public void showNoResult(Object object) {
        ErrorUtil.showNoResultsError(rlRoot, inError, (String) object, R.drawable.ic_error);
    }

    @Override
    public void showSuccess(Object object) {

        list = (List<Result>) object;


        for (int i = 0; i < list.size(); i++) {

            String distance = CommonFunction.getDistance(mLatitude, mLongitude, list.get(i).getGeometry().getLocation().getLat(), list.get(i).getGeometry().getLocation().getLng());

            list.get(i).setDistance(distance);
        }

        adapter = new LocationAdapter(this, list, MyApp.getDeviceHeight());
        rvNearByPlaces.setAdapter(adapter);

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        initLocationSettingCheck();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onStart() {
        super.onStart();
        if (apiClient != null) {
            apiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (apiClient != null && apiClient.isConnected()) {
            //apiClient.stopAutoManage(getActivity());
            apiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Permiso.getInstance().setActivity(this);
    }

    @Override
    public void onTryAgain() {
        ErrorUtil.hideInsetError(rlRoot, inError);
        mPresenter.loadNearByPlaces(mLatitude, mLongitude);
    }
}
