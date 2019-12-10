package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.airlineticketreservationsystem.DB.AppDatabase;
import com.example.airlineticketreservationsystem.DB.FlightDAO;
import com.example.airlineticketreservationsystem.DB.ReservationDAO;
import com.example.airlineticketreservationsystem.DB.TransactionDAO;

import java.util.List;

public class Cancellation extends AppCompatActivity {

    TextView mTitle, mDisplay;
    EditText mFlight;
    Button mButton;

    ReservationDAO mReservationDAO;
    List<Reservation> mReservations;
    TransactionDAO mTransactionDAO;
    FlightDAO mFlightDAO;

    AlertDialog.Builder mAlertBuilder;
    SharedPreferences mSharedPreferences;

    public static final String USERNAME_KEY = "USERNAME_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation);

        mTitle = findViewById(R.id.cancelTitleTextView);
        mDisplay = findViewById(R.id.cancelDisplayTextView);
        mDisplay.setMovementMethod(new ScrollingMovementMethod());
        mFlight = findViewById(R.id.cancelFlightEditText);
        mButton = findViewById(R.id.cancelButton);

        mSharedPreferences = getSharedPreferences("", Context.MODE_PRIVATE);
        mAlertBuilder = new AlertDialog.Builder(Cancellation.this);

        mReservationDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getReservationDAO();

        mTransactionDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getTransactionDAO();

        mFlightDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getFlightDAO();

        display();
    }

    // Display all reservations made with username
    public void display() {
        String username = mSharedPreferences.getString(USERNAME_KEY, "username");
        mReservations = mReservationDAO.findReservationWithUsername(username);
        Log.d("CANCEL", username);
        if (!mReservations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Reservation reservation : mReservations) {
                stringBuilder.append(reservation);
                stringBuilder.append("------------------------------------------------------------\n");
            }
            mDisplay.setText(stringBuilder);
        } else {
            mAlertBuilder.setMessage("You have no reservations");
            mAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Cancellation.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            mAlertBuilder.create();
            mAlertBuilder.show();
        }
    }

    // Cancel flight
    public void cancel(View view) {

        String flightNo;

        final String username = mSharedPreferences.getString(USERNAME_KEY, "username");
        flightNo = mFlight.getText().toString();

        final Reservation reservation = mReservationDAO.findReservationWithUsernameAndFlight(username, flightNo);
        Flight flight = mFlightDAO.findFlightWithNumber(flightNo);

        final String message = "Customer's username: " + username + "\n"
                                + "Flight number: " + flightNo + "\n"
                                + "Departure: " + flight.getDeparture() + " at " + flight.getDepartureTime() + "\n"
                                + "Arrival: " + flight.getArrival() + "\n"
                                + "Number of tickets: " + reservation.getSeats() + "\n"
                                + "Reservation number: " + reservation.getId();
        mAlertBuilder.setMessage("Please confirm that you are canceling your reservation on flight " + flightNo);
        mAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTransactionDAO.insert(new Transaction(username, getString(R.string.TYPE_CANCEL_RESERVATION), message));
                mReservationDAO.delete(reservation);
                Intent intent = new Intent(Cancellation.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mAlertBuilder.create();
        mAlertBuilder.show();

    }
}
