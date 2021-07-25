package com.example.doremi.Models;

public class Playlist {

    private String name;
    private String url;
    private String coverImageUrl;
    private String songsCount;

    public Playlist(String name, String url, String coverImageUrl, String songsCount) {
        this.name = name;
        this.url = url;
        this.coverImageUrl = coverImageUrl;
        this.songsCount = songsCount;
    }

    public Playlist() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getSongsCount() {
        return songsCount;
    }

    public void setSongsCount(String songsCount) {
        this.songsCount = songsCount;
    }
}
