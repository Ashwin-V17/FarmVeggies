package com.shop.veggies.model;

public class SliderListModel {
    private String cid;
    private int image;
    private String sl_id;
    private String sl_image;
    private String sl_name;

    public SliderListModel() {
    }

    public SliderListModel(int image2) {
        this.image = image2;
    }

    public String getSl_name() {
        return this.sl_name;
    }

    public void setSl_name(String sl_name2) {
        this.sl_name = sl_name2;
    }

    public String getSl_image() {
        return this.sl_image;
    }

    public void setSl_image(String sl_image2) {
        this.sl_image = sl_image2;
    }

    public String getSl_id() {
        return this.sl_id;
    }

    public void setSl_id(String sl_id2) {
        this.sl_id = sl_id2;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image2) {
        this.image = image2;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid2) {
        this.cid = cid2;
    }
}
