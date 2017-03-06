package it341.happening;

// android

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.Toast;

// maps
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// yelp
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


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

    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

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

    public void searchNearMe(View view) {



        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

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
        };

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                Log.d("DEBBUG","requestPermissions");
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET}, 10);
                return;
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
        } else {
            Log.d("DEBBUG","else requestLocationUpdates");
            locationManager.requestLocationUpdates("gps", 5000, 3, locationListener);
        }


    }

    public void onRequestResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("DEBUG","onRequestPermissionResult");
    }
}
