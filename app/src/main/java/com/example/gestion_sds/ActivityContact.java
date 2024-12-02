package com.example.gestion_sds;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicelocal.R;

public class ActivityContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Find the button (ImageView) by ID
        ImageView btn1 = findViewById(R.id.btn1);

        // Set an OnClickListener for the button
        btn1.setOnClickListener(v -> {
            // Navigate to ActivityWelcome
            Intent intent = new Intent(ActivityContact.this, ActivityWelcome.class);
            startActivity(intent);
        });
    }
}
