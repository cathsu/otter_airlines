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
import com.example.airlineticketreservationsystem.DB.TransactionDAO;
import com.example.airlineticketreservationsystem.DB.UserDAO;

import java.util.List;

public class TransactionLogs extends AppCompatActivity {


    TextView mMainTitle, mCreateTitle, mReserveTitle, mCancelTitle, mCreateDisplay, mReserveDisplay, mCancelDisplay;

    Button mButton;

    TransactionDAO mTransactionDAO;
    List<Transaction>mTransactions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_logs);

        mMainTitle = findViewById(R.id.logTitleTextView);
        mCreateDisplay = findViewById(R.id.logDisplayTextView);

        mCreateDisplay.setMovementMethod(new ScrollingMovementMethod());
        mButton = findViewById(R.id.logButton);

        mTransactionDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getTransactionDAO();

        refreshCreateDisplay();

    }

    private void refreshCreateDisplay() {
        mTransactions = mTransactionDAO.getTransaction();
        if (! mTransactions.isEmpty() ) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Transaction transaction: mTransactions) {
                stringBuilder.append(transaction.toString());
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
