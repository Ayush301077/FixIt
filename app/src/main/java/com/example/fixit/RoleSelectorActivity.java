package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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

//        String userId = getIntent().getStringExtra("userId");

        Service_Taker = findViewById(R.id.button1);
        Service_Provider = findViewById(R.id.button2);


        if (currentUser == null) {
            startActivity(new Intent(RoleSelectorActivity.this, LoginActivity.class));
            finish();
            return;
        }

        Service_Taker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = currentUser.getUid();
                Intent intent = new Intent(RoleSelectorActivity.this, CustomerDashboardActivity.class);
                intent.putExtra("userId", userid);
                startActivity(intent);
            }
        });

        Service_Provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = currentUser.getUid();
                Intent intent = new Intent(RoleSelectorActivity.this, ServiceProviderDashboard.class);
                intent.putExtra("userId", userid);
                startActivity(intent);
            }
        });
    }
}
