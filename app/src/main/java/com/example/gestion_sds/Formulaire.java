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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Formulaire extends AppCompatActivity {

    private EditText clientNameEditText;
    private DatePicker datePicker;
    private Button saveButton;
    private TextView backToHomeButton; // Add button for back to home
    private DatabaseReference reservationsDatabase;
    private DatabaseReference usersDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        reservationsDatabase = FirebaseDatabase.getInstance().getReference("reservations");
        usersDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Find Views
        clientNameEditText = findViewById(R.id.client_name);
        datePicker = findViewById(R.id.date_picker);
        saveButton = findViewById(R.id.reserve_button); // Assuming you have a Save button in your layout
        backToHomeButton = findViewById(R.id.back_to_home); // Assuming your back button has this ID

        // Set onClickListener for "Save" Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDatabase();
            }
        });

        // Set onClickListener for "Retour à l'accueil" Button
        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to MainActivity
                Intent intent = new Intent(Formulaire.this, MainActivity.class);
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

        // Get the current user's ID (assuming the user is logged in)
        String userId = mAuth.getCurrentUser().getUid();

        // Retrieve the user's clientName from the 'users' table
        usersDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the user's clientName from the dataSnapshot
                String usernameFromDb = dataSnapshot.child("clientName").getValue(String.class);

                if (usernameFromDb != null) {
                    // Now use this username for the reservation
                    Reservation reservation = new Reservation(usernameFromDb, day, month, year);

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
                } else {
                    Toast.makeText(Formulaire.this, "User name not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Formulaire.this, "Error retrieving user data", Toast.LENGTH_SHORT).show();
            }
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
