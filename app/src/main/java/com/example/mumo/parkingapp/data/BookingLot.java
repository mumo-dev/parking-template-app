package com.example.mumo.parkingapp.data;

public class BookingLot {
    private int id;
    private String area;
    private double fee;

    public BookingLot(int id, String area, double fee) {
        this.id = id;
        this.area = area;
        this.fee = fee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
