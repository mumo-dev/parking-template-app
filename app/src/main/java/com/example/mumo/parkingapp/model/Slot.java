package com.example.mumo.parkingapp.model;

public class Slot {
    private int id;
    private String refNo;

    public Slot(int id, String refNo){
        this.id = id;
        this.refNo = refNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }
}
