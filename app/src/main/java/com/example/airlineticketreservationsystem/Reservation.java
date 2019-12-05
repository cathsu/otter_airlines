package com.example.airlineticketreservationsystem;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.airlineticketreservationsystem.DB.AppDatabase;

import java.text.DecimalFormat;

@Entity(tableName= AppDatabase.RESERVATION_TABLE)
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    private String mUsername;
    private String mFlightNo;
    private int mSeats;
    private double mCost;

    public Reservation(String username, String flightNo, int seats, double cost) {
        mUsername = username;
        mFlightNo = flightNo;
        mSeats = seats;
        mCost = cost;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getFlightNo() {
        return mFlightNo;
    }

    public void setFlightNo(String flightNo) {
        mFlightNo = flightNo;
    }

    public int getSeats() {
        return mSeats;
    }

    public void setSeats(int seats) {
        mSeats = seats;
    }

    public double getCost() {
        return mCost;
    }

    public void setCost(double cost) {
        mCost = cost;
    }

    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return "Reservation Number: " + mId + "\n"
                + "Customer Username: " + mUsername + "\n"
                + "Flight Number: " + mFlightNo + "\n"
                + "Number of Tickets: " + mSeats + "\n"
                + "Total Cost: $" + decimalFormat.format(mCost);
    }
}
