package com.udacity.popularmovies.utils;


/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class UrlValidator {

    public static final String PREFIX_HTTP = "http://";
    public static final String PREFIX_HTTPS = "https://";

    private UrlValidator() {
    }

    public static String validate(String url) {

        if (!url.startsWith(PREFIX_HTTP) && !url.startsWith(PREFIX_HTTPS)) {
            url = PREFIX_HTTP + url;
        }

        return url;
    }
}
