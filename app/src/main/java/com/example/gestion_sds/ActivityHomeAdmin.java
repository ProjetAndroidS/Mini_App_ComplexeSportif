package com.example.gestion_sds;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicelocal.R;

public class ActivityHomeAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        TextView welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setText("Welcome, Admin!");
    }
}
