package com.example.gestion_sds;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicelocal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityInscri extends AppCompatActivity {

    // Declare Firebase variables
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private EditText mUsername, mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscri); // Ensure this layout is correct for the Inscri activity

        // Initialize FirebaseAuth and FirebaseDatabase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // Initialize UI elements
        mUsername = findViewById(R.id.SignUpUsername);
        mEmail = findViewById(R.id.SignUpEmail);
        mPassword = findViewById(R.id.SignUpPassword);

        // Button for Inscri
        Button mInscriButton = findViewById(R.id.btnSignUp); // Ensure the correct button ID

        // Set OnClickListener for Inscri button
        mInscriButton.setOnClickListener(v -> {
            String username = mUsername.getText().toString().trim();
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            // Validation checks
            if (TextUtils.isEmpty(username)) {
                mUsername.setError("Username is required");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is required");
                return;
            }

            // Create a new user with Firebase Authentication
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign-up success
                            String userId = mAuth.getCurrentUser().getUid();

                            // Prepare user data to be stored in the Realtime Database
                            User user = new User(username, email);

                            // Store user data in the "users" node of the Firebase Realtime Database
                            mDatabase.getReference("users").child(userId).setValue(user)
                                    .addOnCompleteListener(dbTask -> {
                                        if (dbTask.isSuccessful()) {
                                            // Data stored successfully
                                            Toast.makeText(ActivityInscri.this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
                                            // Navigate to another activity or show success message
                                        } else {
                                            // Error storing user data
                                            Toast.makeText(ActivityInscri.this, "Failed to store user data: " + dbTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Sign-up failed
                            Toast.makeText(ActivityInscri.this, "Inscription échouée: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    // User model class
    public static class User {
        public String username;
        public String email;

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }
}
