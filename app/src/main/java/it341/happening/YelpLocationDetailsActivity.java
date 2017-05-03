package it341.happening;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class YelpLocationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yelp_location_details);

        final YelpLocation location = (YelpLocation)getIntent().getParcelableExtra("location");
        TextView title = (TextView)findViewById(R.id.title);
        TextView rating = (TextView)findViewById(R.id.rating);
        TextView phone = (TextView)findViewById(R.id.phone);
        TextView open = (TextView)findViewById(R.id.openHours);
        TextView numFriends = (TextView)findViewById(R.id.numFriends);
        Button yelpBtn = (Button)findViewById(R.id.linkBtn);
        Button addFav = (Button)findViewById(R.id.btn_AddToFav);
        Button checkIn = (Button)findViewById(R.id.btn_checkIn);

        title.setText(location.name);
        rating.setText("Rating: " + String.valueOf(location.rating));
        phone.setText("Phone: " + location.phone);
        numFriends.setText("Friends: " + String.valueOf(0));
        open.setText(location.isClosed == 1 ? "closed" : "open");

        yelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(location.url));
                startActivity(browserIntent);
            }
        });

        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppInfo.getInstance().authenticatedUser) {
                    // add to favs
                }
            }
        });

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppInfo.getInstance().authenticatedUser) {
                    AppInfo.getInstance().checkedInLocation = location;
                }
            }
        });
    }
}
