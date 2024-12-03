package com.example.gestion_sds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicelocal.R;

public class ActivityHomeAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        // Update welcome text
        TextView welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setText("Welcome, Admin!");

        // Find the "Client Reservation" button
        Button clientReservationButton = findViewById(R.id.Client_Reservation);
        Button Clientlist = findViewById(R.id.Client_list);

        // Set onClickListener for the button
        clientReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ActivityOrders
                Intent intent = new Intent(ActivityHomeAdmin.this, ActivityOrders.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for the button
        Clientlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ActivityOrders
                Intent intent = new Intent(ActivityHomeAdmin.this, ActivityMemberList.class);
                startActivity(intent);
            }
        });
    }
}
