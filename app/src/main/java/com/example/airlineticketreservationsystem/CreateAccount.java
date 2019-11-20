package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateAccount extends AppCompatActivity {

    private static final String TAG = "CREATE_ACCOUNT";

    TextView mTitle, mInstructions;

    EditText mUsername, mPassword;

    Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mTitle = findViewById(R.id.createTitleTextView);
        mInstructions = findViewById(R.id.createInstructTextView);

        mUsername = findViewById(R.id.createUsernameEditText);
        mPassword = findViewById(R.id.createPasswordEditText);

        mSubmitButton = findViewById(R.id.createSubmitButton);

    }

    public void create(View view) {
        String username, password;

        username = mUsername.getText().toString();
        password = mPassword.getText().toString();

        Log.i(TAG, username);
        Log.i(TAG, password);

    }
}
