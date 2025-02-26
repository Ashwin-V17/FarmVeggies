package com.shop.veggies.model;

public class CategoryModel {
    private String category_id;
    private String category_image;
    private String category_name;
    private int cimage;
    private String scid;
    private String subcategoryname;

    public CategoryModel() {
    }

    public CategoryModel(String category_name2, int cimage2) {
        this.category_name = category_name2;
        this.cimage = cimage2;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public void setCategory_name(String category_name2) {
        this.category_name = category_name2;
    }

    public String getCategory_image() {
        return this.category_image;
    }

    public void setCategory_image(String category_image2) {
        this.category_image = category_image2;
    }

    public String getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(String category_id2) {
        this.category_id = category_id2;
    }

    public int getCimage() {
        return this.cimage;
    }

    public void setCimage(int cimage2) {
        this.cimage = cimage2;
    }

    public String getScid() {
        return this.scid;
    }

    public void setScid(String scid2) {
        this.scid = scid2;
    }

    public String getSubcategoryname() {
        return this.subcategoryname;
    }

    public void setSubcategoryname(String subcategoryname2) {
        this.subcategoryname = subcategoryname2;
    }
}
