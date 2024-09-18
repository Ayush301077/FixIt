package com.example.fixit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RoleSelectorActivity extends AppCompatActivity {

    Button Service_Taker, Service_Provider;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roleselector);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        // Buttons to select role
        Service_Taker = findViewById(R.id.button1);
        Service_Provider = findViewById(R.id.button2);

        // If the user is not logged in, redirect to LoginActivity
        if (currentUser == null) {
            startActivity(new Intent(RoleSelectorActivity.this, LoginActivity.class));
            finish();
            return;
        }

        Service_Taker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = currentUser.getUid();
                // Store userId in SharedPreferences
                SharedPreferences prefs = getSharedPreferences("FixItApp", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userId", userid); // Saving the userId
                editor.apply();

                Intent intent = new Intent(RoleSelectorActivity.this, CustomerDashboardActivity.class);
                intent.putExtra("userId", userid);
                startActivity(intent);
            }
        });

        Service_Provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = currentUser.getUid();

                // Store userId in SharedPreferences
                SharedPreferences prefs = getSharedPreferences("FixItApp", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userId", userid); // Saving the userId
                editor.apply();

                // Pass the userId to the ServiceProviderDashboard
                Intent intent = new Intent(RoleSelectorActivity.this, ServiceProviderDashboard.class);
                intent.putExtra("userId", userid);
                startActivity(intent);
            }
        });
    }
}
