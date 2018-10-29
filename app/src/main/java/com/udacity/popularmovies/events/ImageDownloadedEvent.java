package com.udacity.popularmovies.events;

import android.graphics.Bitmap;

public class ImageDownloadedEvent {

    private int imageId;
    private String url;
    private Bitmap poster;

    public ImageDownloadedEvent(int imageId, String url, Bitmap poster) {
        this.imageId = imageId;
        this.url = url;
        this.poster = poster;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }
}
