package it341.happening;

/**
 * Created by Guzmop on 5/1/17.
 */

import android.os.Parcelable;
import android.os.Parcel;
import java.util.ArrayList;

public class YelpLocation implements Parcelable {
    public String name;
    public String phone;
    public String url;
    public String id;
    public double rating;
    public double longitude;
    public double latitude;
    public int numFriends;
    public int isClosed;
    //public ArrayList<String> address;

    public YelpLocation() {

    }

    public boolean equals(Object o) {
        if(o == null)
            return false;

        if (getClass() != o.getClass())
            return false;
        YelpLocation other = (YelpLocation) o;

        // field comparison
        return other.name.equals(this.getName());
    }

    public String toString() {
        return name + ", rating: " + rating;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
//        out.writeInt(mData);
        out.writeString(name);
        out.writeString(phone);
        out.writeString(url);
        out.writeString(id);
        out.writeDouble(rating);
        out.writeDouble(longitude);
        out.writeDouble(latitude);
        out.writeInt(numFriends);
        out.writeInt(isClosed);
        //out.writeStringList(address);
    }

    public static final Parcelable.Creator<YelpLocation> CREATOR
            = new Parcelable.Creator<YelpLocation>() {
        public YelpLocation createFromParcel(Parcel in) {
            return new YelpLocation(in);
        }

        public YelpLocation[] newArray(int size) {
            return new YelpLocation[size];
        }
    };

    public YelpLocation(Parcel in) {
//        mData = in.readInt();
        name = in.readString();
        phone = in.readString();
        url = in.readString();
        id = in.readString();
        rating = in.readDouble();
        longitude = in.readDouble();
        latitude = in.readDouble();
        numFriends = in.readInt();
        isClosed = in.readInt();
        //in.readStringList(address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getNumFriends() {
        return numFriends;
    }

    public void setNumFriends(int numFriends) {
        this.numFriends = numFriends;
    }

    public int getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(int isClosed) {
        this.isClosed = isClosed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
