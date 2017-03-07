package it341.happening;

// android
import android.content.Context;
import android.location.Location;
import android.util.Log;

// yelp
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    public void search(double latitude, double longitude) {
        Map<String, String> params = new HashMap<>();

        // general params
        params.put("term", "food");
        params.put("limit", "5");

        // locale params
        params.put("lang", "en");

        CoordinateOptions coordinate = CoordinateOptions.builder()
                .latitude(latitude)
                .longitude(longitude).build();

        Call<SearchResponse> call = yelpAPI.search(coordinate, params);
        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                // Update UI text with the searchResponse.
                Log.d("DEBUG","onResponse");
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                // HTTP error happened, do something to handle it.
                Log.d("DEBUG","onFailure");
            }
        };

        call.enqueue(callback);

        try {
            Response<SearchResponse> response = call.execute();
        }catch(Exception ex) {
            Log.d("DEBUG","EXCEPTION");
        }
    }
}
