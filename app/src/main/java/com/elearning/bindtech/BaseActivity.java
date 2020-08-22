package com.elearning.bindtech;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.elearning.bindtech.interfaces.CommonInterface;
import com.elearning.bindtech.ui_activities.LoginActivity;
import com.elearning.bindtech.utils.AppConstants;
import com.elearning.bindtech.utils.DataCache;
import com.elearning.bindtech.utils.DialogManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.regex.Pattern;

import retrofit.RetrofitError;

public class BaseActivity extends AppCompatActivity implements CommonInterface, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    protected static GoogleApiClient mGoogleApiClient;
    protected static LocationRequest mLocationRequest;
    protected static String geoLocation;
    protected static boolean checkForLocation = true;
    protected final int COUNT_DOWN_TIME = 30000;
    protected final int COUNT_DOWN_TIME_INTERVAL = 1000;
    private final String TAG = "mGoogleApiClient";
    public FirebaseAnalytics mFirebaseAnalytics;
    protected DataCache mDataCache;
    private Dialog imageDialog;
    private CountDownTimer mLocationCountDownTimer;

    public static boolean isForeground(Context ctx, String myPackage) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);

        ComponentName componentInfo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            componentInfo = runningTaskInfo.get(0).topActivity;
        }
        return componentInfo.getPackageName().equals(myPackage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataCache = DataCache.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


    }

    public void onRequestSuccess(Object responseObj) {

    }

    public void onRequestFailure(RetrofitError errorCode, String errorFrom) {

        if (errorCode.getCause() instanceof SocketTimeoutException) {
            DialogManager.showToast(this, "Connection Timeout");
        } else {
            DialogManager.showToast(this, "No Internet");
        }
    }

    public void doLogout(String message) {
        DialogManager.showToast(this, message);
        Intent gotoLogin = new Intent(this, LoginActivity.class);
        gotoLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(gotoLogin);
    }

    public void ImageViewer(Context context, final String imgUrl) {

        try {


            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.image_viewer, null);

            ViewGroup root = layout
                    .findViewById(R.id.main_activity_view);

            imageDialog = new Dialog(context,
                    android.R.style.Theme_Translucent_NoTitleBar);
            imageDialog.setContentView(layout);
            ImageView closeBtn = imageDialog
                    .findViewById(R.id.closeBtn);
            ImageView editBtn = imageDialog.findViewById(R.id.editBtn);
            editBtn.setVisibility(View.VISIBLE);
            ImageView save_btn = imageDialog
                    .findViewById(R.id.save_btn);
            ImageView imageview = imageDialog.findViewById(R.id.imageview);


            if (imgUrl != null && !imgUrl.isEmpty()) {
                if (imgUrl.startsWith("/")) {
                    Glide.with(context).load(Uri.fromFile(new File(imgUrl)))
                            .thumbnail(0.5f)
                            .into(imageview);
                } else {
                    Glide.with(context).load(imgUrl)
                            .thumbnail(0.5f)
                            .into(imageview);
                }
            }
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageDialog.dismiss();
                }
            });
            imageDialog.show();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    public void navigateToLogin(Activity mActivity) {
        mActivity.finish();
        Intent gotoLogin = new Intent(mActivity, LoginActivity.class);
        gotoLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(gotoLogin);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (geoLocation != null && geoLocation.length() > 0)
            return;
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startLocationUpdates();
            mLocationCountDownTimer = new CountDownTimer(COUNT_DOWN_TIME, COUNT_DOWN_TIME_INTERVAL) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    stopLocationUpdates();
                    try {
                        Location loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        if (loc != null) {
                            geoLocation = "" + loc.getLatitude() + "," + loc.getLongitude();
                        }
                    } catch (SecurityException e) {

                    }
                }
            };
            mLocationCountDownTimer.start();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location loc) {
        if (loc != null) {
            stopLocationUpdates();
            cancelLocationTimer();
            geoLocation = "" + loc.getLatitude() + "," + loc.getLongitude();


        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
            } catch (SecurityException e) {

            }
        }

    }


    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected())
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
    }


    private void cancelLocationTimer() {
        if (mLocationCountDownTimer != null) {
            mLocationCountDownTimer.cancel();
            mLocationCountDownTimer = null;
        }
    }

    public void resetImage() {

    }


    public boolean matchRegularExp(String scanResult) {

        boolean matched = false;
        boolean regExWithoutDecimal = Pattern.matches(AppConstants.COUPON_CODE_REGULAR_EXP, scanResult);
        boolean regExWithDecimal = Pattern.matches(AppConstants.COUPON_CODE_REGULAR_EXP_DECIMAL, scanResult);

        if (regExWithDecimal || regExWithoutDecimal)
            matched = true;

        return matched;
        // final commit
    }


}