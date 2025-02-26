package com.shop.veggies.model;

public class ApiSliderItem {
    private String banner_id;
    private String banner_image;

    public ApiSliderItem(String banner_id, String banner_image) {
        this.banner_id = banner_id;
        this.banner_image = banner_image;
    }

    public String getBannerId() {
        return banner_id;
    }

    public void setBannerId(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getBannerImage() {
        return banner_image;
    }

    public void setBannerImage(String banner_image) {
        this.banner_image = banner_image;
    }
}
