package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airlineticketreservationsystem.DB.AppDatabase;
import com.example.airlineticketreservationsystem.DB.UserDAO;

import java.util.List;

public class Login extends AppCompatActivity {

    private static final String TAG = "LOGIN";
    private static final String USERNAME_KEY = "USERNAME_KEY";

    TextView mTitle, mInstructions, mDisplay;

    EditText mUsername, mPassword;

    Button mSubmitButton;

    AlertDialog.Builder mAlertBuilder;

    UserDAO mUserDAO;
    List<User> mUsers;
    SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTitle = findViewById(R.id.loginTitleTextView);
        mDisplay = findViewById(R.id.loginDisplayTextView);

        mDisplay.setMovementMethod(new ScrollingMovementMethod());

        mUsername = findViewById(R.id.loginUsernameEditText);
        mPassword = findViewById(R.id.loginPasswordEditText);

        mSubmitButton = findViewById(R.id.loginLoginButton);

        mAlertBuilder = new AlertDialog.Builder(Login.this);

        mSharedPreferences = getSharedPreferences("", Context.MODE_PRIVATE);


        mAlertBuilder.setPositiveButton(R.string.createAlertButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });


        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getUserDAO();

        refreshDisplay();
    }

    public void validateLogin(View view) {
        loginToSystem();
        refreshDisplay();


    }

    private void loginToSystem() {
        String username, password;

        username = mUsername.getText().toString();
        password = mPassword.getText().toString();

        boolean isLoginCorrect = isLoginCorrect(username, password);

        if (isLoginCorrect) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(USERNAME_KEY, username);
            editor.apply();

            Toast t = Toast.makeText(getApplicationContext(), R.string.loginSuccessfulLogin, Toast.LENGTH_SHORT);
            t.setGravity(Gravity.BOTTOM, 0, 0);
            t.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
            }, 2000);


        } else {
            mAlertBuilder.setMessage(R.string.loginAlertIncorrectLogin);
            mAlertBuilder.show();
            mUsername.setText(R.string.emptyString);
            mPassword.setText(R.string.emptyString);
        }


    }

    private boolean isLoginCorrect(String username, String password) {
        boolean defaultLogin1 = username.equals("alice5") && password.equals("csumb100");
        boolean defaultLogin2 = username.equals("brian77") && password.equals("123ABC");
        boolean defaultLogin3 = username.equals("chris21") && password.equals("CHRIS21");
        boolean adminLogin = username.equals("admin2") && password.equals("admin2");

        if (defaultLogin1 || defaultLogin2 || defaultLogin3 || adminLogin) {
            return true;
        }

        User user = mUserDAO.findUserWithUsername(username);

        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }


    private void refreshDisplay() {
        mUsers = mUserDAO.getUsers();
        if (! mUsers.isEmpty() ) {
            StringBuilder stringBuilder = new StringBuilder();
            for (User user: mUsers) {
                stringBuilder.append(user.toString());
            }

            mDisplay.setText(stringBuilder.toString());

        } else {
            mDisplay.setText("Empty log");
        }
    }
}
