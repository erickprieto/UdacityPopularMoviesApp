package com.udacity.popularmovies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.udacity.popularmovies.R;

/**
 * Presentation of this App.
 * @author Erick Prieto
 * @since 2018
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent activityIntent = new Intent(this, MainActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        SplashActivity.this.startActivity(activityIntent);
        SplashActivity.this.finish();
    }
}
