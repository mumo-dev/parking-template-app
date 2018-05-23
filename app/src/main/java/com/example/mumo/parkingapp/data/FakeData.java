package com.example.mumo.parkingapp.data;

import java.util.ArrayList;
import java.util.List;

public class FakeData {

    private  List<BookingLot> sBookingLots = new ArrayList<>();

    public FakeData(){
        sBookingLots.add(new BookingLot(1,"Nakumatt Supermaket", 300.0));
        sBookingLots.add(new BookingLot(2,"Naivas Supermaket", 200.0));
        sBookingLots.add(new BookingLot(3,"Dockt Plaza", 100.0));
        sBookingLots.add(new BookingLot(4,"National Bank", 300.0));
        sBookingLots.add(new BookingLot(5,"Fery House", 200.0));
        sBookingLots.add(new BookingLot(6,"Marikit market", 250));
        sBookingLots.add(new BookingLot(7,"Sheraz Booking space", 80));
        sBookingLots.add(new BookingLot(8,"Noayi house", 700));
        sBookingLots.add(new BookingLot(9,"Nyayo house", 500.0));
        sBookingLots.add(new BookingLot(10,"Machs country bus", 800.0));
        sBookingLots.add(new BookingLot(11,"T-tot hotel", 150.0));
        sBookingLots.add(new BookingLot(12,"Kiamba mall", 300.0));
        sBookingLots.add(new BookingLot(13,"456 Booking space", 300.0));
        sBookingLots.add(new BookingLot(14,"County parking space01", 500.0));
        sBookingLots.add(new BookingLot(15,"County parking space03", 600.0));
        sBookingLots.add(new BookingLot(16,"County parking space02", 350.0));
        sBookingLots.add(new BookingLot(17,"County parking space05", 200.0));
    }

    public  List<BookingLot> getBookingLots() {
        return sBookingLots;
    }
}
