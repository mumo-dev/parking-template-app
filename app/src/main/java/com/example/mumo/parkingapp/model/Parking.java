package com.example.mumo.parkingapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Parking implements Serializable {
    private int id;
    private double fee;
    private String location;
    private int startTime;
    private int endTime;
    private List<Slot> slots;
    private String[] timeSlots;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Parking() {
    }

    public Parking(int id, String location, double fee, List<Slot> slots, int startTime, int endTime, String date) {
        this.id = id;
        this.location = location;
        this.fee = fee;
        this.slots = slots;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        timeSlots = generateTimeSlots();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public String[] getTimeSlots() {

        return timeSlots;
    }

    public void setTimeSlots(String[] timeSlots) {
        this.timeSlots = timeSlots;
    }

    private String[] generateTimeSlots(){
        List<String> arr = new ArrayList<>();

        for (int i = startTime; i < endTime; i++) {
            String slottime = i + "-" + (i+1);
            arr.add(slottime);

        }
        String[] array =new String[arr.size()];
        int i =0;
        for (String s: arr) {
            array[i] = s;
            i++;
        }
        return array;
    }
}
