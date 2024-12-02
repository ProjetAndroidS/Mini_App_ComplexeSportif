package com.example.gestion_sds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicelocal.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Formulaire extends AppCompatActivity {

    private EditText clientNameEditText;
    private DatePicker datePicker;
    private Button saveButton;
    private DatabaseReference reservationsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        // Initialize Firebase Database
        reservationsDatabase = FirebaseDatabase.getInstance().getReference("reservations");

        // Find Views
        clientNameEditText = findViewById(R.id.client_name);
        datePicker = findViewById(R.id.date_picker);
        saveButton = findViewById(R.id.reserve_button); // Assuming you have a Save button in your layout

        // Set onClickListener for "Save" Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDatabase();
            }
        });

        // Back to Home functionality
        TextView backToHome = findViewById(R.id.back_to_home);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Formulaire.this, ActivityMain.class);
                startActivity(intent);
            }
        });
    }

    private void saveDataToDatabase() {
        String clientName = clientNameEditText.getText().toString().trim();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Month is zero-based
        int year = datePicker.getYear();

        if (clientName.isEmpty()) {
            clientNameEditText.setError("Client name is required");
            return;
        }

        // Create a Reservation object
        Reservation reservation = new Reservation(clientName, day, month, year);

        // Save the reservation data to Firebase
        reservationsDatabase.push().setValue(reservation)
                .addOnSuccessListener(aVoid -> {
                    // Show success message and clear the form
                    Toast.makeText(Formulaire.this, "Reservation saved successfully", Toast.LENGTH_SHORT).show();
                    clientNameEditText.setText("");
                    datePicker.updateDate(year, month - 1, day); // Reset to current date
                })
                .addOnFailureListener(e -> {
                    // Show error message
                    Toast.makeText(Formulaire.this, "Failed to save reservation", Toast.LENGTH_SHORT).show();
                });
    }

    // Reservation class to hold the data
    public static class Reservation {
        public String clientName;
        public int day, month, year;

        public Reservation() {
            // Default constructor required for Firebase
        }

        public Reservation(String clientName, int day, int month, int year) {
            this.clientName = clientName;
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }
}
