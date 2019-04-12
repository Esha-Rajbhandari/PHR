package com.example.esha.personalhealthrecord;

import java.util.List;

public class News {

    private String news_title;
    private String news_body;
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
}
