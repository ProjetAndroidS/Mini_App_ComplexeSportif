package com.example.gestion_sds;

import android.os.Bundle;
import android.widget.ArrayAdapter;
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

public class ActivityMemberList extends AppCompatActivity {

    private ListView clientListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> clientList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        // Initialize ListView and ArrayList
        clientListView = findViewById(R.id.Client_list_view);
        clientList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clientList);
        clientListView.setAdapter(adapter);

        // Reference to "Users" table in Firebase
        databaseReference = FirebaseDatabase.getInstance("https://gestion-complexesportif-default-rtdb.firebaseio.com/")
                .getReference("Users");

        // Retrieve data from Firebase
        fetchClientList();
    }

    private void fetchClientList() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                clientList.clear(); // Clear the list before updating
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    User user = childSnapshot.getValue(User.class);
                    if (user != null) {
                        clientList.add(user.username + " - " + user.email);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter about data changes
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ActivityMemberList.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // User class to match the Firebase data structure
    public static class User {
        public String username;
        public String email;
        public String password;

        public User() {
        }

        public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }
    }
}
