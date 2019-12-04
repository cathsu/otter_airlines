package com.example.airlineticketreservationsystem;

import androidx.room.PrimaryKey;

public class Reservation {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    private int mUserId;
    private int mFlightId;
    private int mSeats;

    public Reservation(int userId, int flightId, int seats) {
        mUserId = userId;
        mFlightId = flightId;
        mSeats = seats;
    }
}
