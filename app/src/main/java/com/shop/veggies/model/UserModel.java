package com.shop.veggies.model;

import org.json.JSONException;
import org.json.JSONObject;

public class UserModel {
    private String action;
    private String customer_address;
    private String customer_email;
    private String customer_id;
    private String customer_mobile;
    private String customer_name;
    private String customer_profile;
    private String password;

    public UserModel() {
    }

    public UserModel(JSONObject jsonObject) {
        try {
            if (jsonObject.has("action")) {
                this.action = jsonObject.getString("action");
            }
            if (jsonObject.has("customer_id")) {
                this.customer_id = jsonObject.getString("customer_id");
            }
            if (jsonObject.has("customer_name")) {
                this.customer_name = jsonObject.getString("customer_name");
            }
            if (jsonObject.has("customer_mobile")) {
                this.customer_mobile = jsonObject.getString("customer_mobile");
            }
            if (jsonObject.has("customer_email")) {
                this.customer_email = jsonObject.getString("customer_email");
            }
            if (jsonObject.has("customer_address")) {
                this.customer_email = jsonObject.getString("customer_address");
            }
            if (jsonObject.has("customer_profile")) {
                this.customer_email = jsonObject.getString("customer_profile");
            }
            if (jsonObject.has("password")) {
                this.customer_email = jsonObject.getString("password");
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }

    public String getCustomer_id() {
        return this.customer_id;
    }

    public void setCustomer_id(String customer_id2) {
        this.customer_id = customer_id2;
    }

    public String getCustomer_name() {
        return this.customer_name;
    }

    public void setCustomer_name(String customer_name2) {
        this.customer_name = customer_name2;
    }

    public String getCustomer_mobile() {
        return this.customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile2) {
        this.customer_mobile = customer_mobile2;
    }

    public String getCustomer_email() {
        return this.customer_email;
    }

    public void setCustomer_email(String customer_email2) {
        this.customer_email = customer_email2;
    }

    public String getCustomer_address() {
        return this.customer_address;
    }

    public void setCustomer_address(String customer_address2) {
        this.customer_address = customer_address2;
    }

    public String getCustomer_profile() {
        return this.customer_profile;
    }

    public void setCustomer_profile(String customer_profile2) {
        this.customer_profile = customer_profile2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }
}
