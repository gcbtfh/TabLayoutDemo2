package com.example.tonghung.tablayoutdemo.object;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by tonghung on 30/12/2016.
 */

public class Product {
    private int id;
    private String name;
    private int cate;
    private int price;
    private Bitmap image;
    private Date date;

    public Product() {
    }

    public Product(int id, String name, int cate, int price, Bitmap image, Date date) {
        this.id = id;
        this.name = name;
        this.cate = cate;
        this.price = price;
        this.image = image;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
