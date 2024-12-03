package com.example.gestion_sds;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicelocal.R;

public class ActivityWelcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);  // Use the correct layout

        // ImageViews in your layout
        ImageView baskettImage = findViewById(R.id.baskett_image);
        ImageView tennisImage = findViewById(R.id.tennis_image);
        ImageView boxxImage = findViewById(R.id.boxx_image);
        ImageView fitnessImage = findViewById(R.id.fitnes_image);
        ImageView swimminggImage = findViewById(R.id.swimmingg_image);
        ImageView gymnasticImage = findViewById(R.id.gymnastic_image);
        ImageView orderImageView = findViewById(R.id.order);
        TextView backToHome = findViewById(R.id.back_to_home);

        // Back to Home functionality
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityWelcome.this, ActivityMain.class);
                startActivity(intent);
            }
        });

        orderImageView.setOnClickListener(v -> {
            // Navigate to the FormActivity
            Log.d("ActivityWelcome", "Order image clicked");
            Intent intent = new Intent(ActivityWelcome.this, Formulaire.class);
            startActivity(intent);
        });
        // Set click listeners for each image
        baskettImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailsActivity("Basketball Session");
            }
        });

        tennisImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailsActivity("Tennis Session");
            }
        });

        boxxImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailsActivity("Boxing Session");
            }
        });

        fitnessImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailsActivity("Fitness Session");
            }
        });

        swimminggImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailsActivity("Swimming Session");
            }
        });

        gymnasticImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailsActivity("Gymnastic Session");
            }
        });
    }

    // Method to open the details activity and pass the session type
    private void openDetailsActivity(String sessionType) {
        Intent intent = new Intent(ActivityWelcome.this, ActivityDetails.class);
        intent.putExtra("SESSION_TYPE", sessionType);  // Pass the session type to ActivityDetails
        startActivity(intent);
    }
}
