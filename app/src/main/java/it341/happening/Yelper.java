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
public class Yelper {

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
}
