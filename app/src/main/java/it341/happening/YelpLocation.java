package it341.happening;

/**
 * Created by Guzmop on 5/1/17.
 */

import android.os.Parcelable;
import android.os.Parcel;
import java.util.ArrayList;

public class YelpLocation implements Parcelable {
    public String name;
    public double rating;
    public double longitude;
    public double latitude;
    public int numFriends;
    public ArrayList<String> address;

    public YelpLocation() {

    }

    public String toString() {
        return name + " " + address + ", rating: " + rating;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
//        out.writeInt(mData);
        out.writeString(name);
        out.writeDouble(rating);
        out.writeDouble(longitude);
        out.writeDouble(latitude);
        out.writeInt(numFriends);
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
        rating = in.readDouble();
        longitude = in.readDouble();
        latitude = in.readDouble();
        numFriends = in.readInt();
        //in.readStringList(address);
    }

}
