package it341.happening;

// yelp
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Guzmop on 3/1/17.
 */
public class Yelper implements LocationListener {

    YelpAPIFactory apiFactory = null;
    YelpAPI yelpAPI = null;
    Context context;

    public Yelper(Context context) {
        // init yelp
        this.context = context;
        apiFactory = new YelpAPIFactory(
                context.getString(R.string.consumerKey),
                context.getString(R.string.consumerSecret),
                context.getString(R.string.token),
                context.getString(R.string.tokenSecret));
        yelpAPI = apiFactory.createAPI();
    }

    @Override
    public void onLocationChanged(Location loc) {
        //editLocation.setText("");
        //pb.setVisibility(View.INVISIBLE);
        Toast.makeText(
                context,
                "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                        + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + loc.getLongitude();
        Log.v("DEBUG", longitude);
        String latitude = "Latitude: " + loc.getLatitude();
        Log.v("DEBUG", latitude);

        /*------- To get city name from coordinates -------- */
        String cityName = null;
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;
        //editLocation.setText(s);
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
}
