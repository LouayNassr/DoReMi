package com.example.doremi.Models;

public class Song {

    private int id;
    private String song_ar_title;
    private String song_en_title;
    private String song_url;
    private String song_artist_name;
    private String song_cover_img;
    private String created_at;
    private String updated_at;

    public Song(int id, String song_ar_title, String song_en_title, String song_url, String song_artist_name, String song_cover_img, String created_at, String updated_at) {
        this.id = id;
        this.song_ar_title = song_ar_title;
        this.song_en_title = song_en_title;
        this.song_url = song_url;
        this.song_artist_name = song_artist_name;
        this.song_cover_img = song_cover_img;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Song() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSong_ar_title() {
        return song_ar_title;
    }

    public void setSong_ar_title(String song_ar_title) {
        this.song_ar_title = song_ar_title;
    }

    public String getSong_en_title() {
        return song_en_title;
    }

    public void setSong_en_title(String song_en_title) {
        this.song_en_title = song_en_title;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }

    public String getSong_artist_name() {
        return song_artist_name;
    }

    public void setSong_artist_name(String song_artist_name) {
        this.song_artist_name = song_artist_name;
    }

    public String getSong_cover_img() {
        return song_cover_img;
    }

    public void setSong_cover_img(String song_cover_img) {
        this.song_cover_img = song_cover_img;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
