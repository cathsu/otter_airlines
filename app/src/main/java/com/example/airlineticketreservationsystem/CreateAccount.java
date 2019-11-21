package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.airlineticketreservationsystem.DB.AppDatabase;
import com.example.airlineticketreservationsystem.DB.UserDAO;

import java.util.List;

public class CreateAccount extends AppCompatActivity {

    private static final String TAG = "CREATE_ACCOUNT";

    TextView mTitle, mInstructions, mDisplay;

    EditText mUsername, mPassword;

    Button mSubmitButton;

    UserDAO mUserDAO;
    List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mTitle = findViewById(R.id.createTitleTextView);
        mInstructions = findViewById(R.id.createInstructTextView);
        mDisplay = findViewById(R.id.createDisplayTextView);

        mDisplay.setMovementMethod(new ScrollingMovementMethod());

        mUsername = findViewById(R.id.createUsernameEditText);
        mPassword = findViewById(R.id.createPasswordEditText);

        mSubmitButton = findViewById(R.id.createSubmitButton);

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();

        refreshDisplay();
    }

    public void create(View view) {
        submitUserLog();
        refreshDisplay();


    }

    private void submitUserLog() {
        String username, password;

        username = mUsername.getText().toString();
        password = mPassword.getText().toString();

        mUserDAO.insert(new User(username, password));
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
