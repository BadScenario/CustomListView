package com.example.customlistview.models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String price;
    private String photo;

    private String description;

    public Product(String name, String price, String photo, String description) {
        this.name = name;
        this.price = price;
        this.photo = photo;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
