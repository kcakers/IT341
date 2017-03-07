package it341.happening;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}
