package com.shop.veggies.model;

public class HistoryModel {
    private String amount;
    private String comments;
    private String created_at;
    private String transaction_id;

    public String getTransaction_id() {
        return this.transaction_id;
    }

    public void setTransaction_id(String transaction_id2) {
        this.transaction_id = transaction_id2;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments2) {
        this.comments = comments2;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount2) {
        this.amount = amount2;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at2) {
        this.created_at = created_at2;
    }
}
