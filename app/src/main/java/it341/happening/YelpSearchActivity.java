package it341.happening;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

public class YelpSearchActivity extends AppCompatActivity {

    private Yelper yelper = null;
    private EditText typeEditBox = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yelp_search);

        yelper = new Yelper(this);
        typeEditBox = (EditText)findViewById(R.id.searchType);
    }

    public void search(View view) {
        String type = typeEditBox.getText().toString();
        int limit = 5;
        LatLng loc = new LatLng(40.5123, -88.9947);
        YelpResult results = yelper.search("Bar", limit, loc.latitude, loc.longitude);
        for(YelpLocation location : results.locations) {
            Log.d("DEBUG",location.toString());
        }
    }

}
