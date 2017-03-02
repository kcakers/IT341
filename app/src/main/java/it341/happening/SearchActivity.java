package it341.happening;

// android
import android.os.Bundle;
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



public class SearchActivity extends FragmentActivity implements OnMapReadyCallback {

    private class ZoomLevel {
        final static int WORLD = 1;
        final static int CONTINENT = 5;
        final static int CITY = 10;
        final static int STREETS = 15;
        final static int BUILDINGS = 20;
    }

    GoogleMap map = null;
    YelpAPIFactory apiFactory = null;
    YelpAPI yelpAPI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // get map fragment and call async to start getting updates
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // init yelp
        apiFactory = new YelpAPIFactory(
                getString(R.string.consumerKey),
                getString(R.string.consumerSecret),
                getString(R.string.token),
                getString(R.string.tokenSecret));
        yelpAPI = apiFactory.createAPI();
    }

    public void onMapReady(GoogleMap map) {
        Log.d("DEBUG", "onMapReady");
        this.map = map;

        home(null);
    }

    public void home(View view) {
        if(map == null) return;

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
}
