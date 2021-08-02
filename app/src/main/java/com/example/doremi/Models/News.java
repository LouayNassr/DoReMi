package com.example.doremi.Models;

public class News {
    private int id;
    private String coverImage;
    private String title;
    private String newsUrl;
    private String date;

    public News(int id, String coverImage, String title, String newsUrl, String date) {
        this.id = id;
        this.coverImage = coverImage;
        this.title = title;
        this.newsUrl = newsUrl;
        this.date = date;
    }

    public News() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
