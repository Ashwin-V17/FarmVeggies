package com.shop.veggies.model;

import java.util.ArrayList;

public class PlanHeaderModel {
    private ArrayList<PlanChildModel> categoryChildModelArrayList;
    private String planHeader_id;
    private String planHeader_image;
    private String planHeader_title;

    public PlanHeaderModel() {
    }

    public PlanHeaderModel(String planHeader_id2, String planHeader_title2, ArrayList<PlanChildModel> categoryChildModelArrayList2) {
        this.planHeader_id = planHeader_id2;
        this.planHeader_title = planHeader_title2;
        this.categoryChildModelArrayList = categoryChildModelArrayList2;
    }

    public String getPlanHeader_id() {
        return this.planHeader_id;
    }

    public void setPlanHeader_id(String planHeader_id2) {
        this.planHeader_id = planHeader_id2;
    }

    public String getPlanHeader_title() {
        return this.planHeader_title;
    }

    public void setPlanHeader_title(String planHeader_title2) {
        this.planHeader_title = planHeader_title2;
    }

    public String getPlanHeader_image() {
        return this.planHeader_image;
    }

    public void setPlanHeader_image(String planHeader_image2) {
        this.planHeader_image = planHeader_image2;
    }

    public ArrayList<PlanChildModel> getCategoryChildModelArrayList() {
        return this.categoryChildModelArrayList;
    }

    public void setCategoryChildModelArrayList(ArrayList<PlanChildModel> categoryChildModelArrayList2) {
        this.categoryChildModelArrayList = categoryChildModelArrayList2;
    }
}
