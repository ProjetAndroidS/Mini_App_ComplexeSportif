package com.example.gestion_sds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicelocal.R;

public class ActivityDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details); // Use the correct layout for this activity

        // Get the session type passed from the previous activity
        String sessionType = getIntent().getStringExtra("SESSION_TYPE");

        // Find the "Contact" button
        Button contactButton = findViewById(R.id.Contact);
        ImageView backButton = findViewById(R.id.back);

        // Set onClickListener for the "Contact" button
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ActivityContact
                Intent intent = new Intent(ActivityDetails.this, ActivityContact.class);
                startActivity(intent);
            }
        });

        // Set back button action
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityDetails.this, ActivityWelcome.class);
            startActivity(intent);
            finish();
        });
    }
}
