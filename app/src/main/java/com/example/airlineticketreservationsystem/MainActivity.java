package com.example.airlineticketreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTitle;

    ImageView mImage;

    Button mCreateButton;
    Button mReserveButton;
    Button mCancelButton;
    Button mManageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = findViewById(R.id.mainTitleTextView);

        mImage = findViewById(R.id.mainOtterImageView);

        mCreateButton = findViewById(R.id.mainCreateButton);
        mReserveButton = findViewById(R.id.mainReserveButton);
        mCancelButton = findViewById(R.id.mainCancelButton);
        mManageButton = findViewById(R.id.mainManageButton);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });




    }
}
