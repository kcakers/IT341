package it341.happening;

// android
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.support.v4.app.FragmentActivity;
import android.view.View;

// maps
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// yelp
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;

import java.util.List;


public class SearchActivity extends FragmentActivity implements OnMapReadyCallback {

    private class ZoomLevel {
        final static int WORLD = 1;
        final static int CONTINENT = 5;
        final static int CITY = 10;
        final static int STREETS = 15;
        final static int BUILDINGS = 20;
    }

    GoogleMap map = null;
    YelpAPI yelp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // get map fragment and call async to start getting updates
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // create the yelp api
        YelpAPIFactory yelpFactory = new YelpAPIFactory(
                getString(R.string.consumerKey),
                getString(R.string.consumerSecret),
                getString(R.string.token),
                getString(R.string.tokenSecret));
        yelp = yelpFactory.createAPI();
    }

    public void onMapReady(GoogleMap map) {
        Log.d("DEBUG", "onMapReady");
        this.map = map;

        home(null);
    }

    public void home(View view) {
        if (map == null) return;

        // ISU position and zoom level
        LatLng ISU = new LatLng(40.5123, -88.9947);
        int zoom = ZoomLevel.CITY;

        // create a marker and the location update object
        MarkerOptions ISUMarker = new MarkerOptions().position(ISU).title("ISU");
        CameraUpdate camLocation = CameraUpdateFactory.newLatLngZoom(ISU, zoom);

        // apply
        map.addMarker(ISUMarker);
        map.animateCamera(camLocation);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
    }

    public void searchNearMe(View view) {
        requestPermissions();
    }

    @Override
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
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("DEBUG", "onLocationChanged");
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
        };

        Log.d("DEBUG","checking permission");
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("DEBUG", "Permission not found");
            return  ;
        }

        //Log.d("DEBUG","proviers: " + locationManager.getAllProviders());

        Log.d("DEBUG","permission granted");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3, locationListener);
    }
}
