package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    TextView mTitle;

    ImageView mImage;


    Button mRegisterButton;
    Button mLogButton;
    Button mReserveButton;
    Button mCancelButton;
    Button mManageButton;

    SharedPreferences mSharedPreferences;
    public static final String USERNAME_KEY = "USERNAME_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = findViewById(R.id.mainTitleTextView);

        mImage = findViewById(R.id.mainOtterImageView);

        mRegisterButton = findViewById(R.id.mainRegisterButton);
        mLogButton = findViewById(R.id.mainLogButton);
        mReserveButton = findViewById(R.id.mainReserveButton);
        mCancelButton = findViewById(R.id.mainCancelButton);
        mManageButton = findViewById(R.id.mainManageButton);

        mSharedPreferences = getSharedPreferences("", Context.MODE_PRIVATE);

        setLogButtonText();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });

    }

    // Change text on the Log Button depending on whether the use is logged in or not. 
    private void setLogButtonText() {
        if (mSharedPreferences.contains(USERNAME_KEY)) {
            mLogButton.setText(R.string.mainLogoutButton);
        } else {
            mLogButton.setText(R.string.mainLoginButton);
        }
    }

    public void setLogButton(View view) {

        // When the user clicks on the Log Button when they are logged in, log out the user.
        if (mSharedPreferences.contains(USERNAME_KEY)) {
            Toast t = Toast.makeText(this, R.string.mainToastSuccessfulLogout, Toast.LENGTH_SHORT);
            t.show();
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.clear();
            editor.apply();
            setLogButtonText();

        // If the user is not logged in, direct user to login page.
        } else {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            setLogButtonText();
        }
    }
}

