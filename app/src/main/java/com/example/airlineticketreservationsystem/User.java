package com.example.airlineticketreservationsystem;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.airlineticketreservationsystem.DB.AppDatabase;

import java.util.Date;
import java.util.Objects;

@Entity(tableName= AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    private String mUsername;
    private String mPassword;

    private boolean mIsAdmin;
    private boolean mIsNewAccount;


    public User(String username, String password) {
        mUsername = username;
        mPassword = password;
        mIsAdmin = (mUsername.equals("admin2") && mPassword.equals("admin2")) ? true: false;
        mIsNewAccount = true;
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

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(boolean admin) {
        mIsAdmin = admin;
    }

    public boolean isNewAccount() {
        return mIsNewAccount;
    }

    public void setIsNewAccount(boolean newAccount) {
        mIsNewAccount = newAccount;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Username=" + mUsername);
        return stringBuilder.toString();
    }


}
