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

import java.io.ByteArrayOutputStream;
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

    public void addBookmark(String json, String id) {
        Log.d("DEBUG","adding bookmark");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference jsonRef = storageRef.child("bookmarks/" + id + ".json");
        save(jsonRef,json);
        addBookmarkToList(id);
    }

    private void addBookmarkToList(String id) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String[] currentBookmarks = {id};

        StorageReference ref = storageRef.child("bookmarks.json");
        Gson gson = new Gson();
        String json = gson.toJson(currentBookmarks);
        save(ref,json);
    }

    public void downloadBookmarkIds() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference ref = storageRef.child("bookmarks.json");

        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                try {
                    jsonDownload = new String(bytes, "UTF-8");

                    Log.d("DEBUG","download Success: " + jsonDownload);
                    Gson gson = new Gson();
                    String[] bookmarks = gson.fromJson(jsonDownload,String[].class);

                    for(int i=0; i<bookmarks.length; i++) {
                        Log.d("DEBUG",bookmarks[i]);
                    }

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

    public ArrayList<String> getBookmarks() {
        ArrayList<String> bookmarks = new ArrayList<>();

        Log.d("DEBUG","loading bookmarks");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference folder = storageRef.child("bookmarks");


        return bookmarks;
    }
}
