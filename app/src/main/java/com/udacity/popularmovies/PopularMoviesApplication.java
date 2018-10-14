package com.udacity.popularmovies;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

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

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
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
}