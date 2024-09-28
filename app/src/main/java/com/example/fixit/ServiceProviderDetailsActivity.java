package com.example.fixit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class ServiceProviderDetailsActivity extends AppCompatActivity {

    private Button favoriteButton, bookservice, message, withdrawservice;
    private FavoritesStorage favoritesStorage;
    private ServiceProviderInfo currentServiceProvider;
    private FirebaseFirestore db;
    private String customerName, customerContact, area, city, charges;// To hold customer details

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_details_activity);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#F44336"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Details");


        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();

        // Initialize buttons
        favoriteButton = findViewById(R.id.favorite);
        bookservice = findViewById(R.id.bookservice);
        message = findViewById(R.id.message);

        // Retrieve the userId from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("FixItApp", MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        // Fetch customer details from Firestore
        if (userId != null) {
            fetchCustomerDetails(userId);  // Fetch customer info before booking
        }

        favoritesStorage = new FavoritesStorage(this, userId);

        // Set up service provider details
        ImageView profileImageView = findViewById(R.id.profileImage);
        TextView nameTextView = findViewById(R.id.name);
        TextView contactTextView = findViewById(R.id.contact);
        TextView cityTextView = findViewById(R.id.city);
        TextView servicesTextView = findViewById(R.id.services);
        TextView emailTextView = findViewById(R.id.email);
        TextView chargesTextView = findViewById(R.id.charges);

        // Extract service provider's data from the Intent
        String serviceProviderId = getIntent().getStringExtra("serviceProviderId");
        String name = getIntent().getStringExtra("name");
        String contact = getIntent().getStringExtra("contact");
        String city = getIntent().getStringExtra("location");
        String services = getIntent().getStringExtra("service");
        String profileImageUrl = getIntent().getStringExtra("profileImage");
        String email = getIntent().getStringExtra("email");
        String charges = getIntent().getStringExtra("charges");

        // Create the ServiceProviderInfo object
        currentServiceProvider = new ServiceProviderInfo(name, services, contact, city, profileImageUrl, email, serviceProviderId,charges);

        // Set data to views
        nameTextView.setText(name);
        contactTextView.setText(contact);
        cityTextView.setText(city);
        servicesTextView.setText(services);
        emailTextView.setText(email);
        chargesTextView.setText(charges);

        // Load the profile image using Glide
        Glide.with(this)
                .load(profileImageUrl)
                .placeholder(R.drawable.profile_icon)
                .into(profileImageView);
        if (favoritesStorage.isFavorite(currentServiceProvider)) {
            favoriteButton.setText("Remove from Favorites");
        } else {
            favoriteButton.setText("Add to Favorites");
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoritesStorage.isFavorite(currentServiceProvider)) {
                    // Remove from favorites
                    favoritesStorage.removeFavorite(currentServiceProvider);
                    favoriteButton.setText("Add to Favorites");
                    Toast.makeText(getApplicationContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();

                    // Set the result to notify that a change was made
                    setResult(RESULT_OK); // Indicating a change in favorites
                } else {
                    // Add to favorites
                    favoritesStorage.addFavorite(currentServiceProvider);
                    favoriteButton.setText("Remove from Favorites");
                    Toast.makeText(getApplicationContext(), "Added to favorites", Toast.LENGTH_SHORT).show();

                    // Optionally, you can set the result here as well if adding favorites should notify the fragment
                    setResult(RESULT_OK); // Indicating a change in favorites
                }
            }

        });

        // Handle booking service
        bookservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog book = new DatePickerDialog(ServiceProviderDetailsActivity.this, R.style.booking, (datePicker, year, month, day) -> {
                    String bookdate = day + "/" + (month + 1) + "/" + year;

                    String servicesString = servicesTextView.getText().toString();
                    String[] servicesArray = servicesString.split(",");

                    Dialog dialog = new Dialog(ServiceProviderDetailsActivity.this);
                    dialog.setContentView(R.layout.service_type_dialog);
                    dialog.show();

                    EditText reqservice = dialog.findViewById(R.id.reqservice);
                    Button ok = dialog.findViewById(R.id.ok);
                    Button cancel = dialog.findViewById(R.id.cancel);

                    cancel.setOnClickListener(v -> dialog.dismiss());

                    ok.setOnClickListener(v -> {
                        String requiredService = reqservice.getText().toString().trim();

                        boolean serviceProvided = false;
                        for (String service : servicesArray) {
                            if (requiredService.equalsIgnoreCase(service.trim())) {
                                serviceProvided = true;
                                break;
                            }
                        }

                        if (serviceProvided) {
                            dialog.dismiss();

                            Dialog confirmDialog = new Dialog(ServiceProviderDetailsActivity.this);
                            confirmDialog.setContentView(R.layout.confirm_booking_dialog);
                            confirmDialog.show();

                            TextView datebook = confirmDialog.findViewById(R.id.datebook);
                            TextView servicerequest = confirmDialog.findViewById(R.id.servicerequest);
                            Button confirm = confirmDialog.findViewById(R.id.confirm);
                            Button cancelConfirm = confirmDialog.findViewById(R.id.button3);

                            datebook.setText(bookdate);
                            servicerequest.setText(requiredService);

                            cancelConfirm.setOnClickListener(v1 -> confirmDialog.dismiss());

                            confirm.setOnClickListener(v1 -> {
                                // Create BookingRequest with customer details fetched from Firestore
                                BookingRequest bookingRequest = new BookingRequest(customerName, customerContact, area, city, requiredService, bookdate, serviceProviderId,charges);

                                // Send booking request to service provider via Firestore
                                db.collection("Service providers")
                                        .document(serviceProviderId)
                                        .collection("newRequests")
                                        .add(bookingRequest)
                                        .addOnSuccessListener(documentReference -> Toast.makeText(ServiceProviderDetailsActivity.this, "Booking request sent", Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Toast.makeText(ServiceProviderDetailsActivity.this, "Failed to send request", Toast.LENGTH_SHORT).show());

                                db.collection("Customers")
                                        .document(userId)
                                        .collection("PendingBookings")
                                        .document(serviceProviderId)
                                        .set(currentServiceProvider)
                                        .addOnSuccessListener(documentReference -> Toast.makeText(ServiceProviderDetailsActivity.this, "Added to Requested Bookings", Toast.LENGTH_LONG).show())
                                        .addOnFailureListener(e -> Toast.makeText(ServiceProviderDetailsActivity.this, "Failed to add to Requested Bookings", Toast.LENGTH_LONG).show());
                            });
                        } else {
                            Toast.makeText(ServiceProviderDetailsActivity.this, "Service not provided", Toast.LENGTH_SHORT).show();
                        }
                    });
                }, 2024, 8, 15);
                book.show();
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
                        charges = documentSnapshot.getString("charges");
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching customer details: " + e.getMessage()));
    }
}



















