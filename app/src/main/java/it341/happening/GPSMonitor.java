package it341.happening;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by Guzmop on 3/7/17.
 */

public class GPSMonitor implements LocationListener {

    private LocationManager locationManager = null;
    public Activity activity = null;
    private static GPSMonitor sharedInstance = null;
    public Location location = null;

    private GPSMonitor() {
        requestPermissions();
    }

    public static GPSMonitor getInstance(Activity activity) {
        if(sharedInstance == null) {
            sharedInstance = new GPSMonitor();
        }

        sharedInstance.activity = activity;

        return sharedInstance;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("DEBUG","onRequestPermissionsResult");

        for(int i=0; i<permissions.length; i++) {
            String permission = permissions[i];
            int result = grantResults[i];

            if (result == PackageManager.PERMISSION_GRANTED) {
                Log.d("DEBUG", permission + " granted");
                onGPSPermissionGranted();
            } else {
                Log.d("DEBUG", permission + " denied");
            }
        }
    }

    private void onGPSPermissionGranted() {
        locationManager = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
        LocationListener locationListener = this;

        Log.d("DEBUG","checking permission");
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("DEBUG", "Permission not found");
            return  ;
        }

        //Log.d("DEBUG","proviers: " + locationManager.getAllProviders());
        Log.d("DEBUG","permission granted");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3, locationListener);
    }

    public Location getLastLocation() {
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    // Location Listener functions
    @Override
    public void onLocationChanged(Location location) {
        Log.d("DEBUG", "onLocationChanged");
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("DEBUG", "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("DEBUG", "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("DEBUG", "onProviderDisabled");
    }
}
