package it341.happening;

import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import retrofit2.http.POST;

/**
 * Created by Guzmop on 5/3/17.
 */

public class DatabaseManager {

    private static DatabaseManager sharedInstance;
    private DatabaseReference mDatabase;
    public String user;
    public YelpLocation yelpLocation;

    private DatabaseManager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseManager getInstance() {
        if(sharedInstance == null) {
            sharedInstance = new DatabaseManager();
        }

        return sharedInstance;
    }

    public void checkIn(YelpLocation location) {
        String userId = AppInfo.getInstance().user.getDisplayName();
        User user = new User(location,userId);
        Gson gson = new Gson();
        String json = gson.toJson(location);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d("DEBUG","post success");

                YelpLocation post = dataSnapshot.getValue(YelpLocation.class);
                Log.d("DEBUG",post.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };

        mDatabase.addValueEventListener(postListener);
        mDatabase.child("username").child(userId).child("user").setValue(json);
    }

    public void checkOut() {
        String userId = AppInfo.getInstance().user.getDisplayName();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d("DEBUG","post success");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
            }
        };

        mDatabase.addValueEventListener(postListener);
        mDatabase.child("username").child(userId).child("location").setValue("");
    }

}