//public class ServiceProviderDetailsActivity extends AppCompatActivity {
//
//    private Button favoriteButton, bookservice, message, withdrawservice;
//    private FavoritesStorage favoritesStorage;
//    private ServiceProviderInfo currentServiceProvider;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_service_provider_details_activity);
//
//        // Initialize buttons
//        favoriteButton = findViewById(R.id.favorite);
//        bookservice = findViewById(R.id.bookservice);
//        message = findViewById(R.id.message);
//        withdrawservice = findViewById(R.id.withdrawservice);
//
//        // Retrieve the userId from SharedPreferences (or Intent, depending on how you're managing user sessions)
//        SharedPreferences prefs = getSharedPreferences("FixItApp", MODE_PRIVATE);
//        String userId = prefs.getString("userId", null);
//
//        // Initialize storage helper for managing favorites (pass both the context and the userId)
//        favoritesStorage = new FavoritesStorage(this, userId);
//
//        // Get references to the views
//        ImageView profileImageView = findViewById(R.id.profileImage);
//        TextView nameTextView = findViewById(R.id.name);
//        TextView contactTextView = findViewById(R.id.contact);
//        TextView cityTextView = findViewById(R.id.city);
//        TextView servicesTextView = findViewById(R.id.services);
//        TextView emailTextView = findViewById(R.id.email);
//
//        // Extract the service provider's data from the Intent
//        String serviceProviderId;
//        serviceProviderId= getIntent().getStringExtra("serviceProviderId");
//
//        String name = getIntent().getStringExtra("name");
//        String contact = getIntent().getStringExtra("contact");
//        String city = getIntent().getStringExtra("location");
//        String services = getIntent().getStringExtra("service");
//        String profileImageUrl = getIntent().getStringExtra("profileImage");
//        String email = getIntent().getStringExtra("email");
//
//        // Create the current ServiceProviderInfo object
//        currentServiceProvider = new ServiceProviderInfo(name, services, contact, city, profileImageUrl, email, serviceProviderId);
//
//        // Set the data to the views
//        nameTextView.setText(name);
//        contactTextView.setText(contact);
//        cityTextView.setText(city);
//        servicesTextView.setText(services);
//        emailTextView.setText(email);
//
//        // Load the profile image using Glide
//        Glide.with(this)
//                .load(profileImageUrl)
//                .placeholder(R.drawable.profile_icon) // Optional placeholder
//                .into(profileImageView);
//
//        // Check if the service provider is already in the favorites
//        if (favoritesStorage.isFavorite(currentServiceProvider)) {
//            favoriteButton.setText("Remove from Favorites");
//        } else {
//            favoriteButton.setText("Add to Favorites");
//        }
//
//        // Handle the favorite button click
//        favoriteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (favoritesStorage.isFavorite(currentServiceProvider)) {
//                    // Remove from favorites
//                    favoritesStorage.removeFavorite(currentServiceProvider);
//                    favoriteButton.setText("Add to Favorites");
//                    Toast.makeText(getApplicationContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
//
//                    // Set the result to notify that a change was made
//                    setResult(RESULT_OK); // Indicating a change in favorites
//                } else {
//                    // Add to favorites
//                    favoritesStorage.addFavorite(currentServiceProvider);
//                    favoriteButton.setText("Remove from Favorites");
//                    Toast.makeText(getApplicationContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
//
//                    // Optionally, you can set the result here as well if adding favorites should notify the fragment
//                    setResult(RESULT_OK); // Indicating a change in favorites
//                }
//            }
//
//        });
//
//        bookservice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Show DatePickerDialog
//                DatePickerDialog book = new DatePickerDialog(ServiceProviderDetailsActivity.this, R.style.booking, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                        // Store the selected date in bookdate string
//                        String bookdate = day + "/" + (month + 1) + "/" + year;  // month + 1 because month is zero-based in DatePicker
//
//                        // Get the services provided by the service provider (comma-separated string)
//                        String servicesString = servicesTextView.getText().toString();
//                        String[] servicesArray = servicesString.split(",");
//
//                        // Open the service type dialog
//                        Dialog dialog = new Dialog(ServiceProviderDetailsActivity.this);
//                        dialog.setContentView(R.layout.service_type_dialog);
//                        dialog.show();
//
//                        // Get the UI components from the dialog
//                        EditText reqservice = dialog.findViewById(R.id.reqservice);
//                        Button ok = dialog.findViewById(R.id.ok);
//                        Button cancel = dialog.findViewById(R.id.cancel);
//
//                        // Handle the Cancel button in the service dialog
//                        cancel.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss(); // Close the service type dialog and terminate booking process
//                            }
//                        });
//
//                        // Handle the OK button in the service dialog
//                        ok.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                String requiredService = reqservice.getText().toString().trim(); // Get entered service and trim spaces
//
//                                boolean serviceProvided = false;
//                                for (String service : servicesArray) {
//                                    if (requiredService.equalsIgnoreCase(service.trim())) {
//                                        serviceProvided = true;
//                                        break;
//                                    }
//                                }
//
//                                if (serviceProvided) {
//                                    // Service exists, show the confirmation dialog
//                                    dialog.dismiss();  // Close the service type dialog
//
//                                    Dialog confirmDialog = new Dialog(ServiceProviderDetailsActivity.this);
//                                    confirmDialog.setContentView(R.layout.confirm_booking_dialog);
//                                    confirmDialog.show();
//
//                                    // Get the UI components from the confirmation dialog
//                                    TextView datebook = confirmDialog.findViewById(R.id.datebook);
//                                    TextView servicerequest = confirmDialog.findViewById(R.id.servicerequest);
//                                    Button confirm = confirmDialog.findViewById(R.id.confirm);
//                                    Button cancelConfirm = confirmDialog.findViewById(R.id.button3);
//
//                                    // Set the selected date and service in the confirmation dialog
//                                    datebook.setText(bookdate);
//                                    servicerequest.setText(requiredService);
//
//                                    // Handle the Cancel button in the confirmation dialog
//                                    cancelConfirm.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            confirmDialog.dismiss();  // Close the confirmation dialog and terminate booking process
//                                        }
//                                    });
//
//                                    // Handle the Confirm button (No action as per your request, but mention the listener)
//                                    confirm.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            // Gather booking details
//                                            String customerName = "Customer's Name";  // You can retrieve this from SharedPreferences or another source
//                                            String customerContact = "Customer's Contact";  // Retrieve contact
//                                            String area = "Customer's Area";  // Retrieve area from user info
//                                            String city = "Customer's City";  // Retrieve city from user info
//                                            String service = servicerequest.getText().toString();  // Service user selected
//                                            String bookingDate = datebook.getText().toString();  // Date of booking
//                                            // Create a new BookingRequest object
//                                            BookingRequest bookingRequest = new BookingRequest(customerName, customerContact, area, city, service, bookingDate,serviceProviderId);
//
//                                            // Send the booking request to the service provider's "newRequestSection" via Firebase
//                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
//                                            db.collection("Service providers")
//                                                    .document(serviceProviderId)  // Assuming you have the service provider's ID
//                                                    .collection("newRequests")
//                                                    .add(bookingRequest)
//                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                                        @Override
//                                                        public void onSuccess(DocumentReference documentReference) {
//                                                            Toast.makeText(ServiceProviderDetailsActivity.this, "Booking request sent", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    })
//                                                    .addOnFailureListener(new OnFailureListener() {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e) {
//                                                            Toast.makeText(ServiceProviderDetailsActivity.this, "Failed to send request", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    });
//                                        }
//                                    });
//
//
//                                } else {
//                                    // Service not provided, show a toast message
//                                    Toast.makeText(ServiceProviderDetailsActivity.this, "Service not provided by the service provider", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                }, 2024, 8, 15);
//                book.show();
//            }
//        });
//}
//}
