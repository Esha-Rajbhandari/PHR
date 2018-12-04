package com.example.esha.personalhealthrecord;

public class CardContent {

    private int imgResource;
    private String text;

    public CardContent(int imgResource, String text) {
        this.imgResource = imgResource;
        this.text = text;
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
}
