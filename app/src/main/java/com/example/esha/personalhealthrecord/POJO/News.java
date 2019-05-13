package com.example.esha.personalhealthrecord.POJO;

import java.util.List;

public class News {

    private String news_title;
    private String news_body;
    private String date_published;
    private String author;
    private List<String> images;

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_body() {
        return news_body;
    }

    public void setNews_body(String news_body) {
        this.news_body = news_body;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDate_published() {
        return date_published;
    }

    public void setDate_published(String date_published) {
        this.date_published = date_published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
