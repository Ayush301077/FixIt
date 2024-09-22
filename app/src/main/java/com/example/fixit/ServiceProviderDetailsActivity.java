package com.example.fixit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ServiceProviderDetailsActivity extends AppCompatActivity {

    private Button favoriteButton, bookservice, message, withdrawservice;
    private FavoritesStorage favoritesStorage;
    private ServiceProviderInfo currentServiceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_details_activity);

        // Initialize buttons
        favoriteButton = findViewById(R.id.favorite);
        bookservice = findViewById(R.id.bookservice);
        message = findViewById(R.id.message);
        withdrawservice = findViewById(R.id.withdrawservice);

        // Initialize storage helper for managing favorites
        favoritesStorage = new FavoritesStorage(this);

        // Get references to the views
        ImageView profileImageView = findViewById(R.id.profileImage);
        TextView nameTextView = findViewById(R.id.name);
        TextView contactTextView = findViewById(R.id.contact);
        TextView cityTextView = findViewById(R.id.city);
        TextView servicesTextView = findViewById(R.id.services);
        TextView emailTextView = findViewById(R.id.email);

        // Extract the service provider's data from the Intent
        String name = getIntent().getStringExtra("name");
        String contact = getIntent().getStringExtra("contact");
        String city = getIntent().getStringExtra("location");
        String services = getIntent().getStringExtra("service");
        String profileImageUrl = getIntent().getStringExtra("profileImage");
        String email = getIntent().getStringExtra("email");

        // Create the current ServiceProviderInfo object
        currentServiceProvider = new ServiceProviderInfo(name, services, contact, city, profileImageUrl, email);

        // Set the data to the views
        nameTextView.setText(name);
        contactTextView.setText(contact);
        cityTextView.setText(city);
        servicesTextView.setText(services);
        emailTextView.setText(email);

        // Load the profile image using Glide
        Glide.with(this)
                .load(profileImageUrl)
                .placeholder(R.drawable.profile_icon) // Optional placeholder
                .into(profileImageView);

        // Check if the service provider is already in the favorites
        if (favoritesStorage.isFavorite(currentServiceProvider)) {
            favoriteButton.setText("Remove from Favorites");
        } else {
            favoriteButton.setText("Add to Favorites");
        }

        // Handle the favorite button click
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

        bookservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog book = new DatePickerDialog(ServiceProviderDetailsActivity.this,R.style.booking, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String bookdate = day +"/"+month+"/"+year;
                        String servicesString = servicesTextView.getText().toString();
                        String[] servicesArray = servicesString.split(",");
                        Dialog dialog = new Dialog(ServiceProviderDetailsActivity.this);
                        dialog.setContentView(R.layout.service_type_dialog);
                        dialog.show();
                        TextView reqservice = dialog.findViewById(R.id.reqservice);
                        Button ok = dialog.findViewById(R.id.ok);
                        Button cancel = dialog.findViewById(R.id.cancel);

                        String requiredservice = reqservice.getText().toString();
                        for(String serv : servicesArray)
                        {
                            if(requiredservice == serv)
                            {
                                Dialog confirmdDialog = new Dialog(ServiceProviderDetailsActivity.this);
                                confirmdDialog.setContentView(R.layout.confirm_booking_dialog);
                                confirmdDialog.show();
                            }
                        }
                        dialog.dismiss();




                    }
                }, 2024,8,15);
                book.show();
            }
        });
}
}
