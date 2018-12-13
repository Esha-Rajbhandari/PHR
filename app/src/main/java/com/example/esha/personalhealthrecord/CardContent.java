package com.example.esha.personalhealthrecord;

public class CardContent {

    private int imgResource;
    private String text;
    private int id;

    public CardContent(int imgResource, String text, int id) {
        this.imgResource = imgResource;
        this.text = text;
        this.id = id;
    }


    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
