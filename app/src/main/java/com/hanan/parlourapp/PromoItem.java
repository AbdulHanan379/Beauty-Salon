package com.hanan.parlourapp;



public class PromoItem {
    private final String text;
    private final int imageResId;

    public PromoItem(String text, int imageResId) {
        this.text = text;
        this.imageResId = imageResId;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }
}
