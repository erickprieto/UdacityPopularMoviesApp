package com.udacity.popularmovies.utils;


import android.net.Uri;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.net.URL;

/**
 * Validate if its defined a HTTP or HTTPS valid address.
 * This class only provide utilitary methods.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class UrlValidator {

    /**
     * Name of reference to log all records of events in this class.
     */
    private static final String TAG = UrlValidator.class.getSimpleName();

    private static final String PREFIX_HTTP = "http";
    private static final String PREFIX_HTTPS = "https";
    private static final String PREFIX_HTTP_PROTOCOL = PREFIX_HTTP + "://";
    private static final String PREFIX_HTTPS_PROTOCOL = PREFIX_HTTPS + "://";

    /**
     * Only static methods allowed.
     */
    private UrlValidator() {
    }

    /**
     * Validate if this {@param url} begins with {@link UrlValidator#PREFIX_HTTP_PROTOCOL}
     * or {@link UrlValidator#PREFIX_HTTPS_PROTOCOL}.
     * Default is {@link UrlValidator#PREFIX_HTTP_PROTOCOL}.
     * Null safe.
     *
     * @param url <c>String</c> url parsed.
     * @return <c>String</c> Complete URL.
     */
    private static String validateProtocol(String url) {
        if (!url.startsWith(PREFIX_HTTP_PROTOCOL) && !url.startsWith(PREFIX_HTTPS_PROTOCOL)) {
            url = PREFIX_HTTP_PROTOCOL + url;
        }
        return url;
    }

    /**
     * Validate if this {@param url} begins with {@link UrlValidator#PREFIX_HTTP_PROTOCOL}
     * or {@link UrlValidator#PREFIX_HTTPS_PROTOCOL} and if its a valid {@link Uri}.
     * Null safe.
     *
     * @param uri <c>String</c> url parsed.
     * @return <c>String</c> Complete URL.
     */
    public static String validate(String uri) {
        if(StringUtils.isEmpty(uri)) { return null;}

        try {
            URL url = new URL(validateProtocol(uri));
            if (url.getProtocol().equals(PREFIX_HTTP) || url.getProtocol().equals(PREFIX_HTTPS)) {
                return url.toString();
            } else {
                throw new IllegalArgumentException("Not a valid protocol: " + PREFIX_HTTP + " or " + PREFIX_HTTPS);
            }
        } catch (Exception ex) {
            Log.v(TAG, "Url missed: + " + uri + ex.getMessage());
            return null;
        }


    }
}
