package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.airlineticketreservationsystem.DB.AppDatabase;
import com.example.airlineticketreservationsystem.DB.FlightDAO;

import java.util.List;

public class ReserveSeats extends AppCompatActivity {

    TextView mDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_seats);

        mDisplay = findViewById(R.id.reserveDisplayTextView);
        mDisplay.setMovementMethod(new ScrollingMovementMethod());


//        mFlightsDAO.deleteAll();

    }



}
