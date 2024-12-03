package com.example.gestion_sds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicelocal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityOrders extends AppCompatActivity {

    private ListView ordersListView;
    private ArrayList<String> ordersList;
    private DatabaseReference reservationsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // Initialize UI components
        ordersListView = findViewById(R.id.orders_list_view);
        ImageView backButton = findViewById(R.id.back);

        // Initialize List and Adapter
        ordersList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ordersList);
        ordersListView.setAdapter(adapter);

        // Initialize Firebase reference
        reservationsDatabase = FirebaseDatabase.getInstance().getReference("reservations");

        // Set back button action
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityOrders.this, ActivityHomeAdmin.class);
            startActivity(intent);
            finish();
        });

        // Fetch data from Firebase
        reservationsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ordersList.clear(); // Clear the list before adding new data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Fetch reservation data
                    String description = snapshot.child("Description").getValue(String.class);
                    Integer day = snapshot.child("day").getValue(Integer.class);
                    Integer month = snapshot.child("month").getValue(Integer.class);
                    Integer year = snapshot.child("year").getValue(Integer.class);
                    String activity = snapshot.child("Activity").getValue(String.class); // Make 'activity' final
                    String userId = snapshot.child("userId").getValue(String.class);  // Get userId from the reservation

                    // Default values in case of missing data
                    description = (description != null) ? description : "Unknown Client";
                    day = (day != null) ? day : 0;
                    month = (month != null) ? month : 0;
                    year = (year != null) ? year : 0;

                    // Fetch user's name from the Users table using userId
                    DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("Users");
                    Integer finalDay = day;
                    Integer finalMonth = month;
                    Integer finalYear = year;
                    usersDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot userSnapshot) {
                            String clientName = userSnapshot.child("username").getValue(String.class);  // Fetch username
                            clientName = (clientName != null) ? clientName : "Unknown Client";

                            // Format order details with client name
                            String orderDetails = "Client: " + clientName + "\nDate: " + finalDay + "/" + finalMonth + "/" + finalYear + "\nActivity: " + activity;

                            // Add to the list if the data has been successfully retrieved
                            if (!ordersList.contains(orderDetails)) {
                                ordersList.add(orderDetails);

                                // Notify the adapter that the data has changed
                                runOnUiThread(() -> adapter.notifyDataSetChanged());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle database error
                            Toast.makeText(ActivityOrders.this, "Failed to load user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(ActivityOrders.this, "Failed to load reservations: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

