package com.example.fixit;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ServiceProviderDetailsActtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_details_acttivity);

        // Get references to the views
        ImageView profileImageView = findViewById(R.id.profileImage);
        TextView nameTextView = findViewById(R.id.name);
        TextView contactTextView = findViewById(R.id.contact);
        TextView cityTextView = findViewById(R.id.city);
        TextView servicesTextView = findViewById(R.id.services);
        TextView email = findViewById(R.id.email);

        // Extract the data from the Intent
        String name = getIntent().getStringExtra("name");
        String contact = getIntent().getStringExtra("contact");
        String city = getIntent().getStringExtra("location");
        String services = getIntent().getStringExtra("service");
        String profileImageUrl = getIntent().getStringExtra("profileImage");
        String email1 = getIntent().getStringExtra("email");

        // Set the data in the corresponding views
        nameTextView.setText(name);
        contactTextView.setText(contact);
        cityTextView.setText(city);
        servicesTextView.setText(services);
        email.setText(email1);

        // Load the profile image using Glide
        Glide.with(this)
                .load(profileImageUrl)
                .placeholder(R.drawable.profile_icon) // Optional placeholder
                .into(profileImageView);
    }
}
