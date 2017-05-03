package it341.happening;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Guzmop on 5/2/17.
 */

public class AppInfo {

    private static AppInfo sharedInstance;

    public boolean authenticatedUser = false;
    public YelpLocation checkedInLocation;
    public boolean checkedIn = false;
    public FirebaseUser user;

    public static AppInfo getInstance() {
        if(sharedInstance == null) {
            sharedInstance = new AppInfo();
        }

        return sharedInstance;
    }

    private AppInfo() {

    }

}
