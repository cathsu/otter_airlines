package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FlightCriteria extends AppCompatActivity {

    public static final String DEPARTURE = "DEPARTURE";
    public static final String ARRIVAL = "ARRIVAL";
    public static final String TICKET = "TICKET";
    public static final int MAX_TICKETS = 7;

    TextView mTitle;
    EditText mDeparture, mArrival, mTickets;
    Button mSearchButton;

    AlertDialog.Builder mAlertBuilder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_criteria);

        mTitle = findViewById(R.id.flightCriteriaTitleTextView);
        mDeparture = findViewById(R.id.flightCriteriaDepartureEditText);
        mArrival = findViewById(R.id.flightCriteriaArrivalEditText);
        mTickets = findViewById(R.id.flightCriteriaTicketEditText);
        mSearchButton = findViewById(R.id.flightCriteriaButton);

        mAlertBuilder = new AlertDialog.Builder(FlightCriteria.this);

    }

    public void search(View view) {
        String departure, arrival;
        int tickets;

        departure = mDeparture.getText().toString();
        arrival = mArrival.getText().toString();
        tickets = Integer.parseInt(mTickets.getText().toString());

        if (tickets > MAX_TICKETS) {
            mAlertBuilder.setMessage("Reservation cannot be made due to system restrictions");
            mAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mDeparture.setText(R.string.emptyString);
                    mArrival.setText(R.string.emptyString);
                    mTickets.setText(R.string.emptyString);
                }
            });

            mAlertBuilder.create();
            mAlertBuilder.show();

        }
        else {
            Intent intent = new Intent(FlightCriteria.this, ReserveSeats.class);

            // Storing values to be passed to the next activity
            intent.putExtra(DEPARTURE, departure);
            Log.d("FLIGHT", departure);
            intent.putExtra(ARRIVAL, arrival);
            Log.d("FLIGHT", arrival);
            intent.putExtra(TICKET, tickets);

            startActivity(intent);
        }




    }
}
