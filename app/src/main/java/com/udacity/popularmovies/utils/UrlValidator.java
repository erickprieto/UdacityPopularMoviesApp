package com.udacity.popularmovies.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * Validate if its defined a HTTP or HTTPS valid address.
 * This class only provide utilitary methods.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class UrlValidator {

    private static final String PREFIX_HTTP = "http://";
    private static final String PREFIX_HTTPS = "https://";

    /**
     * Only static methods allowed.
     */
    private UrlValidator() {
    }

    /**
     * Validate if this {@param url} begins with {@link UrlValidator#PREFIX_HTTP}
     * or {@link UrlValidator#PREFIX_HTTPS}.
     * if its not complete, this method complete web address.
     * It does not validate if its a valid {@link java.net.URL}
     * Null safe.
     *
     * @param url <c>String</c> url parsed.
     * @return <c>String</c> Complete URL.
     */
    public static String validate(String url) {
        if(StringUtils.isEmpty(url)) { return null;}
        if (!url.startsWith(PREFIX_HTTP) && !url.startsWith(PREFIX_HTTPS)) {
            url = PREFIX_HTTP + url;
        }

        return url;
    }
}
