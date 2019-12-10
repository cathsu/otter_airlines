/** Citations
 * Delay
 * - https://stackoverflow.com/questions/17237287/how-can-i-wait-for-10-second-without-locking-application-ui-in-android
 *
 * Login/Logout:
 * - https://www.tutorialspoint.com/android/android_session_management.htm
 * - https://stackoverflow.com/questions/12074156/android-storing-retrieving-strings-with-shared-preferences
 * - https://www.tutorialspoint.com/how-to-pass-data-from-one-activity-to-another-in-android-using-shared-preferences
 * - https://stackoverflow.com/questions/46011023/i-am-trying-to-pass-the-values-in-shared-preference-from-one-activity-to-another
 *
 * Creating a RadioGroup:
 * - https://developer.android.com/guide/topics/ui/controls/radiobutton
 *
 * Check which RadioButton in the RadioGroup is selected
 * - https://stackoverflow.com/questions/42502055/how-to-check-which-radio-button-of-a-radio-group-is-selected-android
 */

package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

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

import com.example.airlineticketreservationsystem.DB.AppDatabase;
import com.example.airlineticketreservationsystem.DB.FlightDAO;
import com.example.airlineticketreservationsystem.DB.ReservationDAO;
import com.example.airlineticketreservationsystem.DB.TransactionDAO;
import com.example.airlineticketreservationsystem.DB.UserDAO;

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
                if (!mSharedPreferences.contains(USERNAME_KEY)) {
                    Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "You are logged in!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Uncomment for testing purposes
//        UserDAO mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build()
//                .getUserDAO();
//        FlightDAO mFlightDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build()
//                .getFlightDAO();
//        TransactionDAO mTransactionDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build()
//                .getTransactionDAO();
//        ReservationDAO mReservationDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBNAME)
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build()
//                .getReservationDAO();
//        mUserDAO.deleteAll();
//        mFlightDAO.deleteAll();
//        mTransactionDAO.deleteAll();
//        mReservationDAO.deleteAll();

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

    public void reserveSeat(View view) {
        String username = mSharedPreferences.getString(USERNAME_KEY, "username");

        // Only customers can cancel seats.
        if (!username.equals("admin2")) {
            Intent intent = new Intent(MainActivity.this, FlightCriteria.class);
            startActivity(intent);
        } else {
            Toast t = Toast.makeText(this, R.string.mainAdminCannotReserveCancel, Toast.LENGTH_SHORT);
            t.show();
        }

    }

    public void cancelReservation(View view) {
        String username = mSharedPreferences.getString(USERNAME_KEY, "username");

        // Only customers can cancel flight reservations.
        if (!username.equals("admin2")) {
            Intent intent = new Intent(MainActivity.this, Cancellation.class);
            startActivity(intent);
        } else {
            Toast t = Toast.makeText(this, R.string.mainAdminCannotReserveCancel, Toast.LENGTH_SHORT);
            t.show();
        }

    }
    public void manageSystem(View view) {
        String username = mSharedPreferences.getString(USERNAME_KEY, "username");

        // Only admin can manage system.
        if (username.equals("admin2")) {
            Intent intent = new Intent(MainActivity.this, TransactionLogs.class);
            startActivity(intent);
        } else {
            Toast t = Toast.makeText(this, R.string.mainCannotManageSystem, Toast.LENGTH_SHORT);
            t.show();
        }
    }
}

