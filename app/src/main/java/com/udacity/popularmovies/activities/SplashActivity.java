package com.udacity.popularmovies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.utils.ProxyHelper;

import org.apache.commons.lang3.StringUtils;

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

    /**
     * Intent to launch {@link MainActivity}.
     */
    private Intent activityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!licenseAPIisValid()) {
            Toast.makeText(this
                    , R.string.SplashActivity_apiValidationErrorToast
                    , Toast.LENGTH_LONG)
                    .show();
            finish();
        } else {
            startMainActivity();
        }
    }

    /**
     * To launch {@link MainActivity}.
     */
    private void startMainActivity() {
        activityIntent = new Intent(this, MainActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        SplashActivity.this.startActivity(activityIntent);
        SplashActivity.this.finish();
    }

    /**
     * Verify if an API key is defined on conf.properties file.
     * @return <code>true</code> if there is a value on conf.properties file.
     */
    private boolean licenseAPIisValid() {
        return !StringUtils.isEmpty(ProxyHelper.WEB_SERVICES_LICENSE);
    }
}
