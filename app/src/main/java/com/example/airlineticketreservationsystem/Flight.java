package com.example.airlineticketreservationsystem;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.airlineticketreservationsystem.DB.AppDatabase;

import java.text.DecimalFormat;
import java.util.Date;

@Entity(tableName = AppDatabase.FLIGHT_TABLE)
public class Flight {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    private String mNumber, mDeparture, mArrival, mDepartureTime;
    private int mCapacity;
    private double mPrice;


    public Flight(String number,String departure, String arrival, String departureTime, int capacity, double price) {
        mNumber = number;
        mDeparture = departure;
        mArrival = arrival;
        mDepartureTime = departureTime;
        mCapacity = capacity;
        mPrice = price;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getDeparture() {
        return mDeparture;
    }

    public void setDeparture(String departure) {
        mDeparture = departure;
    }

    public String getArrival() {
        return mArrival;
    }

    public void setArrival(String arrival) {
        mArrival = arrival;
    }

    public String getDepartureTime() {
        return mDepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        mDepartureTime = departureTime;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public void setCapacity(int capacity) {
        mCapacity = capacity;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return "Flight" + '\n' +
                "Flight Number: " + mNumber + '\n' +
                "Departure: " + mDeparture + '\n' +
                "Arrival: " + mArrival + '\n' +
                "Departure Time: " + mDepartureTime + '\n' +
                "Capacity: " + mCapacity + '\n' +
                "Price: $" + decimalFormat.format(mPrice);
    }
}
