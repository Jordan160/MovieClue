package com.jvetter2.movieclue;

import android.graphics.Bitmap;

public class Movie {

    private Bitmap image;
    private String name;
    private String description;

    public Bitmap getImage() {
        return image;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
}