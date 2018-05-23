package com.example.mumo.parkingapp.model;

public class BookedSlot {
    private int id;
    private int slotId;
    private String refno;
    private String time;
    private String timeStamp;

    public BookedSlot(int slotId, String refno, String time) {
        this.slotId = slotId;
        this.refno = refno;
        this.time = time;
    }

    public BookedSlot(int id, int slotId, String refno, String time, String timeStamp) {
        this.id = id;
        this.slotId = slotId;
        this.refno = refno;
        this.time = time;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
