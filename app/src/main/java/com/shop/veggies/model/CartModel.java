package com.shop.veggies.model;

public class CartModel {
    private String cart_id;
    private String delivery_charge;
    private String delivery_id;
    private String delivery_time;
    private int pImage;
    private String point;
    private String product_id;
    private String product_image;
    private String product_name;
    private String product_price;
    private String product_quantity;
    private String product_weight;
    private String scid;
    private String vid;

    public CartModel() {
    }

    public CartModel(String product_name2, String product_price2, String product_quantity2, String product_weight2, int pImage2) {
        this.product_name = product_name2;
        this.product_price = product_price2;
        this.product_quantity = product_quantity2;
        this.product_weight = product_weight2;
        this.pImage = pImage2;
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid2) {
        this.vid = vid2;
    }

    public String getScid() {
        return this.scid;
    }

    public void setScid(String scid2) {
        this.scid = scid2;
    }

    public String getDelivery_id() {
        return this.delivery_id;
    }

    public void setDelivery_id(String delivery_id2) {
        this.delivery_id = delivery_id2;
    }

    public String getDelivery_time() {
        return this.delivery_time;
    }

    public void setDelivery_time(String delivery_time2) {
        this.delivery_time = delivery_time2;
    }

    public String getCart_id() {
        return this.cart_id;
    }

    public void setCart_id(String cart_id2) {
        this.cart_id = cart_id2;
    }

    public String getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(String product_id2) {
        this.product_id = product_id2;
    }

    public String getProduct_name() {
        return this.product_name;
    }

    public void setProduct_name(String product_name2) {
        this.product_name = product_name2;
    }

    public String getProduct_price() {
        return this.product_price;
    }

    public void setProduct_price(String product_price2) {
        this.product_price = product_price2;
    }

    public String getProduct_quantity() {
        return this.product_quantity;
    }

    public void setProduct_quantity(String product_quantity2) {
        this.product_quantity = product_quantity2;
    }

    public String getProduct_weight() {
        return this.product_weight;
    }

    public void setProduct_weight(String product_weight2) {
        this.product_weight = product_weight2;
    }

    public String getProduct_image() {
        return this.product_image;
    }

    public void setProduct_image(String product_image2) {
        this.product_image = product_image2;
    }

    public int getpImage() {
        return this.pImage;
    }

    public void setpImage(int pImage2) {
        this.pImage = pImage2;
    }

    public String getDelivery_charge() {
        return this.delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge2) {
        this.delivery_charge = delivery_charge2;
    }

    public String getPoint() {
        return this.point;
    }

    public void setPoint(String point2) {
        this.point = point2;
    }
}
