package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airlineticketreservationsystem.DB.AppDatabase;
import com.example.airlineticketreservationsystem.DB.FlightDAO;

import java.text.DecimalFormat;

public class AddFlight extends AppCompatActivity {

    TextView mTitle, mFlightNumberText, mDepartureText, mArrivalText, mDepartureTimeText, mCapacityText, mPriceText;

    EditText mFlightNumber, mDeparture, mArrival, mDepartureTime, mCapacity, mPrice;

    String clock; //HACKY
    RadioGroup mRadioGroup;
    RadioButton mAMButton, mPMButton;
    Button mSubmitButton;

    AlertDialog.Builder mAlertBuilder;

    FlightDAO mFlightsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);
        mTitle = findViewById(R.id.flightTitleTextView);
        mFlightNumberText = findViewById(R.id.flightNumberTextView);
        mDepartureText = findViewById(R.id.flightDepartTextView);
        mArrivalText = findViewById(R.id.flightArrivalTextView);
        mDepartureTimeText = findViewById(R.id.flightDepartTimeTextView);
        mCapacityText = findViewById(R.id.flightCapacityTextView);
        mPriceText = findViewById(R.id.flightPriceTextView);

        mFlightNumber = findViewById(R.id.flightNumberEditText);
        mDeparture = findViewById(R.id.flightDepartEditText);
        mArrival = findViewById(R.id.flightArrivalEditText);
        mDepartureTime = findViewById(R.id.flightDepartTimeEditText);
        mCapacity = findViewById(R.id.flightCapacityEditText);
        mPrice = findViewById(R.id.flightPriceEditText);

        mRadioGroup = findViewById(R.id.flightRadioGroup);
        mAMButton = findViewById(R.id.flightAMRadioButton);
        mPMButton = findViewById(R.id.flightPMRadioButton);
        mSubmitButton = findViewById(R.id.flightSubmitButton);

        mFlightsDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getFlightDAO();

        mAlertBuilder = new AlertDialog.Builder(AddFlight.this);
        mAlertBuilder.setMessage(R.string.flightAlertQuestionText);
        mAlertBuilder.setPositiveButton(R.string.flightAlertYesButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        mAlertBuilder.setNegativeButton(R.string.flightAlertNoButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast t = Toast.makeText(getApplicationContext(), R.string.flightToastExitMessage, Toast.LENGTH_SHORT);
                t.setGravity(Gravity.BOTTOM, 0, 0);
                t.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AddFlight.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, 2000);

            }
        });
        mAlertBuilder.create();
        mAlertBuilder.show();
    }

    public void addNewFlight(View view) {
        String number, departure, arrival, departureTime;
        final StringBuilder departureTimeString = new StringBuilder();

        int radioButtonID, capacity;
        double price;

        number = mFlightNumber.getText().toString();
        departure = mDeparture.getText().toString();
        arrival = mArrival.getText().toString();
        departureTime = mDepartureTime.getText().toString();
        departureTimeString.append(departureTime + " ");
        radioButtonID = mRadioGroup.getCheckedRadioButtonId();

        if (radioButtonID == R.id.flightAMRadioButton) {
            departureTimeString.append("AM");
        } else {
            departureTimeString.append("PM");
        }


        capacity = mCapacity.getText().toString().equals("")? -1 : Integer.parseInt(mCapacity.getText().toString());
        price = mPrice.getText().toString().equals("")? -1.0: Double.parseDouble(mPrice.getText().toString());

        boolean areFieldsFilled = areFieldsFilled(number, departure, arrival, departureTime,
                mCapacity.getText().toString(), mPrice.getText().toString());
        boolean isNumberUnique = isFlightNumberUnique(number);

//        boolean areFieldsFilled = true;
//        boolean isNumberUnique = true;
        Log.d("Flight", departureTimeString.toString());

        // If all fields are filled and the flight number is unique, add the new flight to the list of flights.
        if (areFieldsFilled && isNumberUnique) {
            mFlightsDAO.insert(new Flight(number, departure, arrival, departureTimeString.toString(), capacity, price));
            mAlertBuilder = new AlertDialog.Builder(AddFlight.this);
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            mAlertBuilder.setMessage("Flight Number: " + number + "\n"
                                    + "Departure: " + departure + " at " + departureTimeString.toString() + "\n"
                                    + "Arrival: " + arrival + "\n"
                                    + "Capacity: " + capacity + "\n"
                                    + "Price of 1 ticket: $" + decimalFormat.format(price));
            mAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast t = Toast.makeText(getApplicationContext(), R.string.flightToastCreateNewFlight, Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.BOTTOM, 0, 0);
                    t.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(AddFlight.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, 2000);
                }
            });
            mAlertBuilder.create();
            mAlertBuilder.show();

            // Otherwise, show error message and reset all text fields.
        } else {
            mAlertBuilder = new AlertDialog.Builder(AddFlight.this);
            if (!areFieldsFilled) {
                mAlertBuilder.setMessage(R.string.flightAlertFieldsNotFilled);
            } else {
                mAlertBuilder.setMessage(R.string.flightAlertDuplicateID);
            }
            mAlertBuilder.setPositiveButton(R.string.flightAlertButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mFlightNumber.setText(R.string.emptyString);
                    mDeparture.setText(R.string.emptyString);
                    mArrival.setText(R.string.emptyString);
                    mDepartureTime.setText(R.string.emptyString);
                    mCapacity.setText(R.string.emptyString);
                    mPrice.setText(R.string.emptyString);
                }
            });
            mAlertBuilder.create();
            mAlertBuilder.show();
        }

    }

    private boolean areFieldsFilled(String number, String departure, String arrival, String departureTime, String capacity, String price) {
        if (number.equals("") || departure.equals("") ||
                arrival.equals("") || departureTime.equals("")
                || capacity.equals("") || price.equals("")) {
            return false;
        } else {
            return true;
        }

    }

    private boolean isFlightNumberUnique(String number) {
        Flight flight = mFlightsDAO.findFlightWithNumber(number);
        return flight == null;
    }
}
