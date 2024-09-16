package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            // If the user is already logged in, go to RoleSelectorActivity
            startActivity(new Intent(MainActivity.this, RoleSelectorActivity.class));
        } else {
            // Otherwise, go to LoginActivity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        finish();
    }
}
