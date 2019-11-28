package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.airlineticketreservationsystem.DB.AppDatabase;
import com.example.airlineticketreservationsystem.DB.UserDAO;

import java.util.List;

public class TransactionLogs extends AppCompatActivity {


    TextView mMainTitle, mCreateTitle, mReserveTitle, mCancelTitle, mCreateDisplay, mReserveDisplay, mCancelDisplay;

    Button mButton;

    UserDAO mUserDAO;
    List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_logs);

        mMainTitle = findViewById(R.id.logTitleTextView);
        mCreateTitle = findViewById(R.id.logCreateTitleTextView);
        mReserveTitle = findViewById(R.id.logReserveTitleTextView);
        mCancelTitle = findViewById(R.id.logCancelTitleTextView);
        mCreateDisplay = findViewById(R.id.logCreateDisplayTextView);
        mReserveDisplay = findViewById(R.id.logReserveDisplayTextView);
        mCancelDisplay = findViewById(R.id.logCancelDisplayTextView);

        mCreateDisplay.setMovementMethod(new ScrollingMovementMethod());
        mReserveDisplay.setMovementMethod(new ScrollingMovementMethod());
        mCancelDisplay.setMovementMethod(new ScrollingMovementMethod());

        mButton = findViewById(R.id.logButton);

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();

        refreshCreateDisplay();

    }

    private void refreshCreateDisplay() {
        mUsers = mUserDAO.getUsers();
        if (! mUsers.isEmpty() ) {
            StringBuilder stringBuilder = new StringBuilder();
            for (User user: mUsers) {
                stringBuilder.append(user.toString());
            }

            mCreateDisplay.setText(stringBuilder.toString());

        } else {
            mCreateDisplay.setText(R.string.logEmptyLogMessage);
        }
    }

    public void continueOn(View View) {
        Intent intent = new Intent(TransactionLogs.this, AddFlight.class);
        startActivity(intent);

    }
}
