package com.udacity.popularmovies;

import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.os.IBinder;
import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.udacity.popularmovies.activities.SplashActivity;
import com.udacity.popularmovies.services.PopularMoviesDatabaseService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Hold all configurations over all time on this Application.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class PopularMoviesApplication extends Application {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = PopularMoviesApplication.class.getSimpleName();

    /**
     * Contains {@link String} PATH to properties file {@link Properties} of this application.
     */
    private static final String PATH_FILE_PROPERTIES = "system/conf.properties";

    /**
     * Instance of {@link Context} that hold this application context.
     */
    private static PopularMoviesApplication context;

    /**
     * Configuration Data of this application, like users of webservices and databases.
     */
    private static Properties configurationProperties;

    /**
     * Uses notification otto event bus Framework. Otto avoid uses callbacks,
     * that is deacopuple publisher and subscribers events.
     * {@link ThreadEnforcer#ANY} establish publish events on One thread and the subscribers on another.
     * Publishes events on background thread and subscribers on UI thread.
     */
    private static Bus eventBus;

    private Intent databaseIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        this.databaseIntent  = new Intent(context, PopularMoviesDatabaseService.class);
        startService(this.databaseIntent);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopService(this.databaseIntent);
    }


    /**
     * Obtain configuration Data of this application, like users of webservices and databases.
     *
     * @return Configuration Data of this application, like users of webservices and databases.
     */
    public Properties getConfigurationProperties() {
        if(configurationProperties != null) { return  configurationProperties; }
        else { return providesProperties(); }
    }

    /**
     * Obtain the file that contains configuration of system,
     * file is located on: {@link #PATH_FILE_PROPERTIES}.
     * @return {@link Properties} configurations of App.
     */
    private Properties providesProperties() {
        Properties properties = new Properties();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(PATH_FILE_PROPERTIES);
            if (inputStream != null) {
                properties.load(inputStream);
                inputStream.close();
            } else {
                Log.wtf(TAG, "Was Impossible read configuration file.");
            }
        } catch (IOException e) {
            Log.e(TAG, "Could not open file", e);
        }
        Log.v(TAG, properties.toString());
        configurationProperties = properties;
        return configurationProperties;
    }

    /**
     * Obtain the value contained on the {@link #configurationProperties} for a specific key.
     *
     * @param key id for desired value.
     * @return value found.
     */
    public static String getAtributoConfiguracion(String key) {
        String atributo = context.getConfigurationProperties().getProperty(key);
        return atributo;
    }

    /**
     * Gets otto event bus. Singleton Object.
     *
     * @return Otto event bus.
     */
    public static Bus getEventBus() {
        if (eventBus == null) {
            eventBus = new Bus(ThreadEnforcer.ANY);
        }
        return eventBus;
    }

}
