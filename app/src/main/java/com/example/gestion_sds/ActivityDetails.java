package com.example.gestion_sds;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.servicelocal.R;

public class ActivityDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details); // Use the correct layout for this activity

        // Get the session type passed from the previous activity
        String sessionType = getIntent().getStringExtra("SESSION_TYPE");

        // Display the session type (for example, in a TextView)
      //  TextView sessionText = findViewById(R.id.session_text);
        //sessionText.setText(sessionType);

    }
}