package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.airlineticketreservationsystem.DB.TransactionDAO;
import com.example.airlineticketreservationsystem.DB.UserDAO;

import java.util.List;

public class CreateAccount extends AppCompatActivity {

    private static final String TAG = "CREATE_ACCOUNT";

    TextView mTitle, mInstructions, mDisplay;

    EditText mUsername, mPassword;

    Button mSubmitButton;

    AlertDialog.Builder mAlertBuilder;

    UserDAO mUserDAO;
    List<User> mUsers;

    TransactionDAO mTransactionDAO;
    List<User> mTransactions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mTitle = findViewById(R.id.createTitleTextView);
        mInstructions = findViewById(R.id.createInstructTextView);

        mUsername = findViewById(R.id.createUsernameEditText);
        mPassword = findViewById(R.id.createPasswordEditText);

        mSubmitButton = findViewById(R.id.createSubmitButton);

        mAlertBuilder = new AlertDialog.Builder(CreateAccount.this);


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

        mTransactionDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getTransactionDAO();

    }

    public void validateAccount(View view) {
        submitUserLog();

    }

    private void submitUserLog() {

        String username, password;

        username = mUsername.getText().toString();
        password = mPassword.getText().toString();

        boolean uniqueLogin = isNewLoginUnique(username, password);
        boolean correctLogin = isNewLoginCorrect(username, password);

        Log.d(TAG, Boolean.toString(uniqueLogin));
        Log.d(TAG, Boolean.toString(correctLogin));

        // If the login is unique + fulfills username/password requirements, add new login to list of users.
        if (uniqueLogin && correctLogin) {
            User user = new User(username, password);
            mUserDAO.insert(user);
            mTransactionDAO.insert(new Transaction( username, getString(R.string.TYPE_NEW_ACCOUNT), user.toString()));
            Toast t = Toast.makeText(getApplicationContext(), R.string.createAlertSuccessfulAccount, Toast.LENGTH_SHORT);
            t.setGravity(Gravity.BOTTOM, 0, 0);
            t.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                    startActivity(intent);
                }
            }, 2000);

        // Otherwise, show error message and reset text fields.
        } else {
            if (!uniqueLogin) {
                mAlertBuilder.setMessage(R.string.createAlertDuplicateLogin);
            } else //incorrectLogin {
                mAlertBuilder.setMessage(R.string.createAlertIncorrectLogin);

            mAlertBuilder.show();
            mUsername.setText(R.string.emptyString);
            mPassword.setText(R.string.emptyString);
        }


    }


    private boolean isNewLoginUnique(String username, String password) {
        // Built-in logins
        boolean defaultLogin1 = username.equals("alice5");
        boolean defaultLogin2 = username.equals("brian77");
        boolean defaultLogin3 = username.equals("chris21");
        boolean adminLogin = username.equals("admin2");

        if (defaultLogin1 || defaultLogin2 || defaultLogin3 || adminLogin) {
            return false;
        }
        User user = mUserDAO.findUserWithUsername(username);

        if (user == null && !username.equals("admin2")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isNewLoginCorrect(String username, String password) {
        return isCredentialValid(username) && isCredentialValid(password);
    }

    private boolean isCredentialValid(String cred) {
        int numLetters = 0, numDigits = 0;
        for (int i = 0; i<cred.length(); i++) {
            if (Character.isDigit(cred.charAt(i))) {
                numDigits ++;

            } else if (Character.isLetter(cred.charAt(i))) {
                numLetters ++;
            }

        }
        return (numLetters >= 3 && numDigits >=1);
    }
}
