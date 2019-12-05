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
import android.widget.EditText;
import android.widget.TextView;

import com.example.airlineticketreservationsystem.DB.AppDatabase;
import com.example.airlineticketreservationsystem.DB.FlightDAO;
import com.example.airlineticketreservationsystem.DB.ReservationDAO;
import com.example.airlineticketreservationsystem.DB.TransactionDAO;

import java.text.DecimalFormat;
import java.util.List;

public class ReserveSeats extends AppCompatActivity {

    public static final String DEPARTURE = "DEPARTURE";
    public static final String ARRIVAL = "ARRIVAL";
    public static final String TICKET = "TICKET";
    public static final String FLIGHT_NO = "FLIGHT NO";
    public static final String DEPARTURE_TIME = "DEPART TIME";
    public static final String CAPACITY = "CAPACITY";
    public static final String PRICE = "PRICE";
    public static final String USERNAME_KEY = "USERNAME_KEY";

    TextView mDisplay;
    EditText mFlight;

    FlightDAO mFlightDAO;
    List<Flight> mFlights;
    List<Flight> mAllFlights;

    ReservationDAO mReservationDAO;
    TransactionDAO mTransactionDAO;

    AlertDialog.Builder mAlertBuilder;
    SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_seats);

        mDisplay = findViewById(R.id.reserveDisplayTextView);
        mDisplay.setMovementMethod(new ScrollingMovementMethod());
        mFlight = findViewById(R.id.reserveFlightEditText);

        mSharedPreferences = getSharedPreferences("", Context.MODE_PRIVATE);
        mAlertBuilder = new AlertDialog.Builder(ReserveSeats.this);

        mFlightDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getFlightDAO();

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

        display();

    }

    public void display() {
        String departure, arrival;
        Integer tickets;

        Intent intent = getIntent();

        departure = intent.getStringExtra(DEPARTURE);
        arrival = intent.getStringExtra(ARRIVAL);
        tickets = intent.getIntExtra(TICKET, -1);

        mFlights = mFlightDAO.findFlightsThatMeetCriteria(departure.trim(), arrival.trim(), tickets);
        mAllFlights = mFlightDAO.getFlights();

//        StringBuilder stringBuilder = new StringBuilder();
//        for (Flight flight: mAllFlights) {
//            stringBuilder.append(flight);
//        }
//        mDisplay.setText(stringBuilder.toString());


        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (!mFlights.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
//            String s = String.format("%-12s%-12s%-12s%-12s%-12s%-12s", FLIGHT_NO, DEPARTURE, ARRIVAL, DEPARTURE_TIME, CAPACITY, PRICE);
//            stringBuilder.append(s + "\n");
            for (Flight flight: mFlights) {
                stringBuilder.append("FLIGHT NO: " + flight.getNumber() + "\n");
                stringBuilder.append("Departure: " + flight.getDeparture() + " at " + flight.getDepartureTime() + "\n");
                stringBuilder.append("Arrival: " + flight.getArrival() + "\n");
                stringBuilder.append("Flight Capacity: " + flight.getCapacity() + "\n");
                stringBuilder.append("Price: $" + decimalFormat.format(flight.getPrice()) + "\n");
                stringBuilder.append("------------------------------------------------\n");

            }

            mDisplay.setText(stringBuilder.toString());

        } else {
            mAlertBuilder.setMessage("There are no flights available from " + departure + " to " + arrival);
            mAlertBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(ReserveSeats.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            mAlertBuilder.create();
            mAlertBuilder.show();
        }
    }
    public void reserve(View view) {

        Intent intent = getIntent();

        String flightNo;
        int tickets;

        final String username = mSharedPreferences.getString(USERNAME_KEY, "username");
        flightNo = mFlight.getText().toString();

        tickets = intent.getIntExtra(TICKET, -1);

        Flight flight = mFlightDAO.findFlightWithNumber(flightNo);
        double totalCost = tickets*flight.getPrice();


        Reservation reservation = new Reservation(username, flightNo, tickets, totalCost);
        mReservationDAO.insert(reservation);

        final String message = reservation.toString() + "\n" + "Departure: " + flight.getDeparture() + "\n"
                + "Arrival: " + flight.getArrival() + "\n";


        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        mAlertBuilder.setMessage("Username: " + username + "\n"
                + "Flight number: " + flightNo + "\n"
                + "Departure: " + flight.getDeparture() + " at " + flight.getDepartureTime() + "\n"
                + "Arrival: " + flight.getArrival() + "\n"
                + "Number of tickets: " + tickets + "\n"
                + "Reservation number: " + reservation.getId() + "\n"
                + "Total Amount $" + decimalFormat.format(totalCost));

        mAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTransactionDAO.insert(new Transaction( username, getString(R.string.TYPE_RESERVE_SEAT), message));
                Intent intent = new Intent(ReserveSeats.this, MainActivity.class);
                startActivity(intent);

            }
        });

        mAlertBuilder.create();
        mAlertBuilder.show();



    }

}
