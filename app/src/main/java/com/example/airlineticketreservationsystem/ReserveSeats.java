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

    FlightDAO mFlightsDAO;
    List<Flight> mFlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_seats);

        mDisplay = findViewById(R.id.reserveDisplayTextView);
        mDisplay.setMovementMethod(new ScrollingMovementMethod());

        mFlightsDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getFlightDAO();

//        mFlightsDAO.deleteAll();
        refreshDisplay();
    }

    private void refreshDisplay() {
        mFlights = mFlightsDAO.getFlights();

        if (! mFlights.isEmpty() ) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Flight flight: mFlights) {
                stringBuilder.append(flight.toString());
            }

            mDisplay.setText(stringBuilder.toString());

        } else {
            mDisplay.setText("Empty log");
        }
    }

}
