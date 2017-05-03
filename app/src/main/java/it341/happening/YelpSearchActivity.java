package it341.happening;

import android.content.Intent;
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
        int limit = 15;

        LatLng ISU = new LatLng(40.5123, -88.9947);
        YelpResult results = yelper.search(type, limit, ISU.latitude, ISU.longitude);
        YelpLocation location = results.locations.get(0);
        Log.d("DEBUG",location.toString());

        Intent i = new Intent(this, YelpResultActivity.class);
        i.putParcelableArrayListExtra("locations",results.locations);
        startActivity(i);
    }

}
