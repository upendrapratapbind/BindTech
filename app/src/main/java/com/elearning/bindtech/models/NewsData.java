package com.elearning.bindtech.models;

public class NewsData {
    private String title;
    private String author;
    private String url;
    private String urlToImage;

    public NewsData(String title, String author, String url, String urlToImage) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.urlToImage = urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return urlToImage;
    }

    public void setImageUrl(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}
