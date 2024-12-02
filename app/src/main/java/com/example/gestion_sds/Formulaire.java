package com.example.gestion_sds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private Spinner activitySpinner;
    private Button saveButton;
    private DatabaseReference reservationsDatabase;
    private String selectedActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        // Initialize Firebase Database
        reservationsDatabase = FirebaseDatabase.getInstance().getReference("reservations");

        // Find Views
        clientNameEditText = findViewById(R.id.client_name);
        datePicker = findViewById(R.id.date_picker);
        activitySpinner = findViewById(R.id.activity_spinner);
        saveButton = findViewById(R.id.reserve_button);

        // Set up Spinner with activities
        String[] activities = {"Tennis", "Basketball", "Boxing", "Fitness", "Swimming", "Gymnastics"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, activities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpinner.setAdapter(adapter);

        // Listen for Spinner selection
        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedActivity = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedActivity = null;
            }
        });

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

        if (selectedActivity == null || selectedActivity.isEmpty()) {
            Toast.makeText(this, "Please select an Activity", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Reservation object
        Reservation reservation = new Reservation(clientName, day, month, year, selectedActivity);

        // Save the reservation data to Firebase
        reservationsDatabase.push().setValue(reservation)
                .addOnSuccessListener(aVoid -> {
                    // Show success message and clear the form
                    Toast.makeText(Formulaire.this, "Reservation saved successfully", Toast.LENGTH_SHORT).show();
                    clientNameEditText.setText("");
                    datePicker.updateDate(year, month - 1, day); // Reset to current date
                    activitySpinner.setSelection(0); // Reset spinner to default position
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
        public String Activity;

        public Reservation() {
            // Default constructor required for Firebase
        }

        public Reservation(String clientName, int day, int month, int year, String Activity) {
            this.clientName = clientName;
            this.day = day;
            this.month = month;
            this.year = year;
            this.Activity = Activity;
        }
    }
}
