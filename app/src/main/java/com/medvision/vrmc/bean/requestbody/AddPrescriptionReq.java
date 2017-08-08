package com.medvision.vrmc.bean.requestbody;

import java.util.ArrayList;

/**
 * Created by raytine on 2017/5/9.
 */

public class AddPrescriptionReq extends BaseReq {
    private String userId;
    private String disease;
    private String suggestion;
    private double total;
    private ArrayList<AddPrescriptionsReq> prescriptionContentList;

    public AddPrescriptionReq() {
    }

    public AddPrescriptionReq(String userId, String disease, String suggestion, int price, ArrayList<AddPrescriptionsReq> prescriptionContentList) {

        this.userId = userId;
        this.disease = disease;
        this.suggestion = suggestion;
        this.total = price;
        this.prescriptionContentList = prescriptionContentList;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public double getPrice() {
        return total;
    }

    public void setPrice(double price) {
        this.total = price;
    }

    public ArrayList<AddPrescriptionsReq> getPrescriptionContentList() {
        return prescriptionContentList;
    }

    public void setPrescriptionContentList(ArrayList<AddPrescriptionsReq> prescriptionContentList) {
        this.prescriptionContentList = prescriptionContentList;
    }
}
