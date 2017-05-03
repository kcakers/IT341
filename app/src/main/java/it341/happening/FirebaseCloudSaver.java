package it341.happening;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Guzmop on 5/3/17.
 */

public class FirebaseCloudSaver {

    private String jsonDownload;

    public FirebaseCloudSaver() {

    }

    private void save(StorageReference ref, String json) {
        UploadTask uploadTask = ref.putBytes(json.getBytes());
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d("DEBUG","upload failure");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d("DEBUG","upload success: " + downloadUrl.toString());
            }
        });
    }

    public void saveBookmarks(final ArrayList<YelpLocation> locations) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        final StorageReference ref = storageRef.child("bookmark.json");

        // save to json
        Gson gson = new Gson();
        String newJson = gson.toJson(locations);
        save(ref,newJson);
    }

    public void addBookmark(final YelpLocation location) {
        // get current bookmarks
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        final StorageReference ref = storageRef.child("bookmark.json");

        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                try {
                    jsonDownload = new String(bytes, "UTF-8");

                    Log.d("DEBUG","download Success: " + jsonDownload);
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<ArrayList<YelpLocation>>(){}.getType();
                    ArrayList<YelpLocation> bookmarks = gson.fromJson(jsonDownload,collectionType);
                    Log.d("DEBUG",bookmarks.toString());

                    // add the new one
                    bookmarks.add(location);

                    // save to json
                    String newJson = gson.toJson(bookmarks);
                    save(ref,newJson);

                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("DEBUG","download failure");
                ArrayList<YelpLocation> bookmarks = new ArrayList<YelpLocation>();

                // add the new one
                bookmarks.add(location);

                // save to json
                Gson gson = new Gson();
                String newJson = gson.toJson(bookmarks);
                save(ref,newJson);
            }
        });
    }

    public void downloadBookmarks() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference ref = storageRef.child("bookmark.json");

        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                try {
                    jsonDownload = new String(bytes, "UTF-8");

                    Log.d("DEBUG","download Success: " + jsonDownload);
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<ArrayList<YelpLocation>>(){}.getType();
                    ArrayList<YelpLocation> bookmarks = gson.fromJson(jsonDownload,collectionType);
//                    Log.d("DEBUG",bookmarks.toString());

                    BookmarkManager.getInstance().setBookmarks(bookmarks);

                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("DEBUG","download failure");
            }
        });
    }
}
