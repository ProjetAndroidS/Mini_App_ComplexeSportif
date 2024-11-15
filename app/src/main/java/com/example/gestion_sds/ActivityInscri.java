package com.example.gestion_sds;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicelocal.R;

public class ActivityInscri extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscri);

        // Initialize the Login button
        Button loginButton = findViewById(R.id.Login); // Assurez-vous que l'ID est correct dans le fichier XML

        // Set OnClickListener for the Login button
        loginButton.setOnClickListener(v -> {
            // Navigate to the Login activity
            Intent intent = new Intent(ActivityInscri.this, ActivityLogin.class);
            startActivity(intent);
        });
    }
}
