package com.shop.veggies.model;

public class MyordersModel {
    private String action;
    private String count;
    private String delivery_date;
    private int image;
    private String order_date;
    private String order_id;
    private String order_no;
    private String order_payment;
    private String order_status;
    private String order_total;
    private String product_id;
    private String product_image;
    private String product_name;
    private String product_price;
    private String product_quantity;
    private String product_weight;
    private String review_status;
    private String user_email;
    private String user_mobile;
    private String user_name;

    public MyordersModel() {
    }

    public MyordersModel(String order_no2, String order_date2, String order_total2, String product_name2, String product_price2, String product_quantity2, String product_weight2, int image2) {
        this.order_no = order_no2;
        this.order_date = order_date2;
        this.order_total = order_total2;
        this.product_name = product_name2;
        this.product_price = product_price2;
        this.product_quantity = product_quantity2;
        this.product_weight = product_weight2;
        this.image = image2;
    }

    public String getDelivery_date() {
        return this.delivery_date;
    }

    public void setDelivery_date(String delivery_date2) {
        this.delivery_date = delivery_date2;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }

    public String getOrder_id() {
        return this.order_id;
    }

    public void setOrder_id(String order_id2) {
        this.order_id = order_id2;
    }

    public String getOrder_no() {
        return this.order_no;
    }

    public void setOrder_no(String order_no2) {
        this.order_no = order_no2;
    }

    public String getOrder_date() {
        return this.order_date;
    }

    public void setOrder_date(String order_date2) {
        this.order_date = order_date2;
    }

    public String getOrder_total() {
        return this.order_total;
    }

    public void setOrder_total(String order_total2) {
        this.order_total = order_total2;
    }

    public String getOrder_payment() {
        return this.order_payment;
    }

    public void setOrder_payment(String order_payment2) {
        this.order_payment = order_payment2;
    }

    public String getOrder_status() {
        return this.order_status;
    }

    public void setOrder_status(String order_status2) {
        this.order_status = order_status2;
    }

    public String getReview_status() {
        return this.review_status;
    }

    public void setReview_status(String review_status2) {
        this.review_status = review_status2;
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

    public String getCount() {
        return this.count;
    }

    public void setCount(String count2) {
        this.count = count2;
    }

    public String getProduct_image() {
        return this.product_image;
    }

    public void setProduct_image(String product_image2) {
        this.product_image = product_image2;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name2) {
        this.user_name = user_name2;
    }

    public String getUser_mobile() {
        return this.user_mobile;
    }

    public void setUser_mobile(String user_mobile2) {
        this.user_mobile = user_mobile2;
    }

    public String getUser_email() {
        return this.user_email;
    }

    public void setUser_email(String user_email2) {
        this.user_email = user_email2;
    }

    public String getProduct_weight() {
        return this.product_weight;
    }

    public void setProduct_weight(String product_weight2) {
        this.product_weight = product_weight2;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image2) {
        this.image = image2;
    }
}
