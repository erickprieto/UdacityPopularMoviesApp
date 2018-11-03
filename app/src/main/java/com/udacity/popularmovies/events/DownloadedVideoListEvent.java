package com.udacity.popularmovies.events;

import com.udacity.popularmovies.models.Video;

import java.util.List;

public class DownloadedVideoListEvent {

    private List<Video> videos;

    public DownloadedVideoListEvent(List<Video> videos) {
        this.videos = videos;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
