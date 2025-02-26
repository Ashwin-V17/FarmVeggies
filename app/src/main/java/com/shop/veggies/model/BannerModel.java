package com.shop.veggies.model;

public class BannerModel {
    private String banner_id;
    private String banner_image;
    private String banner_title;

    public String getBanner_image() {
        return this.banner_image;
    }

    public void setBanner_image(String banner_image2) {
        this.banner_image = banner_image2;
    }

    public String getBanner_id() {
        return this.banner_id;
    }

    public void setBanner_id(String banner_id2) {
        this.banner_id = banner_id2;
    }

    public String getBanner_title() {
        return this.banner_title;
    }

    public void setBanner_title(String banner_title2) {
        this.banner_title = banner_title2;
    }
}
