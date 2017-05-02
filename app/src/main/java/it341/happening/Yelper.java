package it341.happening;

// android
import android.content.Context;
import android.location.Location;
import android.os.StrictMode;
import android.util.Log;

// yelp
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Guzmop on 3/1/17.
 */
public class Yelper {

    private YelpAPIFactory apiFactory = null;
    private YelpAPI yelpAPI = null;
    private Context context;

    public Yelper(Context context) {
        // init yelp
        this.context = context;
        apiFactory = new YelpAPIFactory(
                context.getString(R.string.consumerKey),
                context.getString(R.string.consumerSecret),
                context.getString(R.string.token),
                context.getString(R.string.tokenSecret));
        yelpAPI = apiFactory.createAPI();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public YelpResult search(String type, int limit, double latitude, double longitude) {
        YelpResult result = new YelpResult();
        Map<String, String> params = new HashMap<>();

        // general params
        params.put("term", type);
        params.put("limit", Integer.toString(limit));

        // locale params
        params.put("lang", "en");

        // coords from argument
        CoordinateOptions coordinate = CoordinateOptions.builder()
                .latitude(latitude)
                .longitude(longitude).build();

        Call<SearchResponse> call = yelpAPI.search(coordinate, params);

        try {
            Response<SearchResponse> response = call.execute();
            if(response.isSuccessful()) {
                List<Business> businesses = response.body().businesses();
                for(Business business : businesses) {
                    //Log.d("DEBUG","name: " + business.name());
                    YelpLocation location = new YelpLocation();
                    location.name = business.name();
                    location.rating = business.rating();
                    location.address = business.location().displayAddress();
                    result.locations.add(location);
                }
            }
        }catch(Exception ex) {
            Log.d("DEBUG","EXCEPTION");
            ex.printStackTrace();
        }

        return result;
    }
}
