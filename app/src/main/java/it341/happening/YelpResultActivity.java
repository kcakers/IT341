package it341.happening;

import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class YelpResultActivity extends AppCompatActivity {

    private ArrayList<YelpLocation> yelpLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yelp_result);

        populateLocations();
        populateListView();
        clickCallback();
    }

    private void populateLocations() {
        yelpLocations = getIntent().getExtras().getParcelableArrayList("locations");
    }

    private void populateListView() {
        CellAdapter adapter = new CellAdapter();
        ListView list = (ListView)findViewById(R.id.resultListView);
        list.setAdapter(adapter);
    }

    private void clickCallback(){
        ListView list = (ListView)findViewById(R.id.resultListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long ID) {
                YelpLocation location = yelpLocations.get(position);
                Log.d("DEBUG",location.name);
                Toast.makeText(YelpResultActivity.this,location.name,Toast.LENGTH_LONG).show();
            }
        });
    }

    private class CellAdapter extends ArrayAdapter<YelpLocation> {

        public CellAdapter() {
            super(YelpResultActivity.this,R.layout.resultcell,yelpLocations);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.resultcell,parent,false);
            }

            YelpLocation location = yelpLocations.get(position);
            TextView tv = (TextView)itemView.findViewById(R.id.name);
            RatingBar stars = (RatingBar)itemView.findViewById(R.id.ratingBar);

            tv.setText(location.name);
            stars.setRating((float)location.rating);

            return itemView;
        }
    }
}
