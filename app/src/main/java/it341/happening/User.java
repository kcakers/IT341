package it341.happening;

/**
 * Created by Guzmop on 5/2/17.
 */

public class User {

    public YelpLocation location;
    public String id;

    public User(YelpLocation location, String id) {
        this.location = location;
        this.id = id;
    }

    public User() {

    }


    public YelpLocation getLocation() {
        return location;
    }

    public void setLocation(YelpLocation location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return id + ", " + location.toString();
    }





}
