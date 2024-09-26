package com.example.fixit;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class PendingSPDetailsActivity extends AppCompatActivity {

    private Button completedService;
    private PendingServiceModel currentServiceProvider;
    private FirebaseFirestore db;
    private Button confirm;
    private String customerName, customerContact, area, city;  // To hold customer details
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_spdetails);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#F44336"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Details");

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();

        // Retrieve the userId from SharedPreferences
        prefs = getSharedPreferences("FixItApp", MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        // Fetch customer details from Firestore
        if (userId != null) {
            fetchCustomerDetails(userId);  // Fetch customer info before booking
        }

        // Set up service provider details
        ImageView profileImageView = findViewById(R.id.profileImage);
        TextView nameTextView = findViewById(R.id.name);
        TextView contactTextView = findViewById(R.id.contact);
        TextView cityTextView = findViewById(R.id.city);
        TextView servicesTextView = findViewById(R.id.services);
        TextView emailTextView = findViewById(R.id.email);

        // Extract service provider's data from the Intent
        String serviceProviderId = getIntent().getStringExtra("serviceProviderId");
        String name = getIntent().getStringExtra("name");
        String contact = getIntent().getStringExtra("contact");
        String city = getIntent().getStringExtra("location");
        String services = getIntent().getStringExtra("service");
        String profileImageUrl = getIntent().getStringExtra("profileImage");
        String email = getIntent().getStringExtra("email");

        // Create the ServiceProviderInfo object
        currentServiceProvider = new PendingServiceModel(name, services, contact, city, profileImageUrl, email, serviceProviderId);

        // Set data to views
        nameTextView.setText(name);
        contactTextView.setText(contact);
        cityTextView.setText(city);
        servicesTextView.setText(services);
        emailTextView.setText(email);

        // Load the profile image using Glide
        Glide.with(this)
                .load(profileImageUrl)
                .placeholder(R.drawable.profile_icon)
                .into(profileImageView);

        completedService = findViewById(R.id.completedService);
        completedService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog completedServiceDialog = new Dialog(PendingSPDetailsActivity.this);
                completedServiceDialog.setContentView(R.layout.completed_service_dialog);
                confirm = completedServiceDialog.findViewById(R.id.confirmDialog);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Move the service provider from PendingBookings to CompletedBookings
                        String userId = prefs.getString("userId", null);
                        if (userId != null) {
                            db.collection("Customers").document(userId)
                                    .collection("PendingBookings").document(currentServiceProvider.getServiceProviderId())
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            db.collection("Customers").document(userId)
                                                    .collection("CompletedBookings").document(currentServiceProvider.getServiceProviderId())
                                                    .set(currentServiceProvider)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(PendingSPDetailsActivity.this, "Service marked as completed!", Toast.LENGTH_SHORT).show();
                                                            completedServiceDialog.dismiss();
                                                            finish(); // Close the current activity
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(PendingSPDetailsActivity.this, "Failed to mark service as completed", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(PendingSPDetailsActivity.this, "Failed to remove from PendingBookings", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
                completedServiceDialog.show();
            }
        });
    }

    // Method to fetch customer details from Firestore
    private void fetchCustomerDetails(String userId) {
        db.collection("Customers").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        customerName = documentSnapshot.getString("name");
                        customerContact = documentSnapshot.getString("contact");
                        area = documentSnapshot.getString("area");
                        city = documentSnapshot.getString("city");
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching customer details: " + e.getMessage()));
    }
}
