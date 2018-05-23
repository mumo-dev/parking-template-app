package com.example.mumo.parkingapp.database;

import android.provider.BaseColumns;

public class ParkingDbContract {

    public static class SlotsSchema implements BaseColumns {
        public static final String TABLE_NAME ="slots";

        public static final String COL_REF_NO ="ref_no";
        public static final String COL_SLOT_ID="slot_id";
        public static final String COL_TIME_BOOKED ="time";
        public static final String COL_TIMESTAMP = "timestamp";

    }
}
