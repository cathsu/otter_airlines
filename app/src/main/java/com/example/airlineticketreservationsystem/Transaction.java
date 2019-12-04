package com.example.airlineticketreservationsystem;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import com.example.airlineticketreservationsystem.DB.AppDatabase;
import com.example.airlineticketreservationsystem.DB.UserDAO;

import java.util.Date;


@Entity(tableName = AppDatabase.TRANSACTION_TABLE)
public class Transaction {


    @PrimaryKey(autoGenerate = true)
    private int mId;
    private String mMessage;
    private String mUsername;
    private String mType;
    private Date mDate;


    private static final String TYPE_NEW_ACCOUNT = "Type: New Account";
    private static final String TYPE_RESERVE_SEAT = "Type: Reserve Seat";
    private static final String TYPE_CANCEL_RESERVATION = "Type: Cancel Reservation";

    public Transaction(String username, String type, String message) {
        mDate = new Date();
        mUsername = username;
        mType = type;
        mMessage = message;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String toString() {
        return mDate + "\n"
                + mType + "\n"
                + mMessage;
    }
}
