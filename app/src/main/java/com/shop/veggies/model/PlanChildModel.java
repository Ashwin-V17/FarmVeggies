package com.shop.veggies.model;

public class PlanChildModel {
    private String child_id;
    private String child_name;

    public PlanChildModel() {
    }

    public PlanChildModel(String child_name2) {
        this.child_name = child_name2;
    }

    public String getChild_id() {
        return this.child_id;
    }

    public void setChild_id(String child_id2) {
        this.child_id = child_id2;
    }

    public String getChild_name() {
        return this.child_name;
    }

    public void setChild_name(String child_name2) {
        this.child_name = child_name2;
    }
}
