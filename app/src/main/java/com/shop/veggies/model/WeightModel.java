package com.shop.veggies.model;

public class WeightModel {
    private String mrp;
    private String option_role;
    private String points;
    private String web_price;
    private String web_title;
    private String wid;

    public WeightModel(String web_title2) {
        this.web_title = web_title2;
    }

    public WeightModel() {
    }

    public String getWeb_title() {
        return this.web_title;
    }

    public void setWeb_title(String web_title2) {
        this.web_title = web_title2;
    }

    public String getWeb_price() {
        return this.web_price;
    }

    public void setWeb_price(String web_price2) {
        this.web_price = web_price2;
    }

    public String getWid() {
        return this.wid;
    }

    public void setWid(String wid2) {
        this.wid = wid2;
    }

    public String getPoints() {
        return this.points;
    }

    public void setPoints(String points2) {
        this.points = points2;
    }

    public String getOption_role() {
        return this.option_role;
    }

    public void setOption_role(String option_role2) {
        this.option_role = option_role2;
    }

    public String getMrp() {
        return this.mrp;
    }

    public void setMrp(String mrp2) {
        this.mrp = mrp2;
    }
}
