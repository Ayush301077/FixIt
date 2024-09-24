package com.example.fixit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CustomersEditProfileActivity extends AppCompatActivity {

    TextView name, contact,email,area,city;
    Button save;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_edit_profile);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);
        area = findViewById(R.id.area);
        city = findViewById(R.id.city);
        save = findViewById(R.id.save);

        db = FirebaseFirestore.getInstance();
        String userIdFromIntent = getIntent().getStringExtra("userId");
        final String userId;
        if (userIdFromIntent == null) {
            SharedPreferences prefs = getSharedPreferences("FixItApp", MODE_PRIVATE);
            userId = prefs.getString("userId", null); // Retrieve saved userId
        } else {
            userId = userIdFromIntent;
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect the profile data from the form
                String nameStr = name.getText().toString();
                String contactStr = contact.getText().toString();
                String emailStr = email.getText().toString();
                String areaStr = area.getText().toString();
                String cityStr = city.getText().toString();
                updateProfile(userId, nameStr, contactStr, emailStr, areaStr, cityStr);

            }
        });

    }

    private void updateProfile(String userId, String name, String contact, String email, String area, String city) {
        // Create a HashMap to store the profile data
        HashMap<String, Object> customer = new HashMap<>();
        customer.put("name", name);
        customer.put("contact", contact);
        customer.put("email", email);
        customer.put("area", area);
        customer.put("city", city);

        // Reference to the specific user's profile document in Firestore
        Task<Void> docRef = db.collection("Customers").document(userId).set(customer)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error updating profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ProfileUpdate", "Error updating profile: " + e.getMessage());
                        Toast.makeText(getApplicationContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });

        // Navigate back to the ServiceProviderDashboard after updating the profile
        startActivity(new Intent(CustomersEditProfileActivity.this, CustomerDashboardActivity.class));
        finish(); // Finish the activity to prevent going back to this screen
    }
}