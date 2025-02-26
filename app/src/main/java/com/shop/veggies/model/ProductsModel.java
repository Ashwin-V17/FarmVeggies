package com.shop.veggies.model;

import java.util.List;

public class ProductsModel {
    private String cid;
    private String offer_status;
    private int pImage;
    private String product_desc;
    private String product_id;
    private String product_image;
    private String product_mrp;
    private String product_name;
    private String product_offer;
    private String product_price;
    private String product_quantity;
    private String product_status;
    private String product_weight;
    private String scid;
    private String subcategoryname;
    List<WeightModel> weight;
    private String weight_count;
    private String weight_webmrp;
    private String weight_webprice;
    private String weight_webtitle;
    private String weight_wid;

    public ProductsModel() {
    }

    public ProductsModel(String product_name2, String product_price2, String product_quantity2, String product_mrp2, String product_offer2, String product_desc2, int pImage2) {
        this.product_name = product_name2;
        this.product_price = product_price2;
        this.product_quantity = product_quantity2;
        this.product_mrp = product_mrp2;
        this.product_offer = product_offer2;
        this.product_desc = product_desc2;
        this.pImage = pImage2;
    }

    public String getProduct_status() {
        return this.product_status;
    }

    public void setProduct_status(String product_status2) {
        this.product_status = product_status2;
    }

    public String getProduct_mrp() {
        return this.product_mrp;
    }

    public void setProduct_mrp(String product_mrp2) {
        this.product_mrp = product_mrp2;
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

    public String getProduct_desc() {
        return this.product_desc;
    }

    public void setProduct_desc(String product_desc2) {
        this.product_desc = product_desc2;
    }

    public List<WeightModel> getWeight() {
        return this.weight;
    }

    public void setWeight(List<WeightModel> weight2) {
        this.weight = weight2;
    }

    public String getProduct_offer() {
        return this.product_offer;
    }

    public void setProduct_offer(String product_offer2) {
        this.product_offer = product_offer2;
    }

    public String getScid() {
        return this.scid;
    }

    public void setScid(String scid2) {
        this.scid = scid2;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid2) {
        this.cid = cid2;
    }

    public String getOffer_status() {
        return this.offer_status;
    }

    public void setOffer_status(String offer_status2) {
        this.offer_status = offer_status2;
    }

    public String getSubcategoryname() {
        return this.subcategoryname;
    }

    public void setSubcategoryname(String subcategoryname2) {
        this.subcategoryname = subcategoryname2;
    }

    public String getWeight_wid() {
        return this.weight_wid;
    }

    public void setWeight_wid(String weight_wid2) {
        this.weight_wid = weight_wid2;
    }

    public String getWeight_webtitle() {
        return this.weight_webtitle;
    }

    public void setWeight_webtitle(String weight_webtitle2) {
        this.weight_webtitle = weight_webtitle2;
    }

    public String getWeight_webprice() {
        return this.weight_webprice;
    }

    public void setWeight_webprice(String weight_webprice2) {
        this.weight_webprice = weight_webprice2;
    }

    public String getWeight_webmrp() {
        return this.weight_webmrp;
    }

    public void setWeight_webmrp(String weight_webmrp2) {
        this.weight_webmrp = weight_webmrp2;
    }

    public String getWeight_count() {
        return this.weight_count;
    }

    public void setWeight_count(String weight_count2) {
        this.weight_count = weight_count2;
    }
}
