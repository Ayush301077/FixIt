package com.example.fixit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

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

        // Other button logic (bookservice, message, withdrawservice) remains the same
    }
}





//package com.example.fixit;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//
//import com.bumptech.glide.Glide;
//
//public class ServiceProviderDetailsActivity extends AppCompatActivity {
//
//    private Button favorite, bookservice, message, withdrawservice;
//    private FavoritesStorage favoritesStorage;  // Helper class for storing favorites
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_service_provider_details_activity);
//
//        // Initialize buttons
//        favorite = findViewById(R.id.favorite);
//        bookservice = findViewById(R.id.bookservice);
//        message = findViewById(R.id.message);
//        withdrawservice = findViewById(R.id.withdrawservice);
//
//        // Initialize storage helper for managing favorites
//        favoritesStorage = new FavoritesStorage(this);
//
//        // Get references to the views in the layout
//        ImageView profileImageView = findViewById(R.id.profileImage);
//        TextView nameTextView = findViewById(R.id.name);
//        TextView contactTextView = findViewById(R.id.contact);
//        TextView cityTextView = findViewById(R.id.city);
//        TextView servicesTextView = findViewById(R.id.services);
//        TextView emailTextView = findViewById(R.id.email);
//
//        // Extract the service provider's data from the Intent
//        String name = getIntent().getStringExtra("name");
//        String contact = getIntent().getStringExtra("contact");
//        String city = getIntent().getStringExtra("location");
//        String services = getIntent().getStringExtra("service");
//        String profileImageUrl = getIntent().getStringExtra("profileImage");
//        String email = getIntent().getStringExtra("email");
//
//        // Create a ServiceProviderInfo object to encapsulate the data
//        ServiceProviderInfo spi = new ServiceProviderInfo(name, services, contact, city, profileImageUrl, email);
//
//        // Set the extracted data to the corresponding views
//        nameTextView.setText(name);
//        contactTextView.setText(contact);
//        cityTextView.setText(city);
//        servicesTextView.setText(services);
//        emailTextView.setText(email);
//
//        // Load the service provider's profile image using Glide
//        Glide.with(this)
//                .load(profileImageUrl)
//                .placeholder(R.drawable.profile_icon) // Set a placeholder image if needed
//                .into(profileImageView);
//
//        // Set up click listener for the "Add to Favorites" button
//        favorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Add the current service provider to favorites using the storage helper
//                favoritesStorage.addFavorite(spi);
//
//                // Show a confirmation message to the user
//                Toast.makeText(getApplicationContext(), "Service provider added to favorites", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Set up click listener for the "Book Service" button (implement logic as needed)
//        bookservice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Service booked successfully", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Set up click listener for the "Message" button (implement logic as needed)
//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Message sent to service provider", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Set up click listener for the "Withdraw Service" button (implement logic as needed)
//        withdrawservice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Service withdrawn successfully", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Handle navigation to the favorites fragment (example if needed)
//    private void navigateToFavoritesFragment() {
//        CustomerFavoritesFragment favoritesFragment = new CustomerFavoritesFragment();
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, favoritesFragment)  // Replace with your fragment container ID
//                .addToBackStack(null)  // Add the transaction to the back stack so the user can navigate back
//                .commit();
//    }
//}
//



