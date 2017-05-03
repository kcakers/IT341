package it341.happening;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.util.Log;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.AccessToken;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager callbackManager;
    private GPSMonitor gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button launchBookmarks = (Button)findViewById(R.id.btn_launchBookmarksActivity);
        launchBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritesPressed();
            }
        });

        Button launchFriends = (Button)findViewById(R.id.btn_main_viewFriends);
        launchFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFriends();
            }
        });

        //gps = GPSMonitor.getInstance(this);

        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton)findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email","public_profile");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("DEBUG","FACEBOOK LOGIN SUCCESS");
                handleFacebookAccessToken(loginResult.getAccessToken());
                AppInfo.getInstance().authenticatedUser = true;
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("DEBUG","FACEBOOK LOGIN CANCEL");
                AppInfo.getInstance().authenticatedUser = false;
                FirebaseAuth.getInstance().signOut();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("DEBUG","FACEBOOK LOGIN ERROR");
                AppInfo.getInstance().authenticatedUser = false;
                FirebaseAuth.getInstance().signOut();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("DEBUG", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DEBUG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("DEBUG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        AppInfo.getInstance().user = currentUser;
        AppInfo.getInstance().authenticatedUser = currentUser != null;

        if(AppInfo.getInstance().authenticatedUser) {
            BookmarkManager.getInstance().load();
        }
    }

    public void loginPressed(View view) {
        Log.d("DEBUG", "LoginPressed");

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void mapviewPressed(View view) {
        Log.d("DEBUG", "SearchPressed");

        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void searchPressed(View view) {
        startActivity(new Intent(this, YelpSearchActivity.class));
    }

    public void favoritesPressed() {
        if(AppInfo.getInstance().authenticatedUser) {
            Intent i = new Intent(this, YelpResultActivity.class);
            i.putParcelableArrayListExtra("locations", BookmarkManager.getInstance().getBookmarks());
            startActivity(i);
        } else {
            Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewFriends() {
        if(AppInfo.getInstance().authenticatedUser) {
            Intent i = new Intent(this, ViewFriendsActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
        }
    }

}
